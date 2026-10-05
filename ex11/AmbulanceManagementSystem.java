import java.util.*;

public class AmbulanceManagementSystem {

    enum Status { AVAILABLE, BUSY, MAINTENANCE }

    static class Ambulance {
        int id;
        String plateNo, driver;
        int zone;               // current location (zone number)
        Status status = Status.AVAILABLE;

        Ambulance(int id, String plateNo, String driver, int zone) {
            this.id = id; this.plateNo = plateNo; this.driver = driver; this.zone = zone;
        }

        public String toString() {
            return String.format("ID:%d | %s | Driver: %s | Zone: %d | %s",
                    id, plateNo, driver, zone, status);
        }
    }

    static class Request {
        int id;
        String patient, emergency;
        int zone;
        Ambulance ambulance;    // null if none was available
        boolean completed;

        Request(int id, String patient, int zone, String emergency) {
            this.id = id; this.patient = patient; this.zone = zone; this.emergency = emergency;
        }

        public String toString() {
            String state = ambulance == null ? "PENDING (no ambulance)"
                         : completed ? "COMPLETED" : "IN PROGRESS";
            return String.format("Req:%d | %s | Zone: %d | %s | Ambulance: %s | %s",
                    id, patient, zone, emergency,
                    ambulance == null ? "-" : ambulance.plateNo, state);
        }
    }

    static List<Ambulance> ambulances = new ArrayList<>();
    static List<Request> requests = new ArrayList<>();
    static int ambId = 1, reqId = 1;
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        // sample data
        ambulances.add(new Ambulance(ambId++, "TN-01-A-1001", "Ravi", 2));
        ambulances.add(new Ambulance(ambId++, "TN-01-A-1002", "Kumar", 7));

        int choice;
        do {
            System.out.println("\n===== AMBULANCE MANAGEMENT SYSTEM =====");
            System.out.println("1. Add ambulance");
            System.out.println("2. View ambulances");
            System.out.println("3. Book emergency (auto-dispatch)");
            System.out.println("4. Complete trip");
            System.out.println("5. View requests");
            System.out.println("6. Change ambulance status");
            System.out.println("0. Exit");
            choice = readInt("Choose: ");

            switch (choice) {
                case 1 -> addAmbulance();
                case 2 -> ambulances.forEach(System.out::println);
                case 3 -> bookEmergency();
                case 4 -> completeTrip();
                case 5 -> {
                    if (requests.isEmpty()) System.out.println("No requests yet.");
                    requests.forEach(System.out::println);
                }
                case 6 -> changeStatus();
                case 0 -> System.out.println("Goodbye!");
                default -> System.out.println("Invalid choice.");
            }
        } while (choice != 0);
    }

    static void addAmbulance() {
        System.out.print("Plate number: ");
        String plate = sc.nextLine();
        System.out.print("Driver name: ");
        String driver = sc.nextLine();
        int zone = readInt("Current zone (number): ");
        ambulances.add(new Ambulance(ambId++, plate, driver, zone));
        System.out.println("Ambulance added.");
    }

    static void bookEmergency() {
        System.out.print("Patient name: ");
        String name = sc.nextLine();
        int zone = readInt("Patient zone (number): ");
        System.out.print("Emergency type (accident/cardiac/other): ");
        String type = sc.nextLine();

        Request r = new Request(reqId++, name, zone, type);
        requests.add(r);

        // nearest available ambulance = smallest |zone difference|
        Ambulance nearest = null;
        for (Ambulance a : ambulances) {
            if (a.status != Status.AVAILABLE) continue;
            if (nearest == null || Math.abs(a.zone - zone) < Math.abs(nearest.zone - zone))
                nearest = a;
        }

        if (nearest == null) {
            System.out.println("No ambulance available. Request #" + r.id + " is pending.");
        } else {
            nearest.status = Status.BUSY;
            r.ambulance = nearest;
            System.out.println("Dispatched " + nearest.plateNo + " (driver " + nearest.driver
                    + ") to " + name + ". Distance: " + Math.abs(nearest.zone - zone) + " zones.");
        }
    }

    static void completeTrip() {
        int id = readInt("Request ID: ");
        for (Request r : requests) {
            if (r.id == id && r.ambulance != null && !r.completed) {
                r.completed = true;
                r.ambulance.status = Status.AVAILABLE;
                r.ambulance.zone = r.zone;   // ambulance is now at the patient's zone
                System.out.println("Trip completed. " + r.ambulance.plateNo + " is available again.");
                return;
            }
        }
        System.out.println("No active trip found with that ID.");
    }

    static void changeStatus() {
        int id = readInt("Ambulance ID: ");
        for (Ambulance a : ambulances) {
            if (a.id == id) {
                if (a.status == Status.BUSY) {
                    System.out.println("Ambulance is on a trip. Complete the trip first.");
                    return;
                }
                a.status = (a.status == Status.AVAILABLE) ? Status.MAINTENANCE : Status.AVAILABLE;
                System.out.println("Status changed to " + a.status);
                return;
            }
        }
        System.out.println("Ambulance not found.");
    }

    static int readInt(String msg) {
        while (true) {
            System.out.print(msg);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Enter a valid number.");
            }
        }
    }
}
