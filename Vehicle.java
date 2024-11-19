import java.time.LocalTime;
public class Vehicle {
    private String vehicleNum;
    private String vehicleType;
    private LocalTime entryTime;

    public Vehicle(String vehicleNum, String vehicletype, LocalTime entryTime){
        this.vehicleNum = vehicleNum;
        this.vehicleType = vehicletype;
        this.entryTime = entryTime;
    }

    public String getVehicleNum() {
        return vehicleNum;
    }

    public void setVehicleNum(String vehicleNum) {
        this.vehicleNum = vehicleNum;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public LocalTime getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(LocalTime entryTime) {
        this.entryTime = entryTime;
    }
}
