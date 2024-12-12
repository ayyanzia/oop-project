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
        System.out.println("WARNING " + message);
        System.out.println("Vehicle " + vehicle.getVehicleType() + " (" + vehicle.getVehicleNum() + ")" );
        System.out.println("SpotID: " + parkingSpot.getSpotID());
    }

}
