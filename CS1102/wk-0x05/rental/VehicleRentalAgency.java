package rental;
import java.util.Scanner;


public class VehicleRentalAgency {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Welcome to the Vehicle Rental Agency!");
        System.out.println("What kind of vehicle would you like to register?");
        System.out.println("1. Car");
        System.out.println("2. Motorcycle");
        System.out.println("3. Truck");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                Car car = createCar(scanner);
                System.out.println(car.toString());
                break;
            case 2:
                Motorcycle motorcycle = createMotorcycle(scanner);
                System.out.println(motorcycle.toString());
                break;
            case 3:
                Truck truck = createTruck(scanner);
                System.out.println(truck.toString());
                break;
            default:
                System.out.println("Invalid choice.");
        }
        scanner.close();
    }

    private static Car createCar(Scanner scanner) {
        System.out.print("Enter the make of the car: ");
        String make = scanner.next();
        System.out.print("Enter the model of the car: ");
        String model = scanner.next();
        System.out.print("Enter the year of manufacture: ");
        int year = scanner.nextInt();
        System.out.print("Enter the number of doors: ");
        int doors = scanner.nextInt();
        System.out.print("Enter the fuel type (petrol, diesel, electric): ");
        String fuelType = scanner.next();
        
        Car car = new Car(make, model, year);
        car.setNumberOfDoors(doors);
        car.setFuelType(fuelType);
        return car;
    }

    private static Motorcycle createMotorcycle(Scanner scanner) {
        System.out.print("Enter the make of the motorcycle: ");
        String make = scanner.next();
        System.out.print("Enter the model of the motorcycle: ");
        String model = scanner.next();
        System.out.print("Enter the year of manufacture: ");
        int year = scanner.nextInt();
        System.out.print("Enter the number of wheels: ");
        int wheels = scanner.nextInt();
        System.out.print("Enter the motorcycle type (sport, cruiser, off-road): ");
        String type = scanner.next();
        
        Motorcycle motorcycle = new Motorcycle(make, model, year);
        motorcycle.setNumberOfWheels(wheels);
        motorcycle.setMotorcycleType(type);
        return motorcycle;
    }

    private static Truck createTruck(Scanner scanner) {
        System.out.print("Enter the make of the truck: ");
        String make = scanner.next();
        System.out.print("Enter the model of the truck: ");
        String model = scanner.next();
        System.out.print("Enter the year of manufacture: ");
        int year = scanner.nextInt();
        System.out.print("Enter the cargo capacity (in tons): ");
        double capacity = scanner.nextDouble();
        System.out.print("Enter the transmission type (manual, automatic): ");
        String transmissionType = scanner.next();
        
        Truck truck = new Truck(make, model, year);
        truck.setCargoCapacity(capacity);
        truck.setTransmissionType(transmissionType);
        return truck;
    }
}
