package JDBCTests;

import DbConnection.DatabaseConnection;
import Helpers.Utils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcTests {

     DatabaseConnection databaseConnection = null;
     private static Connection connection;
     Statement statement = null;
     ResultSet resultSet = null;
     List<String> names = new ArrayList<>();
     List<String> fakerNames = new ArrayList<>();

     private String searchedName = "Georgi";
     private String query = "SELECT * FROM customers where profile_name = '%s'";
     private String searchingFakeUserQuery = "SELECT * FROM customers where id = 15";

     //remove query from setUp , move it to checkForCurrentName
     //add assertions for new tests
     @BeforeEach
     public void setUp(){
          try  {
               connection = DatabaseConnection.getInstance().getConnection();
               statement = connection.createStatement();
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

     @Test
     public void createCustomerWithFakeData() throws Exception {
          Utils.createCustomerWithFakeData();
          try {
               statement = connection.createStatement();
               resultSet = statement.executeQuery(String.format(searchingFakeUserQuery));
               Assertions.assertNotNull(resultSet);
          } catch (Exception exception) {
               System.out.println(exception.getMessage());
          }
     }


     @Test
     public void createListOfCustomers() {
          Utils.createListOfCustomers();
          try {
               statement = connection.createStatement();
               resultSet = statement.executeQuery(String.format(searchingFakeUserQuery));
               Assertions.assertNotNull(resultSet);
          } catch (Exception exception) {
               System.out.println(exception.getMessage());
          }
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
