import java.time.LocalTime;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner=new Scanner(System.in);
        Vehicle car1 = new Vehicle("1234", "Car", LocalTime.now());
        System.out.println("Entry time :" + car1.getEntryTime());

        String formatternow = null;
        System.out.println("Current timestamp: " +formatternow);
        do {
            System.out.println("Welcome to the parking system: ");
            System.out.println("Enter choice by pressing given number: ");
            System.out.println("Press 1 to Enter your vehicle details in order to park");
            System.out.println("Press 2 to Find your vehicle");
            System.out.println("Press 3 to Find Remaining time for your parked vehicle");
            System.out.println("Press 4 to Exit");

            try {
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        System.out.println("Enter your vehicle details:");

                        break;
                    case 2:
                        System.out.println("Finding your vehicle...");

                        break;
                    case 3:
                        System.out.println("Checking remaining time...");

                        break;
                    case 4:
                        System.out.println("Exiting in 1 second...");
                        System.exit(1);
                        break;
                    default:
                        throw new InputMismatchException();
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Invalid input. Please enter a number between 1 and 4.");
                scanner.next(); // Clears invalid input
            }
        } while (true);
}
}
