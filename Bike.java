import java.time.LocalTime;

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
}
