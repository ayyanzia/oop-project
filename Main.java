import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.*;
import java.time.Duration;
public class Main {
    public static void main(String[] args) {
        // Initialize parking spots
        List<ParkingSpot> spots = Arrays.asList(
                new ParkingSpot("A1", false, "Car"),
                new ParkingSpot("A2", false, "Car"),
                new ParkingSpot("B1", false, "Bike"),
                new ParkingSpot("C1", false, "Truck")
        );

        // Initialize parking lot
        ParkingLot parkingLot = new ParkingLot(spots);
        Scanner scanner = new Scanner(System.in);

        // Initialize Maintenance and Safety systems
        MaintenanceSystem maintenanceSystem = new MaintenanceSystem();
        SafetySystem safetySystem = new SafetySystem(parkingLot);

        // Main interaction loop
        while (true) {
            System.out.println("\nWelcome to the Parking System!");
            System.out.println("1. Customer Interface");
            System.out.println("2. Staff Interface");
            System.out.println("3. Exit");

            int choice;
            try {
                System.out.print("Enter your choice: ");
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 3.");
                scanner.next(); // clear the input
                continue;
            }

            switch (choice) {
                case 1:
                    // Customer Interface (unchanged from original logic)
                    customerInterface(parkingLot, scanner);
                    break;

                case 2:
                    // Staff Interface
                    staffInterface(parkingLot, scanner, maintenanceSystem, safetySystem);
                    break;

                case 3:
                    System.out.println("Exiting system. Thank you for using the parking system.");
                    return; // Exit the program

                default:
                    System.out.println("Invalid option. Please choose 1, 2, or 3.");
            }
        }
    }

    // Customer Interface Logic
    private static void customerInterface(ParkingLot parkingLot, Scanner scanner) {
        while (true) {
            System.out.println("\nCustomer Interface:");
            System.out.println("1. Park a Vehicle");
            System.out.println("2. Find My Vehicle");
            System.out.println("3. Exit the System");

            int choice;
            try {
                System.out.print("Enter your choice: ");
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 3.");
                scanner.next(); // clear the input
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.println("Enter Vehicle Number:");
                    String vehicleNum = scanner.next();
                    System.out.println("Enter Vehicle Type (Car/Bike/Truck/Van/Lorry):");
                    String vehicleType = scanner.next();
                    LocalTime entryTime = LocalTime.now();

                    // Create vehicle based on type
                    Vehicle vehicle;
                    if (vehicleType.equalsIgnoreCase("Car")) {
                        System.out.println("Enter number of doors:");
                        int numDoors = scanner.nextInt();
                        vehicle = new Car(vehicleNum, entryTime, numDoors);
                    } else if (vehicleType.equalsIgnoreCase("Bike")) {
                        System.out.println("Does the bike have a sidecar? (true/false):");
                        boolean hasSideCar = scanner.nextBoolean();
                        vehicle = new Bike(vehicleNum, entryTime, hasSideCar);
                    } else if (vehicleType.equalsIgnoreCase("Truck") || vehicleType.equalsIgnoreCase("Van") || vehicleType.equalsIgnoreCase("Lorry")) {
                        // Existing logic for Truck
                        System.out.println("Enter cargo capacity in kg to one decimal place:");
                        double cargoCapacity = scanner.nextDouble();
                        vehicle = new Truck(vehicleNum, entryTime, cargoCapacity);
                    } else {
                        System.out.println("Invalid vehicle type. Please enter Car, Bike, Truck, Van, or Lorry.");
                        break;
                    }

                    try {
                        // Park vehicle
                        Ticket ticket = parkingLot.parkVehicle(vehicle);
                        System.out.println("Your vehicle is parked at Spot: " + ticket.getParkingSpot().getSpotID());
                        System.out.println("Ticket ID: " + ticket.getTicketID());
                    } catch (IllegalStateException e) {
                        System.out.println("Sorry, the parking lot is currently full. Please try again later.");
                    }
                    break;

                case 2:
                    System.out.println("Enter your Vehicle Number:");
                    String vehicleNumber = scanner.next();
                    Ticket foundTicket = null;
                    for (Ticket ticket : parkingLot.getTickets()) {
                        if (ticket.getVehicle().getVehicleNum().equalsIgnoreCase(vehicleNumber)) {
                            foundTicket = ticket;
                            break;
                        }
                    }

                    if (foundTicket != null) {
                        System.out.println("Your vehicle is parked at the compatible Spot: " + foundTicket.getParkingSpot().getSpotID());
                        System.out.println("Ticket ID: " + foundTicket.getTicketID());

                        // Calculate the parking fee when the vehicle exits
                        System.out.println("Press Enter to exit your vehicle from the parking lot...");
                        scanner.nextLine(); // Consume any extra new line character
                        scanner.nextLine(); // Wait for user to press enter

                        // Set the exit time to now
                        LocalTime exitTime = LocalTime.now();
                        foundTicket.setExitTime(exitTime);

                        // Calculate duration and fees
                        String duration = foundTicket.calculateDuration();
                        double fee = foundTicket.getVehicle().calculateFees(Duration.between(foundTicket.getVehicle().getEntryTime(), exitTime).toMinutes());
                        System.out.println("Your vehicle has been parked for: " + duration);
                        System.out.println("Total fee: " + fee + " PKR");

                        // Process payment
                        PaymentProcessing paymentProcessing = new PaymentProcessing();
                        paymentProcessing.processPayment(foundTicket.getVehicle(), fee);

                        // Free the parking spot
                        foundTicket.getParkingSpot().setOccupancyStatus(false);
                        System.out.println("Your vehicle has exited the parking lot.");
                    } else {
                        System.out.println("Vehicle not found in the parking lot.");
                    }
                    break;

                case 3:
                    System.out.println("Exiting Customer Interface.");
                    return; // Exit the customer interface

                default:
                    System.out.println("Invalid option. Please choose 1, 2, or 3.");
            }
        }
    }

    // Staff Interface Logic
    private static void staffInterface(ParkingLot parkingLot, Scanner scanner, MaintenanceSystem maintenanceSystem, SafetySystem safetySystem) {
        while (true) {
            System.out.println("\nStaff Interface:");
            System.out.println("1. Schedule Maintenance");
            System.out.println("2. Perform Maintenance");
            System.out.println("3. Log Maintenance");
            System.out.println("4. Handle Fire Emergency");
            System.out.println("5. Handle Theft Emergency");
            System.out.println("6. Exit Staff Interface");

            int choice;
            try {
                System.out.print("Enter your choice: ");
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 6.");
                scanner.next(); // clear the input
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.println("Enter maintenance task:");
                    scanner.nextLine(); // Consume newline
                    String task = scanner.nextLine();
                    System.out.println("Enter maintenance date (YYYY-MM-DD):");
                    String timeInput = scanner.nextLine().trim();  // Trim the input to remove extra spaces
                    LocalDate date = LocalDate.parse(timeInput);    // Use LocalDate for just the date
                    maintenanceSystem.scheduleMaintenance(task, date);
                    break;

                case 2:
                    System.out.println("Enter maintenance task to perform:");
                    scanner.nextLine(); // Consume newline
                    String performTask = scanner.nextLine();
                    maintenanceSystem.performMaintenance(performTask);
                    break;

                case 3:
                    System.out.println("Enter maintenance task to log:");
                    scanner.nextLine(); // Consume newline
                    String logTask = scanner.nextLine();
                    System.out.println("Enter completion date (YYYY-MM-DD):");
                    String logTimeInput = scanner.nextLine();
                    LocalDate logDate = LocalDate.parse(logTimeInput);  // Use LocalDate for just the date
                    maintenanceSystem.logMaintenance(logTask, logDate);
                    break;

                case 4:
                    safetySystem.handleFire();
                    break;

                case 5:
                    System.out.println("Enter vehicle number for theft report:");
                    String vehicleNum = scanner.next();
                    Vehicle vehicle = null;
                    for (Ticket ticket : parkingLot.getTickets()) {
                        if (ticket.getVehicle().getVehicleNum().equals(vehicleNum)) {
                            vehicle = ticket.getVehicle();
                            break;
                        }
                    }
                    if (vehicle != null) {
                        safetySystem.handleTheft(vehicle);
                    } else {
                        System.out.println("Vehicle not found in the parking lot.");
                    }
                    break;

                case 6:
                    System.out.println("Exiting Staff Interface.");
                    return; // Exit the staff interface

                default:
                    System.out.println("Invalid option. Please choose 1, 2, 3, 4, 5, or 6.");
            }
        }
    }
}
