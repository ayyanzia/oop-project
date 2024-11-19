public class ParkingSpot {
    private String spotID; // unique identifier for a spot
    private Boolean occupancyStatus; // checks whether a spot is available


    public ParkingSpot(String spotID, Boolean occupancyStatus){
        this.spotID = spotID;
        this.occupancyStatus = occupancyStatus;
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
}
