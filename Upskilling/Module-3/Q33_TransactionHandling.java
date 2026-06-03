// Q33 - Transaction Handling in JDBC
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class TransactionHandling {

    static void transfer(int fromId, int toId, double amount) {
        String debit  = "UPDATE accounts SET balance = balance - ? WHERE id = ?";
        String credit = "UPDATE accounts SET balance = balance + ? WHERE id = ?";

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/bank", "root", "your_password"
            );
            conn.setAutoCommit(false);

            PreparedStatement ps1 = conn.prepareStatement(debit);
            ps1.setDouble(1, amount);
            ps1.setInt(2, fromId);
            ps1.executeUpdate();

            PreparedStatement ps2 = conn.prepareStatement(credit);
            ps2.setDouble(1, amount);
            ps2.setInt(2, toId);
            ps2.executeUpdate();

            conn.commit();
            System.out.println("Transfer of " + amount + " successful.");

            ps1.close();
            ps2.close();
            conn.close();
        } catch (Exception e) {
            System.out.println("Transfer failed: " + e.getMessage());
            try {
                if (conn != null) conn.rollback();
                System.out.println("Transaction rolled back.");
            } catch (Exception ex) {
                System.out.println("Rollback error: " + ex.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        transfer(1, 2, 500.0);
    }
}
