package DAO;

import DbConnection.DatabaseConnection;
import Pojos.Customers;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * In this class will be stored methods for CRUD operations with Db
 */

public class CustomerDao implements DAO<Customers> {

    private Connection connection;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;


    public CustomerDao() throws SQLException, IOException {
        connection = DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public void save(Customers customer) {
        try {
            preparedStatement = connection.prepareStatement("INSERT INTO CUSTOMERS \n" +
                    "id," +
                    "profile_name," +
                    "email," +
                    "phone," +
                    "age," +
                    "gdpr_consent," +
                    "is_customer_profile_active," +
                    "profile_created_at," +
                    "profile_deactivated_at," +
                    "reason_for_deactivation," +
                    "notes) \n" +
                    "VALUES \n" +
                    "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setInt(1, customer.getId());
            preparedStatement.setString(2, customer.getProfile_name());
            preparedStatement.setString(3, customer.getEmail());
            preparedStatement.setString(4, customer.getPhone());
            preparedStatement.setInt(5, customer.getAge());
            preparedStatement.setBoolean(6, customer.isGdpr_consent());
            preparedStatement.setBoolean(7, customer.isIs_customer_profile_active());
            preparedStatement.setTimestamp(8, customer.getProfile_created_at());
            preparedStatement.setTimestamp(9, customer.getProfile_deactivated());
            preparedStatement.setString(10, customer.getReason_for_deactivation());
            preparedStatement.setString(11, customer.getNotes());
            preparedStatement.executeUpdate();
            System.out.println("Customer is saved successfully");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void deleteById(int id) {
        try {
            preparedStatement = connection.prepareStatement("Delete from CUSTOMERS WHERE id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            System.out.printf("Customer with id %d is deleted.%n");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }


    @Override
    public void deleteAll() {
        try {
            preparedStatement = connection.prepareStatement("Delete from CUSTOMERS");
            preparedStatement.executeUpdate();
            System.out.println("All records from Customers table are deleted");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }


    @Override
    public Customers getRandomId() {
        Customers randomCustomer = null;
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM CUSTOMERS ORDER BY RANDOM() LIMIT 1");
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                randomCustomer = Customers.builder()
                        .id(resultSet.getInt("id"))
                        .profile_name(resultSet.getString("profile_name"))
                        .email(resultSet.getString("email"))
                        .phone(resultSet.getString("phone"))
                        .age(resultSet.getInt("age"))
                        .Gdpr_consent(resultSet.getBoolean("gdpr_consent"))
                        .Is_customer_profile_active(resultSet.getBoolean("is_customer_profile_active"))
                        .Profile_created_at(resultSet.getTimestamp("Profile_created_at"))
                        .Profile_deactivated(resultSet.getTimestamp("Profile_deactivated"))
                        .reason_for_deactivation(resultSet.getString("reason_for_deactivation"))
                        .notes(resultSet.getString("notes"))
                        .build();
            }
            System.out.println(randomCustomer);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return randomCustomer;
    }


    @Override
    public List<Integer> getRandomIds(int randomCount) {
        List<Integer> randomCustomersIds = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement("Select * from Customers ORDER BY random() limit ?");
            preparedStatement.setInt(1, randomCount);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                randomCustomersIds.add(resultSet.getInt("id"));
            }
            System.out.println(randomCustomersIds);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return randomCustomersIds;
    }



    @Override
    public int getRecordsCount() {
        int idRecords = 0;
        try {
            preparedStatement = connection.prepareStatement("Select COUNT (id) from Customers ");
            resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    idRecords = resultSet.getInt(1);
                    System.out.printf("Customers Id's count is : %d%n", idRecords);
                }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    return idRecords;
}
}
