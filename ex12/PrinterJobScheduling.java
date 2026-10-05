import java.util.*;

class PrinterJob {
    private int jobId;
    private String userName;
    private String status;

    PrinterJob(int jobId, String userName) {
        this.jobId = jobId;
        this.userName = userName;
        this.status = "Waiting";
    }

    int getJobId() {
        return jobId;
    }

    String getUserName() {
        return userName;
    }

    String getStatus() {
        return status;
    }

    void setStatus(String status) {
        this.status = status;
    }
}

public class PrinterJobScheduling {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Queue<PrinterJob> queue = new LinkedList<>();

        try {
            System.out.print("Enter number of jobs: ");
            int n = sc.nextInt();

            for (int i = 0; i < n; i++) {
                System.out.println("\nEnter details for Job " + (i + 1));
                System.out.print("Job ID: ");
                int id = sc.nextInt();
                System.out.print("User Name: ");
                String name = sc.next();

                queue.add(new PrinterJob(id, name));
            }

            System.out.println("\n----- PRINTER JOB QUEUE -----");
            System.out.println("Job ID\tUser\tPosition\tStatus");

            int position = 1;

            while (!queue.isEmpty()) {
                PrinterJob job = queue.poll();
                job.setStatus("Processing");

                System.out.println(job.getJobId() + "\t" + job.getUserName()
                        + "\t" + position + "\t\t" + job.getStatus());

                job.setStatus("Completed");

                System.out.println(job.getJobId() + "\t" + job.getUserName()
                        + "\t" + position + "\t\t" + job.getStatus());

                position++;
            }

            System.out.println("\nAll jobs processed successfully.");
        }

        catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter valid details.");
        }

        sc.close();
    }
}