import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SE {

    public class Bike extends Vehicle{
        private boolean hasSideCar;

        public Bike(String vehicleNum, LocalTime entryTime, boolean hasSideCar) {
            super(vehicleNum, entryTime);
            this.hasSideCar = hasSideCar;
        }

        public boolean isHasSideCar(){
            return hasSideCar;
        }

        @Override
        public String getVehicleType() {
            return "Bike";
        }

        @Override
        public double calculateFees(long durationInMinutes) {
            double ratePerHour = 30;
            if (durationInMinutes < 60 ){
                durationInMinutes = 60;
            }
            return ((double)durationInMinutes/60) * ratePerHour;
        }
    }

    public interface Bill {
        double calculateFees(long durationInMinutes);
    }


    public class Car extends Vehicle {
        private int numDoors;

        public Car(String vehicleNum, LocalTime entryTime, int numDoors) {
            super(vehicleNum, entryTime);
            this.numDoors = numDoors;
        }

        public int getNumDoors(){
            return numDoors;
        }

        @Override
        public String getVehicleType() {
            return "Car";
        }

        @Override
        public double calculateFees(long durationInMinutes) {
            double ratePerHour = 50;
            if (durationInMinutes < 60 ){
                durationInMinutes = 60;
            }
            return ((double) durationInMinutes /60) * ratePerHour;
        }
    }

    public class MaintenanceSystem {
        public void scheduleMaintenance(String task, LocalDate date) {
            System.out.println("Scheduled Maintenance Task: " + task);
            System.out.println("Scheduled Time: " + date);
        }

        public void performMaintenance(String task) {
            System.out.println("Performing Maintenance Task: " + task);
            System.out.println("Task Completed Successfully.");
        }

        public void logMaintenance(String task, LocalDate date) {
            System.out.println("Maintenance Log:");
            System.out.println("Task: " + task);
            System.out.println("Completed at: " + date);
        }
    }

    public class ParkingLot {
        private List<ParkingSpot> spots;
        private List<Ticket> tickets;

        public ParkingLot(List<ParkingSpot> spots) {
            this.spots = spots;
            this.tickets = new ArrayList<>();
        }

        public List<ParkingSpot> getSpots() {
            return spots;
        }


        public ParkingSpot assignSpot(Vehicle vehicle) {
            for (ParkingSpot spot : spots) {
                if (!spot.getOccupancyStatus()) {
                    if(!spot.isCompatibleWith(vehicle)){
                        Warning warning = new Warning("Incompatible vehicle type for this spot. " , vehicle, spot);
                        warning.displayWarning();
                        continue; // look for another spot.
                    }
                    spot.setOccupancyStatus(true);
                    return spot;
                }

            }
            throw new IllegalStateException("We are sorry. No spot available for this vehicle type as the parking lot is full.");
        }

        public Ticket parkVehicle(Vehicle vehicle){
            ParkingSpot spot = assignSpot(vehicle);
            Ticket ticket = new Ticket(UUID.randomUUID().toString(),vehicle,spot); // using UUID to generate random ticketID and assigning vehicle and spot.
            tickets.add(ticket);
            return ticket;
        }

        public List<Ticket> getTickets() {
            return tickets;
        }
    }

    public class PaymentProcessing {
        private Vehicle vehicle;
        private double fee;

        public void processPayment(Vehicle vehicle, double fee){
            System.out.println("Processing payment for vehicle " + vehicle.getVehicleNum());
            System.out.println("Amount paid " + fee + " PKR");
            System.out.println("Thank you for coming. We look forward to serve you again.");
        }
    }

    public class SafetySystem {
        private ParkingLot parkingLot;

        public SafetySystem(ParkingLot parkingLot){
            this.parkingLot = parkingLot;
        }

        public void handleFire(){
            System.out.println("FIRE DETECTED! Starting safety protocol.");
            evacuateVehicles();
            alertFireDepartment();
        }

        public void handleTheft(Vehicle vehicle){
            System.out.println("THEFT DETECTED for vehicle " + vehicle.getVehicleNum());
            alertSecurity(vehicle);
            alertPolice(vehicle);
            lockParkingLot();
        }

        public void evacuateVehicles(){
            for (ParkingSpot spot : parkingLot.getSpots()){
                if(spot.getOccupancyStatus()){
                    System.out.println("Please whoever owns  " + spot.getSpotID() + " vehicle." + " start evacuating");
                    spot.setOccupancyStatus(false);
                }
            }
            System.out.println("All vehicles start evacuating");
        }

        public void alertFireDepartment(){
            System.out.println("Fire Department alerted. Help is on the way.");
        }

        public void alertSecurity(Vehicle vehicle){
            System.out.println("Security alerted to monitor theft of vehicle " + vehicle.getVehicleNum());
        }

        public void alertPolice(Vehicle vehicle){
            System.out.println("Police has been called and is on its way for theft of vehicle  " + vehicle.getVehicleNum());
        }

        public void lockParkingLot(){
            System.out.println("Parking Lot has been locked due to security concerns. All entry and exit points are locked.");
        }



        public class Ticket {
            private String ticketID;
            private Vehicle vehicle;
            private ParkingSpot parkingSpot;
            private LocalTime exitTime;

            public Ticket(String ticketID, Vehicle vehicle, ParkingSpot parkingSpot) {
                this.ticketID = ticketID;
                this.vehicle = vehicle;
                this.parkingSpot = parkingSpot;
            }

            public String getTicketID() {
                return ticketID;
            }

            public void setTicketID(String ticketID) {
                this.ticketID = ticketID;
            }

            public Vehicle getVehicle() {
                return vehicle;
            }

            public void setVehicle(Vehicle vehicle) {
                this.vehicle = vehicle;
            }

            public ParkingSpot getParkingSpot() {
                return parkingSpot;
            }

            public void setParkingSpot(ParkingSpot parkingSpot) {
                this.parkingSpot = parkingSpot;
            }

            public void setExitTime(LocalTime exitTime){
                this.exitTime = exitTime;
            }

            public LocalTime getExitTime(){
                return exitTime;
            }

            public String calculateDuration() {
                if (vehicle.getEntryTime()!= null && exitTime != null) {
                    Duration duration = Duration.between(vehicle.getEntryTime(), exitTime); // Use Vehicle's entry time and exit time
                    long hoursTime = duration.toHours();
                    long minutesTime = duration.toMinutes();
                    return hoursTime + "hours, " + minutesTime + " minutes." ;


                } else {
                    throw new IllegalStateException("Entry or exit timestamp not set.");
                }
            }
        }


        public class Truck extends Vehicle{
            private double cargoCapacity;

            public Truck(String vehicleNum, LocalTime entryTime, double cargoCapacity) {
                super(vehicleNum, entryTime);
                this.cargoCapacity = cargoCapacity;
            }

            public double getCargoCapacity(){
                return cargoCapacity;
            }

            @Override
            public String getVehicleType() {
                return "Truck";
            }

            public double calculateFees(long durationInMinutes) {
                double ratePerHour = 100;
                if (durationInMinutes < 60 ){
                    durationInMinutes = 60;
                }
                return ((double)durationInMinutes/60) * ratePerHour;
            }
        }

       
        public abstract class Vehicle implements Bill {
            protected String vehicleNum;
            protected LocalTime entryTime;

            public Vehicle(String vehicleNum, LocalTime entryTime){
                this.vehicleNum = vehicleNum;
                this.entryTime = entryTime;
            }


            public String getVehicleNum() {
                return vehicleNum;
            }

            public void setVehicleNum(String vehicleNum) {
                this.vehicleNum = vehicleNum;
            }

            public LocalTime getEntryTime() {
                return entryTime;
            }

            public void setEntryTime(LocalTime entryTime) {
                this.entryTime = entryTime;
            }

            public abstract String getVehicleType();
        }
    }

    public class Warning {
        private String message;
        private Vehicle vehicle;
        private ParkingSpot parkingSpot;

        public Warning(String message, Vehicle vehicle, ParkingSpot parkingSpot) {
            this.message = message;
            this.vehicle = vehicle;
            this.parkingSpot = parkingSpot;
        }

        public String getMessage() {
            return message;
        }

        public Vehicle getVehicle() {
            return vehicle;
        }

        public ParkingSpot getParkingSpot() {
            return parkingSpot;
        }

        public void displayWarning(){
            System.out.println("SpotID: " + parkingSpot.getSpotID());
            System.out.println(message);
            System.out.println("Vehicle " + vehicle.getVehicleType() + " (" + vehicle.getVehicleNum() + ")" );

        }
    }

}
