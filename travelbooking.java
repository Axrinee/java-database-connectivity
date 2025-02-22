import java.sql.*;
import java.util.Scanner;

class Travel {

    private Connection connect() {
        try {
            // Update with your database URL, username, and password
            String url = "jdbc:mysql://localhost:3306/yourDatabase";
            String user = "yourUsername";
            String password = "yourPassword";
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println("Database connection failed: " + e.getMessage());
            return null;
        }
    }

    public void addRecords() {
        try (Connection conn = connect()) {
            if (conn == null) return;

            Scanner s = new Scanner(System.in);
            System.out.println("Enter the User Name:");
            String name = s.nextLine();
            System.out.println("Enter the Email ID:");
            String email = s.nextLine();
            System.out.println("Enter the Phone Number:");
            String phone = s.nextLine();
            System.out.println("Enter the Destination:");
            String destination = s.nextLine();
            System.out.println("Enter the Age:");
            int age = s.nextInt();
            s.nextLine(); // Consume newline
            System.out.println("Enter the Nationality:");
            String nationality = s.nextLine();
            System.out.println("Enter the Booking Date (YYYY-MM-DD):");
            String bookingDate = s.nextLine();

            String query = "INSERT INTO travelbooking (name, email, phone, destination, age, nationality, booking_date) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, phone);
            stmt.setString(4, destination);
            stmt.setInt(5, age);
            stmt.setString(6, nationality);
            stmt.setString(7, bookingDate);

            int rows = stmt.executeUpdate();
            System.out.println(rows + " record(s) added successfully.");
        } catch (SQLException e) {
            System.out.println("Error adding records: " + e.getMessage());
        }
    }

    public void displayRecords() {
        try (Connection conn = connect()) {
            if (conn == null) return;

            String query = "SELECT * FROM travelbooking";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                System.out.println("Name: " + rs.getString("name") +
                                   ", Email: " + rs.getString("email") +
                                   ", Phone: " + rs.getString("phone") +
                                   ", Destination: " + rs.getString("destination") +
                                   ", Age: " + rs.getInt("age") +
                                   ", Nationality: " + rs.getString("nationality") +
                                   ", Booking Date: " + rs.getDate("booking_date"));
            }
        } catch (SQLException e) {
            System.out.println("Error displaying records: " + e.getMessage());
        }
    }

    public void modifyRecords() {
        try (Connection conn = connect()) {
            if (conn == null) return;

            Scanner s = new Scanner(System.in);
            System.out.println("Enter the User Name to Update:");
            String name = s.nextLine();
            System.out.println("Enter the New Destination:");
            String destination = s.nextLine();
            System.out.println("Enter the New Booking Date (YYYY-MM-DD):");
            String bookingDate = s.nextLine();

            String query = "UPDATE travelbooking SET destination = ?, booking_date = ? WHERE name = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, destination);
            stmt.setString(2, bookingDate);
            stmt.setString(3, name);

            int rows = stmt.executeUpdate();
            System.out.println(rows + " record(s) updated successfully.");
        } catch (SQLException e) {
            System.out.println("Error modifying records: " + e.getMessage());
        }
    }

    public void removeRecords() {
        try (Connection conn = connect()) {
            if (conn == null) return;

            Scanner s = new Scanner(System.in);
            System.out.println("Enter the User Name to Delete:");
            String name = s.nextLine();

            String query = "DELETE FROM travelbooking WHERE name = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, name);

            int rows = stmt.executeUpdate();
            System.out.println(rows + " record(s) deleted successfully.");
        } catch (SQLException e) {
            System.out.println("Error removing records: " + e.getMessage());
        }
    }

    public void searchRecords() {
        try (Connection conn = connect()) {
            if (conn == null) return;

            Scanner s = new Scanner(System.in);
            System.out.println("Enter the User Name to Search:");
            String name = s.nextLine();

            String query = "SELECT * FROM travelbooking WHERE name = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, name);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                System.out.println("Name: " + rs.getString("name") +
                                   ", Email: " + rs.getString("email") +
                                   ", Phone: " + rs.getString("phone") +
                                   ", Destination: " + rs.getString("destination") +
                                   ", Age: " + rs.getInt("age") +
                                   ", Nationality: " + rs.getString("nationality") +
                                   ", Booking Date: " + rs.getDate("booking_date"));
            } else {
                System.out.println("No record found for the given name.");
            }
        } catch (SQLException e) {
            System.out.println("Error searching records: " + e.getMessage());
        }
    }
}

class TravelBooking {
    public static void main(String[] args) {
        Travel travel = new Travel();
        Scanner s = new Scanner(System.in);

        while (true) {
            System.out.println("Enter 1. Add Record 2. Display Records 3. Modify Record 4. Delete Record 5. Search Record 6. Exit");
            System.out.print("Enter your choice: ");
            int choice = s.nextInt();

            switch (choice) {
                case 1:
                    travel.addRecords();
                    break;
                case 2:
                    travel.displayRecords();
                    break;
                case 3:
                    travel.modifyRecords();
                    break;
                case 4:
                    travel.removeRecords();
                    break;
                case 5:
                    travel.searchRecords();
                    break;
                case 6:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
