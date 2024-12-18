import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.time.Duration;

public class Main {
    public static void main(String[] args) {
        // Initializing parking spots
        List<ParkingSpot> spots = Arrays.asList(
                new ParkingSpot("A1", false, "Car"),
                new ParkingSpot("A2", false, "Car"),
                new ParkingSpot("B1", false, "Bike"),
                new ParkingSpot("B2", false, "Bike"),
                new ParkingSpot("C1", false, "Truck"),
                new ParkingSpot("C2", false, "Truck")

        );

        // Initializes parking lot
        ParkingLot parkingLot = new ParkingLot(spots);
        Scanner scanner = new Scanner(System.in);

        // Initialize sMaintenance and Safety systems
        MaintenanceSystem maintenanceSystem = new MaintenanceSystem();
        SafetySystem safetySystem = new SafetySystem(parkingLot);

        // Main loop of the interface
        while (true) {
            System.out.println("\nWelcome to the Parking System!");
            System.out.println("1. Customer Interface");
            System.out.println("2. Staff Interface");
            System.out.println("3. Exit");

            int choice = getValidatedInt(scanner, "Enter your choice: ", 1, 3); // method call to handle errors

            switch (choice) {
                case 1:
                    customerInterface(parkingLot, scanner);
                    break;

                case 2:
                    staffInterface(parkingLot, scanner, maintenanceSystem, safetySystem);
                    break;

                case 3:
                    System.out.println("Exiting system. Thank you for using the parking system.");
                    return;

                default:
                    System.out.println("Invalid option. Please choose 1, 2, or 3.");
            }
        }
    }

    // Customer interface logic
    private static void customerInterface(ParkingLot parkingLot, Scanner scanner) {
        while (true) {
            System.out.println("\nCustomer Interface:");
            System.out.println("1. Park a Vehicle");
            System.out.println("2. Find My Vehicle");
            System.out.println("3. Exit Customer interface");

            int choice = getValidatedInt(scanner, "Enter your choice: ", 1, 3);

            switch (choice) {
                case 1:
                    System.out.println("Enter Vehicle Number:");
                    String vehicleNum = scanner.next();
//                   if(vehicleNum.equals(vehicleNum))
//                   {
//                       System.out.println("This vehicle is already present and not allowed");
//                   break;}

                    System.out.println("Enter Vehicle Type (Car/Bike/Truck/Van/Lorry):");
                    String vehicleType = scanner.next();
                    LocalTime entryTime = LocalTime.now();

                    Vehicle vehicle;
                    if (vehicleType.equalsIgnoreCase("Car")) {
                        int numDoors = getValidatedInt(scanner, "Enter number of doors: ", 2, 4);
                        vehicle = new Car(vehicleNum, entryTime, numDoors);
                    } else if (vehicleType.equalsIgnoreCase("Bike")) {
                        boolean hasSideCar = getValidatedBoolean(scanner, "Does the bike have a sidecar? (true/false): ");
                        vehicle = new Bike(vehicleNum, entryTime, hasSideCar);
                    } else if (vehicleType.equalsIgnoreCase("Truck") || vehicleType.equalsIgnoreCase("Van") || vehicleType.equalsIgnoreCase("Lorry")) {
                        double cargoCapacity = getValidatedDouble(scanner, "Enter cargo capacity in kg: ", 0, 10000);
                        vehicle = new Truck(vehicleNum, entryTime, cargoCapacity);
                    } else {
                        System.out.println("Invalid vehicle type. Please enter Car, Bike, Truck, Van, or Lorry.");
                        break;
                    }

                    try {
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
                        System.out.println("Your vehicle is parked at Spot: " + foundTicket.getParkingSpot().getSpotID());
                        System.out.println("Ticket ID: " + foundTicket.getTicketID());

                        while (true) { // Keep asking until valid input is given
                            System.out.println("\nPress Enter to exit your vehicle from the parking lot or type 'back' to return to the Customer Interface.");
                            scanner.nextLine(); // Consume any leftover newlines
                            String input = scanner.nextLine().trim();

                            if (input.equalsIgnoreCase("back")) {
                                System.out.println("Returning to Customer Interface...");
                                break; // Go back to the customer menu
                            } else if (input.isEmpty()) {
                                // Process vehicle exit
                                LocalTime exitTime = LocalTime.now();
                                foundTicket.setExitTime(exitTime);

                                String duration = foundTicket.calculateDuration();
                                double fee = foundTicket.getVehicle().calculateFees(
                                        Duration.between(foundTicket.getVehicle().getEntryTime(), exitTime).toMinutes());

                                System.out.println("Your vehicle has been parked for: " + duration);
                                System.out.println("Total fee: " + fee + " PKR");

                                PaymentProcessing paymentProcessing = new PaymentProcessing();
                                paymentProcessing.processPayment(foundTicket.getVehicle(), fee);

                                foundTicket.getParkingSpot().setOccupancyStatus(false);
                                System.out.println("Your vehicle has exited the parking lot.");
                                break;
                            } else {
                                System.out.println("Invalid input. Please press Enter to exit or type 'back' to return.");
                            }
                        }
                    } else {
                        System.out.println("Vehicle not found in the parking lot. Please check your vehicle number and try again.");
                    }
                    break;

                case 3:
                    System.out.println("Exiting Customer Interface.");
                    return;

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

            int choice = getValidatedInt(scanner, "Enter your choice: ", 1, 6);

            switch (choice) {
                case 1:
                    System.out.println("Enter maintenance task:");
                    scanner.nextLine();
                    String task = scanner.nextLine();
                    LocalDate date = getValidatedDate(scanner, "Enter maintenance date (YYYY-MM-DD): ");
                    maintenanceSystem.scheduleMaintenance(task, date);
                    break;

                case 2:
                    System.out.println("Write the task on which you want to perform maintenance:");
                    scanner.nextLine();
                    String performTask = scanner.nextLine();    //takes string input
                    maintenanceSystem.performMaintenance(performTask); //maintainance system class method called
                    break;

                case 3:
                    System.out.println("Enter maintenance task to log:");
                    scanner.nextLine(); // adds new line or skips one takes input in next line
                    String logTask = scanner.nextLine();
                    LocalDate logDate = getValidatedDate(scanner, "Enter completion date (YYYY-MM-DD): ");
                    maintenanceSystem.logMaintenance(logTask, logDate);
                    break;

                case 4:
                    safetySystem.handleFire();
                    break;

                case 5:
                    System.out.println("Enter vehicle number for theft report:");
                    String vehicleNum = scanner.next();
                    Vehicle vehicle = null; //obj value null
                    for (Ticket ticket : parkingLot.getTickets()) {
                        if (ticket.getVehicle().getVehicleNum().equals(vehicleNum)) {
                            vehicle = ticket.getVehicle();
                            break;
                        }
                    }
                    if (vehicle != null) {  //if it is null, means no vehicle present
                        safetySystem.handleTheft(vehicle);
                    } else {
                        System.out.println("Vehicle not found in the parking lot.");
                    }
                    break;

                case 6:
                    System.out.println("Exiting Staff Interface.");
                    return;

                default:
                    System.out.println("Invalid option. Please choose 1, 2, 3, 4, 5, or 6.");
            }
        }
    }

    // Helper Methods for to handle errors
    private static int getValidatedInt(Scanner scanner, String prompt, int min, int max) {
        while (true) {
            try {
                System.out.print(prompt);
                int input = scanner.nextInt();
                if (input >= min && input <= max) {
                    return input;
                } else {
                    System.out.println("Input must be between " + min + " and " + max + ".");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Clear invalid input
            }
        }
    }

    private static double getValidatedDouble(Scanner scanner, String prompt, double min, double max) {
        while (true) {
            try {
                System.out.print(prompt);
                double input = scanner.nextDouble();
                if (input >= min && input <= max) {
                    return input;
                } else {
                    System.out.println("Input must be between " + min + " and " + max + ".");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.next(); // Clear invalid input
            }
        }
    }

    private static boolean getValidatedBoolean(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextBoolean()) {
                return scanner.nextBoolean();
            } else {
                System.out.println("Invalid input. Please enter true or false.");
                scanner.next(); // Clear invalid input
            }
        }
    }

    private static LocalDate getValidatedDate(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return LocalDate.parse(scanner.next().trim());
            } catch (Exception e) {
                System.out.println("Invalid date format. Please enter a valid date in the format YYYY-MM-DD.");
            }
        }
    }
}
