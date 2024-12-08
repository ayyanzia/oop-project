import java.time.LocalTime;
public abstract class Vehicle {
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



