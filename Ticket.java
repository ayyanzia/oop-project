import java.time.LocalTime;
import java.time.Duration;

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



