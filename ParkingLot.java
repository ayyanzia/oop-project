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
        Ticket ticket = new Ticket(UUID.randomUUID().toString(),vehicle,spot);
        tickets.add(ticket);
        return ticket;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }
}
