public class ParkingSpot {
    private String spotID; // unique identifier for a spot
    private Boolean occupancyStatus; // checks whether a spot is available
    private String spotType;


    public ParkingSpot(String spotID, Boolean occupancyStatus, String spotType){
        this.spotID = spotID;
        this.occupancyStatus = false;
        this.spotType = spotType;
    }

    public String getSpotID() {
        return spotID;
    }

    public void setSpotID(String spotID) {
        this.spotID = spotID;
    }

    public Boolean getOccupancyStatus() {
        return occupancyStatus;
    }

    public void setOccupancyStatus(Boolean occupancyStatus) {
        this.occupancyStatus = occupancyStatus;
    }

    public String getSpotType() {
        return spotType;
    }

    public void setSpotType(String spotType) {
        this.spotType = spotType;
    }

    public boolean isCompatibleWith(Vehicle vehicle){
        return this.spotType.equalsIgnoreCase(vehicle.getVehicleType());
    }



}
