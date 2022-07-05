package jdbcTests;

import dao.CustomerDao;
import dbConnection.DatabaseConnection;
import helpers.Utils;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pojos.Customers;

import java.io.IOException;
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

     CustomerDao customerDao = new CustomerDao();

     public JdbcTests() throws SQLException, IOException {
     }

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

     @AfterEach
     public void closeConnection() {
          try {
               statement.close();
               resultSet.close();
          } catch (Exception e) {
               e.printStackTrace();
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
          Assertions.assertNotNull(Utils.createListOfCustomers());
     }

     @Test
     public void testSaveCrudOperation(){
          customerDao.save(Utils.createCustomerWithFakeData());
          try {
               statement = connection.createStatement();
               resultSet = statement.executeQuery("Select * from Customers where id = 30");
               Assertions.assertNotNull(resultSet);
          } catch (Exception exception) {
               System.out.println(exception.getMessage());
          }
     }

     @Test
     public void testDeleteById() {
          customerDao.deleteById(1);
          try {
               statement = connection.createStatement();
               resultSet = statement.executeQuery("Select * from Customers where id = 1");
               Assertions.assertNull(resultSet);
          } catch (Exception exception) {
               System.out.println(exception.getMessage());
          }
     }

     //execute this tests at the END !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     @Test
     public void testDeleteAll() {
          customerDao.deleteAll();
               try {
                    statement = connection.createStatement();
                    resultSet = statement.executeQuery("Select * from customers");
                    Assertions.assertNull(resultSet);
               } catch (Exception exception) {
                    System.out.println(exception.getMessage());
               }
     }

     @Test
     public void testGetRandomId() {
          Assertions.assertNotNull(customerDao.getRandomId());
     }

     @Test
     public void createListWithRandomIds () {
          Assertions.assertNotNull(customerDao.getRandomIds(8));
     }

     @Test
     public void getCountOfCustomerIds() {
          Assertions.assertNotNull(customerDao.getRecordsCount());
     }

     @Test
     public void getByIdTest() {
          Assertions.assertNotNull(customerDao.getById(2));
     }

     @Test
     public void getByIdsTest() {
          List<Integer> ids = new ArrayList<>();
          ids.add(4);
          ids.add(5);
               List <Customers> customers  = customerDao.getByIds(ids);
                    for (Customers customer : customers) {
                         Assertions.assertEquals(customers.size(),ids.size());
                         Assertions.assertNotNull(customers.get(0).getProfile_name());
                         Assertions.assertNotNull(customers.get(1).getProfile_name());
          }
     }

     @Test
     public void testResultMapperWithId() {
          Assert.assertNotNull(customerDao.getByIdWithResultSetMapper(2).get(0).getProfile_name());
     }

     @Test
     public void testResultMapperWithListOfIds() {
          List<Integer> ids = new ArrayList<>();
          ids.add(4);
          ids.add(5);
          customerDao.getByIdsWithResultSetMapper(ids);
}
     }

