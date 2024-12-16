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





}
