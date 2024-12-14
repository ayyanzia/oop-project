import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.*;
import java.time.Duration;
public class Main {
    public static void main(String[] args) {// Initialize parking spots
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

        // Main interaction loop
        while (true) {
            System.out.println("\nWelcome to the Parking System!");
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
                    System.out.println("Enter Vehicle Type (Car/Bike/Truck):");
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
                    } else if (vehicleType.equalsIgnoreCase("Truck")) {
                        System.out.println("Enter cargo capacity in kg:");
                        double cargoCapacity = scanner.nextDouble();
                        vehicle = new Truck(vehicleNum, entryTime, cargoCapacity);
                    } else {
                        System.out.println("Invalid vehicle type.");
                        break;
                    }

                    try {
                        // Park vehicle
                        Ticket ticket = parkingLot.parkVehicle(vehicle);
                        System.out.println("Your vehicle is parked at Spot: " + ticket.getParkingSpot().getSpotID());
                        System.out.println("Ticket ID: " + ticket.getTicketID());
                    } catch (IllegalStateException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 2:
                    System.out.println("Enter your Vehicle Number:");
                    String vehicleNumber = scanner.next();
                    Ticket foundTicket = null;
                    for (Ticket ticket : parkingLot.getTickets()) {
                        if (ticket.getVehicle().getVehicleNum().equals(vehicleNumber)) {
                            foundTicket = ticket;
                            break;
                        }
                    }

                    if (foundTicket != null) {
                        System.out.println("Your vehicle is parked at Spot: " + foundTicket.getParkingSpot().getSpotID());
                    } else {
                        System.out.println("Vehicle not found in the parking lot.");
                    }
                    break;

                case 3:
                    System.out.println("Enter your Vehicle Number:");
                    String exitVehicleNumber = scanner.next();
                    Ticket exitTicket = null;
                    for (Ticket ticket : parkingLot.getTickets()) {
                        if (ticket.getVehicle().getVehicleNum().equals(exitVehicleNumber)) {
                            exitTicket = ticket;
                            break;
                        }
                    }

                    if (exitTicket != null) {
                        LocalTime exitTime = LocalTime.now();
                        exitTicket.setExitTime(exitTime);

                        // Calculate parking duration in minutes
                        String duration = exitTicket.calculateDuration();
                        System.out.println("Parking Duration: " + duration);

                        // Calculate charges
                        long parkedMinutes = Duration.between(exitTicket.getVehicle().getEntryTime(), exitTime).toMinutes();
                        double charges = exitTicket.getVehicle().calculateFees(parkedMinutes);

                        // Process payment
                        PaymentProcessing paymentProcessing = new PaymentProcessing();
                        paymentProcessing.processPayment(exitTicket.getVehicle(), charges);

                        // Free up the parking spot
                        exitTicket.getParkingSpot().setOccupancyStatus(false);
                        parkingLot.getTickets().remove(exitTicket);
                        System.out.println("Thank you for using our parking system. Have a nice day!");
                    } else {
                        System.out.println("Vehicle not found in the parking lot.");
                    }
                    break;

                default:
                    System.out.println("Invalid option. Please choose 1, 2, or 3.");
            }
        }
    }
}

