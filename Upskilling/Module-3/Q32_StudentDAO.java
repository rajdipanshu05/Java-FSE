// Q32 - Insert and Update Operations in JDBC
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class StudentDAO {

    static Connection getConnection() throws Exception {
        return DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/school", "root", "your_password"
        );
    }

    static void insertStudent(int id, String name, int age) {
        String sql = "INSERT INTO students (id, name, age) VALUES (?, ?, ?)";
        try {
            Connection conn = getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setInt(3, age);
            ps.executeUpdate();
            System.out.println("Student inserted: " + name);
            ps.close();
            conn.close();
        } catch (Exception e) {
            System.out.println("Insert error: " + e.getMessage());
        }
    }

    static void updateStudent(int id, String newName) {
        String sql = "UPDATE students SET name = ? WHERE id = ?";
        try {
            Connection conn = getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, newName);
            ps.setInt(2, id);
            ps.executeUpdate();
            System.out.println("Student updated. ID: " + id);
            ps.close();
            conn.close();
        } catch (Exception e) {
            System.out.println("Update error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        insertStudent(1, "Alice", 20);
        updateStudent(1, "Alice Johnson");
    }
}
