import java.sql.Connection;

public class TestConnection {

    public static void main(String[] args) {

        try {
            Connection connection =
                    DatabaseConnection.getConnection();

            System.out.println(
                    "MySQL Connected Successfully!"
            );

            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
