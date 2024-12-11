import java.time.LocalTime;

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
        return ((double) durationInMinutes /60) * ratePerHour;
    }
}


