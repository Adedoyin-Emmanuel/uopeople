import SwiftUI
import SwiftData
import LocalAuthentication
import CoreLocation

// MARK: - Models
@Model
final class User {
    @Attribute(.unique) var email: String
    var name: String
    var passwordHash: String
    var isBiometricRegistered: Bool

    @Relationship(deleteRule: .cascade, inverse: \AttendanceRecord.user)
    var attendanceRecords: [AttendanceRecord]

    init(email: String, name: String, passwordHash: String, isBiometricRegistered: Bool = false) {
        self.email = email
        self.name = name
        self.passwordHash = passwordHash
        self.isBiometricRegistered = isBiometricRegistered
        self.attendanceRecords = []
    }
}

@Model
final class AttendanceRecord {
    var timestamp: Date
    var type: String // "CheckIn" or "CheckOut"
    var user: User?

    init(timestamp: Date, type: String, user: User? = nil) {
        self.timestamp = timestamp
        self.type = type
        self.user = user
    }
}

// MARK: - Network
enum NetworkError: Error, LocalizedError {
    case noInternetConnection
    case invalidURL
    case noData
    case decodingError(Error)
    case serverError(Int)

    var errorDescription: String? {
        switch self {
        case .noInternetConnection:
            return "No internet connection. Please check your network settings."
        case .invalidURL:
            return "The URL provided was invalid."
        case .noData:
            return "No data was returned from the server."
        case .decodingError(let error):
            return "Failed to decode the response: \(error.localizedDescription)"
        case .serverError(let statusCode):
            return "Server returned an error with status code: \(statusCode)"
        }
    }
}

class NetworkService {
    static let shared = NetworkService()
    private init() {}

    func performRequest<T: Decodable>(url: URL, completion: @escaping (Result<T, NetworkError>) -> Void) {
        guard isInternetAvailable() else {
            completion(.failure(.noInternetConnection))
            return
        }

        let task = URLSession.shared.dataTask(with: url) { data, response, error in
            if let error = error {
                let nsError = error as NSError
                if nsError.domain == NSURLErrorDomain && nsError.code == NSURLErrorNotConnectedToInternet {
                    completion(.failure(.noInternetConnection))
                } else {
                    completion(.failure(.noData))
                }
                return
            }

            if let httpResponse = response as? HTTPURLResponse, !(200...299).contains(httpResponse.statusCode) {
                completion(.failure(.serverError(httpResponse.statusCode)))
                return
            }

            guard let data = data else {
                completion(.failure(.noData))
                return
            }

            do {
                let decoder = JSONDecoder()
                let decodedData = try decoder.decode(T.self, from: data)
                completion(.success(decodedData))
            } catch {
                completion(.failure(.decodingError(error)))
            }
        }

        task.resume()
    }

    private func isInternetAvailable() -> Bool {
        return true
    }
}

// MARK: - Location
class LocationManager: NSObject, ObservableObject, CLLocationManagerDelegate {
    private let manager = CLLocationManager()

    @Published var location: CLLocation?
    @Published var authorizationStatus: CLAuthorizationStatus

    private let officeLocation = CLLocation(latitude: 37.7749, longitude: -122.4194)
    private let acceptableRadius: CLLocationDistance = 100.0

    override init() {
        self.authorizationStatus = manager.authorizationStatus
        super.init()

        manager.delegate = self
        manager.desiredAccuracy = kCLLocationAccuracyBest
        manager.requestWhenInUseAuthorization()
        manager.startUpdatingLocation()
    }

    func requestPermission() {
        manager.requestWhenInUseAuthorization()
    }

    func isAtOffice() -> Bool {
        guard let currentLocation = location else {
            return false
        }

        let distance = currentLocation.distance(from: officeLocation)
        return distance <= acceptableRadius
    }

    func locationManager(_ manager: CLLocationManager, didChangeAuthorization status: CLAuthorizationStatus) {
        self.authorizationStatus = status
    }

    func locationManager(_ manager: CLLocationManager, didUpdateLocations locations: [CLLocation]) {
        self.location = locations.last
    }

    func locationManager(_ manager: CLLocationManager, didFailWithError error: Error) {
        print("Location Manager failed with error: \(error.localizedDescription)")
    }
}

// MARK: - Validation Utilities
enum ValidationError: Error, LocalizedError {
    case invalidEmail
    case emptyFields
    case weakPassword

    var errorDescription: String? {
        switch self {
        case .invalidEmail:
            return "Please enter a valid email address."
        case .emptyFields:
            return "All fields are required."
        case .weakPassword:
            return "Password must be at least 6 characters long."
        }
    }
}

func validateEmail(_ email: String) -> Bool {
    let emailRegEx = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,64}"
    let emailPred = NSPredicate(format:"SELF MATCHES %@", emailRegEx)
    return emailPred.evaluate(with: email)
}

// MARK: - Main Content View
struct ContentView: View {
    @State private var currentScreen: AppScreen = .signIn
    @State private var currentUserEmail: String?

    enum AppScreen {
        case signIn
        case signUp
        case home
    }

    var body: some View {
        NavigationStack {
            switch currentScreen {
            case .signIn:
                SignInView(currentScreen: $currentScreen, currentUserEmail: $currentUserEmail)
            case .signUp:
                SignUpView(currentScreen: $currentScreen)
            case .home:
                if let email = currentUserEmail {
                    HomeView(userEmail: email, currentScreen: $currentScreen)
                } else {
                    Text("Error: User not found")
                        .onAppear { currentScreen = .signIn }
                }
            }
        }
    }
}

// MARK: - Sign In View
struct SignInView: View {
    @Binding var currentScreen: ContentView.AppScreen
    @Binding var currentUserEmail: String?

    @Environment(\.modelContext) private var modelContext
    @Query private var users: [User]

    @State private var email = ""
    @State private var password = ""
    @State private var errorMessage = ""
    @State private var showError = false

    var body: some View {
        VStack(spacing: 20) {
            Text("Sign In")
                .font(.largeTitle)
                .bold()

            TextField("Email", text: $email)
                .textFieldStyle(.roundedBorder)
                .autocapitalization(.none)
                .keyboardType(.emailAddress)

            SecureField("Password", text: $password)
                .textFieldStyle(.roundedBorder)

            if showError {
                Text(errorMessage)
                    .foregroundColor(.red)
                    .font(.caption)
            }

            Button("Sign In") {
                signIn()
            }
            .buttonStyle(.borderedProminent)

            Button("Don't have an account? Sign Up") {
                currentScreen = .signUp
            }
            .padding(.top)
        }
        .padding()
    }

    private func signIn() {
        guard !email.isEmpty, !password.isEmpty else {
            showError("Please fill in all fields")
            return
        }

        if let user = users.first(where: { $0.email == email && $0.passwordHash == password }) {
            currentUserEmail = user.email
            currentScreen = .home
        } else {
            showError("Invalid email or password")
        }
    }

    private func showError(_ message: String) {
        errorMessage = message
        showError = true
    }
}

// MARK: - Sign Up View
struct SignUpView: View {
    @Binding var currentScreen: ContentView.AppScreen
    @Environment(\.modelContext) private var modelContext
    @Query private var users: [User]

    @State private var name = ""
    @State private var email = ""
    @State private var password = ""
    @State private var errorMessage = ""
    @State private var showError = false

    var body: some View {
        VStack(spacing: 20) {
            Text("Sign Up")
                .font(.largeTitle)
                .bold()

            TextField("Name", text: $name)
                .textFieldStyle(.roundedBorder)

            TextField("Email", text: $email)
                .textFieldStyle(.roundedBorder)
                .autocapitalization(.none)
                .keyboardType(.emailAddress)

            SecureField("Password", text: $password)
                .textFieldStyle(.roundedBorder)

            if showError {
                Text(errorMessage)
                    .foregroundColor(.red)
                    .font(.caption)
            }

            Button("Sign Up") {
                signUp()
            }
            .buttonStyle(.borderedProminent)

            Button("Already have an account? Sign In") {
                currentScreen = .signIn
            }
            .padding(.top)
        }
        .padding()
    }

    private func signUp() {
        do {
            try validateInput()

            if users.contains(where: { $0.email == email }) {
                showError("User with this email already exists")
                return
            }

            let newUser = User(email: email, name: name, passwordHash: password)
            modelContext.insert(newUser)

            // Successfully signed up, go back to sign in
            currentScreen = .signIn

        } catch let error as ValidationError {
            showError(error.localizedDescription)
        } catch {
            showError("An unknown error occurred")
        }
    }

    private func validateInput() throws {
        guard !name.isEmpty, !email.isEmpty, !password.isEmpty else {
            throw ValidationError.emptyFields
        }

        guard validateEmail(email) else {
            throw ValidationError.invalidEmail
        }

        guard password.count >= 6 else {
            throw ValidationError.weakPassword
        }
    }

    private func showError(_ message: String) {
        errorMessage = message
        showError = true
    }
}

// MARK: - Home View
struct HomeView: View {
    let userEmail: String
    @Binding var currentScreen: ContentView.AppScreen

    @Environment(\.modelContext) private var modelContext
    @Query private var users: [User]
    @Query private var attendanceRecords: [AttendanceRecord]

    @StateObject private var locationManager = LocationManager()

    @State private var alertMessage = ""
    @State private var showAlert = false
    @State private var showBiometricAlert = false
    @State private var pendingAction: AttendanceType?

    enum AttendanceType: String {
        case checkIn = "CheckIn"
        case checkOut = "CheckOut"
    }

    var currentUser: User? {
        users.first(where: { $0.email == userEmail })
    }

    var body: some View {
        VStack(spacing: 30) {
            Text("Welcome, \(currentUser?.name ?? "User")!")
                .font(.title)

            HStack(spacing: 20) {
                Button("Check In") {
                    handleAttendanceAction(.checkIn)
                }
                .buttonStyle(.borderedProminent)
                .tint(.green)

                Button("Check Out") {
                    handleAttendanceAction(.checkOut)
                }
                .buttonStyle(.borderedProminent)
                .tint(.orange)
            }

            // Network test button to demonstrate generics and error handling
            Button("Test Network Connection") {
                testNetwork()
            }
            .padding(.top)

            Spacer()

            Button("Sign Out") {
                currentScreen = .signIn
            }
            .foregroundColor(.red)
        }
        .padding()
        .alert(isPresented: $showAlert) {
            Alert(title: Text("Notice"), message: Text(alertMessage), dismissButton: .default(Text("OK")))
        }
        .alert("Register Biometrics", isPresented: $showBiometricAlert) {
            Button("Register", action: registerBiometric)
            Button("Cancel", role: .cancel, action: {})
        } message: {
            Text("You need to register your biometrics before checking in/out.")
        }
    }

    private func testNetwork() {
        // Example URL that might return error or data
        let url = URL(string: "https://jsonplaceholder.typicode.com/todos/1")!

        // Use generic network service
        NetworkService.shared.performRequest(url: url) { (result: Result<TodoResponse, NetworkError>) in
            DispatchQueue.main.async {
                switch result {
                case .success(_):
                    self.showMessage("Network connection successful!")
                case .failure(let error):
                    self.showMessage("Network Error: \(error.localizedDescription)")
                }
            }
        }
    }

    private func handleAttendanceAction(_ type: AttendanceType) {
        guard let user = currentUser else { return }

        // 1. Check if biometrics are registered
        if !user.isBiometricRegistered {
            pendingAction = type
            showBiometricAlert = true
            return
        }

        // 2. Authenticate with biometrics
        authenticateBiometric { success in
            DispatchQueue.main.async {
                if success {
                    // 3. Check if already checked in/out today
                    if hasRecordedToday(type: type, for: user) {
                        showMessage("You have already performed \(type.rawValue) today.")
                    } else {
                        recordAttendance(type: type, for: user)
                    }
                } else {
                    showMessage("Biometric authentication failed.")
                }
            }
        }
    }

    private func registerBiometric() {
        authenticateBiometric { success in
            DispatchQueue.main.async {
                if success {
                    if let user = currentUser {
                        user.isBiometricRegistered = true
                        showMessage("Biometric registered successfully!")
                        if let action = pendingAction {
                            handleAttendanceAction(action)
                            pendingAction = nil
                        }
                    }
                } else {
                    showMessage("Failed to register biometric.")
                }
            }
        }
    }

    private func authenticateBiometric(completion: @escaping (Bool) -> Void) {
        let context = LAContext()
        var error: NSError?

        if context.canEvaluatePolicy(.deviceOwnerAuthenticationWithBiometrics, error: &error) {
            let reason = "Authenticate to mark attendance"
            context.evaluatePolicy(.deviceOwnerAuthenticationWithBiometrics, localizedReason: reason) { success, authenticationError in
                completion(success)
            }
        } else {
            // No biometrics available on device
            print("Biometrics not available: \(error?.localizedDescription ?? "")")
            completion(false) // Or handle simulation for simulator
        }
    }

    private func hasRecordedToday(type: AttendanceType, for user: User) -> Bool {
        let calendar = Calendar.current
        let today = Date()

        return user.attendanceRecords.contains { record in
            record.type == type.rawValue && calendar.isDate(record.timestamp, inSameDayAs: today)
        }
    }

    private func recordAttendance(type: AttendanceType, for user: User) {
        let record = AttendanceRecord(timestamp: Date(), type: type.rawValue, user: user)
        modelContext.insert(record)
        showMessage("Successfully recorded \(type.rawValue) at \(Date().formatted(date: .omitted, time: .shortened))")
    }

    private func showMessage(_ message: String) {
        alertMessage = message
        showAlert = true
    }
}

// Helper for Dictionary Decoding in generic network request
struct TodoResponse: Decodable {
    let userId: Int?
    let id: Int?
    let title: String?
    let completed: Bool?
}
