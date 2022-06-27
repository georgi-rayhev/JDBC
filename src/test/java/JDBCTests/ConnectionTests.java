package JDBCTests;

import DbConnection.DatabaseConnection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConnectionTests {

     DatabaseConnection databaseConnection = null;
     Statement statement = null;
     ResultSet resultSet = null;
     List<String> names = new ArrayList<>();

     private String searchedName = "Georgi";

     @BeforeEach
     public void setUp(){
          try (Connection connection = DatabaseConnection.getInstance().getConnection()) {
               statement = connection.createStatement();
               String query = "SELECT * FROM customers where profile_name = '%s'";
               resultSet = statement.executeQuery(String.format(query, searchedName));
               while (resultSet.next()){
                    names.add(resultSet.getString("profile_name"));
               }
          } catch (Exception exception) {
               System.out.println(exception.getMessage());
          }
          }


     @Test
     public void checkForCurrentName() {
          for (String name : names) {
               Assertions.assertEquals(name , searchedName);
          }
          System.out.println(names);
     }

     @AfterEach
     public void closeConnection() {
          try {
               statement.close();
               resultSet.close();
          } catch (Exception e) {
               e.printStackTrace();
          }
     }
}
