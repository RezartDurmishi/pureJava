import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Driver {
    public List<String> executeQuery(String query, String method) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/testdatabase";
        String user = "root";
        String password = "1234";

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<String> notes = new ArrayList<>();

        try {
            // 1. Get a connection to database
            connection = DriverManager.getConnection(url, user, password);

            // 2. Create a statement
            statement = connection.createStatement();

            // 3. Execute SQL query
            if (method.equals("post")){
                statement.executeUpdate(query);
                System.out.println("Note inserted.");
            }

            if (method.equals("get")){
                resultSet = statement.executeQuery(query);

                // 4. Process the result set
                while (resultSet.next()) {
                    System.out.println(resultSet.getString("note"));
                    notes.add(resultSet.getString("note"));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }

            if (connection != null) {
                connection.close();
            }
        }
        return notes;
    }
}

