package jdbcTests;

import dao.CustomerAddressesDao;

import dbConnection.DatabaseConnection;
import helpers.Utils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pojos.CustomerAddresses;


import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CustomerAddressTests {


    DatabaseConnection databaseConnection = null;
    private static Connection connection;
    Statement statement = null;
    ResultSet resultSet = null;
    List<String> names = new ArrayList<>();

    private int address_id = 1;
    private String query = "SELECT * FROM customer_addresses where address_id = '%s'";


    CustomerAddressesDao customerAddressesDao = new CustomerAddressesDao();

    public CustomerAddressTests() throws SQLException, IOException {
    }


    @BeforeEach
    public void setUp(){
        try  {
            connection = DatabaseConnection.getInstance().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(String.format(query, address_id));
            while (resultSet.next()){
                names.add(resultSet.getString("address_id"));
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    @AfterEach
    public void closeConnection() {
        try {
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void CustomerAddressSaveTests(){
        customerAddressesDao.save(Utils.createCustomerAddressWithFakeData());
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("Select * from customer_address where address_id = 20");
            Assertions.assertNotNull(resultSet);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    @Test
    public void testDeleteById() {
        customerAddressesDao.deleteById(22);
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("Select * from customer_addresses where address_id = 22");
            Assertions.assertNull(resultSet);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    @Test
    public void testDeleteAll() {
        customerAddressesDao.deleteAll();
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("Select * from customer_addresses");
            Assertions.assertNull(resultSet);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    @Test
    public void testGetRandomId() {
        Assertions.assertNotNull(customerAddressesDao.getRandomId());
    }

    @Test
    public void createListWithRandomIds () {
        Assertions.assertNotNull(customerAddressesDao.getRandomIds(8));
    }

    @Test
    public void getCountOfCustomerIds() {
        Assertions.assertNotNull(customerAddressesDao.getRecordsCount());
    }

    @Test
    public void getByIdTest() {
        Assertions.assertNotNull(customerAddressesDao.getById(2));
    }

    @Test
    public void getByIdsTest() {
        List<Integer> ids = new ArrayList<>();
        ids.add(4);
        ids.add(5);
        List <CustomerAddresses> customerAddresses  = customerAddressesDao.getByIds(ids);
        for (CustomerAddresses addresses : customerAddresses) {
            Assertions.assertEquals(customerAddresses.size(),ids.size());
            Assertions.assertNotNull(customerAddresses.get(0).getAddress());
            Assertions.assertNotNull(customerAddresses.get(1).getAddress());
        }
    }
}
