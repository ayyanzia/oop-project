import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ParkingLot {
    private List <ParkingSpot> spots;
    private List<Ticket> tickets;

    public ParkingLot(List<ParkingSpot> spots) {
        this.spots = spots;
        this.tickets = new ArrayList<>();
    }

    public ParkingSpot assignSpot(Vehicle vehicle) {
        for (ParkingSpot spot : spots) {
            if (!spot.getOccupancyStatus() && spot.isCompatibleWith(vehicle)) {
                spot.setOccupancyStatus(true);
                return spot;
            }
        }
        throw new IllegalStateException("No spot available for this vehicle type.");
    }

    public Ticket parkVehicle(Vehicle vehicle){
        ParkingSpot spot = assignSpot(vehicle);
        Ticket ticket = new Ticket(UUID.randomUUID().toString(),vehicle,spot);
        tickets.add(ticket);
        return ticket;
    }

}
