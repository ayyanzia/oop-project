import java.time.LocalDateTime;
public class MaintenanceSystem {
        public void scheduleMaintenance(String task, LocalDateTime time) {
            System.out.println("Scheduled Maintenance Task: " + task);
            System.out.println("Scheduled Time: " + time);
        }

        public void performMaintenance(String task) {
            System.out.println("Performing Maintenance Task: " + task);
            System.out.println("Task Completed Successfully.");
        }

        public void logMaintenance(String task, LocalDateTime time) {
            System.out.println("Maintenance Log:");
            System.out.println("Task: " + task);
            System.out.println("Completed at: " + time);
        }
    }


