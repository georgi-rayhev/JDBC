import javax.xml.crypto.Data;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private  static DatabaseConnection instance;
    private Connection connection;
    private PropertiesHelper propertiesHelper;

    private DatabaseConnection() throws IOException {
        propertiesHelper = PropertiesHelper.getInstance();
        try {
                connection = DriverManager.getConnection(
                    propertiesHelper.getUrl(),
                    propertiesHelper.getUser(),
                    propertiesHelper.getPassword());

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public Connection getConnection(){
        return connection;
    }

    public static DatabaseConnection getInstance() throws SQLException, IOException {
        if (instance == null) {
            instance = new DatabaseConnection();
        } else if (instance.getConnection().isClosed()) {
            instance = new DatabaseConnection();
        }
        return instance;
    }
}
