
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DAO {
    private static final String URL = "jdbc:mysql://localhost:3306/HRD?serverTimezone=Europe/Moscow&useSSL=false";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;

    public static void connect() {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void create(String name) {
        String sql = "INSERT INTO units (name) VALUES ('" + name + "')";
        try {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void delete(int id) {
        String sql = "DELETE FROM units WHERE id = " + id;
        try {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void hire(int id, String name, String surname, String patronymic, String position) {
        String sql = "INSERT INTO employees (unit_id, name, surname, patronymic, position) VALUES (" + id + ", '" + name + "', '" + surname + "', '" + patronymic + "', '" + position + "')";
        try {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void dismiss(int id) {
        String sql = "DELETE FROM employees WHERE id = " + id;
        try {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void edit(int id, String name, String surname, String patronymic, String position) {
        String sql = "UPDATE employees SET name = '" + name + "', surname = '" + surname + "', patronymic = '" + patronymic + "', position = '" + position + "' WHERE id = " + id;
        try {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void transfer(int id, int unit_id) {
        String sql = "UPDATE employees SET unit_id = " + unit_id + " WHERE id = " + id;
        try {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int count(int id) {
        String sql = "SELECT COUNT(*) FROM employees WHERE unit_id = " + id;
        try {
            resultSet = statement.executeQuery(sql);
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static List<String> getEmployees(int id) {
        List<String> list = new ArrayList<>();
        String sql = "SELECT * FROM employees WHERE unit_id = " + id;
        try {
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                list.add(resultSet.getString(1) + " " + resultSet.getString(3) + " " + resultSet.getString(4) + " " + resultSet.getString(5) + " " + resultSet.getString(6));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<String> getUnits() {
        List<String> list = new ArrayList<>();
        String sql = "SELECT * FROM units";
        try {
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                list.add(resultSet.getString(1) + " " + resultSet.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
