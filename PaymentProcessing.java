public class PaymentProcessing {
    private Vehicle vehicle;
    private double fee;

    public void processPayment(Vehicle vehicle, double fee){
        System.out.println("Processing payment for vehicle " + vehicle.getVehicleNum());
        System.out.println("Amount paid " + fee + " PKR");
        System.out.println("Thank you for coming. We look forward to serve you again.");
    }
}
