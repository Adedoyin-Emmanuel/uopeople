//
//  ContentView.swift
//  bmi-app
//
//  Created by Mac on 11/03/2026.
//

import SwiftUI

// MARK: - Enum for BMI Category

enum BMICategory {
    case underweight
    case normal
    case overweight

    var description: String {
        switch self {
        case .underweight:
            return "You are underweight"
        case .normal:
            return "Your weight is normal"
        case .overweight:
            return "You are overweight"
        }
    }

    var color: Color {
        switch self {
        case .underweight:
            return .red
        case .normal:
            return .green
        case .overweight:
            return .red
        }
    }
}

// MARK: - Enum for Gender

enum Gender: String, CaseIterable {
    case male = "Male"
    case female = "Female"
}

// MARK: - BMI Calculator Class

class BMICalculator {
    var weight: Double // in kilograms
    var height: Double // in centimeters
    var gender: Gender

    init(weight: Double, height: Double, gender: Gender) {
        self.weight = weight
        self.height = height
        self.gender = gender
    }

    func calculateBMI() -> Double {
        let heightInMeters = height / 100.0
        guard heightInMeters > 0 else { return 0 }
        return weight / (heightInMeters * heightInMeters)
    }

    func getCategory() -> BMICategory {
        let bmi = calculateBMI()
        switch bmi {
        case ..<18.5:
            return .underweight
        case 18.5..<25.0:
            return .normal
        default:
            return .overweight
        }
    }
}

// MARK: - Input View

struct ContentView: View {
    @State private var weightText: String = ""
    @State private var heightText: String = ""
    @State private var selectedGender: Gender = .male
    @State private var showResult = false
    @State private var bmiValue: Double = 0
    @State private var bmiCategory: BMICategory = .normal

    var body: some View {
        NavigationStack {
            VStack(spacing: 24) {
                Text("BMI Calculator")
                    .font(.largeTitle)
                    .fontWeight(.bold)

                VStack(alignment: .leading, spacing: 16) {
                    // Gender Picker
                    VStack(alignment: .leading, spacing: 8) {
                        Text("Gender")
                            .font(.headline)
                        Picker("Gender", selection: $selectedGender) {
                            ForEach(Gender.allCases, id: \.self) { gender in
                                Text(gender.rawValue).tag(gender)
                            }
                        }
                        .pickerStyle(.segmented)
                    }

                    // Weight Input
                    VStack(alignment: .leading, spacing: 8) {
                        Text("Weight (kg)")
                            .font(.headline)
                        TextField("Enter your weight", text: $weightText)
                            .keyboardType(.decimalPad)
                            .textFieldStyle(.roundedBorder)
                    }

                    // Height Input
                    VStack(alignment: .leading, spacing: 8) {
                        Text("Height (cm)")
                            .font(.headline)
                        TextField("Enter your height", text: $heightText)
                            .keyboardType(.decimalPad)
                            .textFieldStyle(.roundedBorder)
                    }
                }
                .padding()

                // Calculate Button
                Button(action: calculateBMI) {
                    Text("Calculate BMI")
                        .font(.title2)
                        .fontWeight(.semibold)
                        .foregroundColor(.white)
                        .frame(maxWidth: .infinity)
                        .padding()
                        .background(Color.blue)
                        .cornerRadius(12)
                }
                .padding(.horizontal)

                Spacer()
            }
            .padding()
            .navigationDestination(isPresented: $showResult) {
                ResultView(bmiValue: bmiValue, category: bmiCategory)
            }
        }
    }

    private func calculateBMI() {
        guard let weight = Double(weightText),
              let height = Double(heightText),
              weight > 0, height > 0 else {
            return
        }

        let calculator = BMICalculator(
            weight: weight,
            height: height,
            gender: selectedGender
        )

        bmiValue = calculator.calculateBMI()
        bmiCategory = calculator.getCategory()
        showResult = true
    }
}

// MARK: - Result View

struct ResultView: View {
    let bmiValue: Double
    let category: BMICategory

    var body: some View {
        VStack(spacing: 32) {
            Text("Your BMI Result")
                .font(.largeTitle)
                .fontWeight(.bold)

            Text(String(format: "%.1f", bmiValue))
                .font(.system(size: 72, weight: .bold))
                .foregroundColor(category.color)

            Text(category.description)
                .font(.title2)
                .fontWeight(.semibold)
                .foregroundColor(category.color)

            // BMI Range Info
            VStack(alignment: .leading, spacing: 8) {
                HStack {
                    Circle().fill(.red).frame(width: 12, height: 12)
                    Text("Underweight: BMI < 18.5")
                }
                HStack {
                    Circle().fill(.green).frame(width: 12, height: 12)
                    Text("Normal: BMI 18.5 – 24.9")
                }
                HStack {
                    Circle().fill(.red).frame(width: 12, height: 12)
                    Text("Overweight: BMI ≥ 25.0")
                }
            }
            .font(.subheadline)
            .padding()
            .background(Color(.systemGray6))
            .cornerRadius(12)

            Spacer()
        }
        .padding()
    }
}

#Preview {
    ContentView()
}
