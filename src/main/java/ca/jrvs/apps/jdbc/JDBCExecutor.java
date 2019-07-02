package ca.jrvs.apps.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

public class JDBCExecutor {

    public static void main(String[] args) {
        DatabaseConnectionManager dcm = new DatabaseConnectionManager("localhost",
                "jdbc_db", "postgres", "password");

        try {
            Connection connection = dcm.getConnection();
            CustomerDAO customerDAO = new CustomerDAO(connection);
            Customer customer = new Customer();
            customer.setFirstName("Andre");
            customer.setLastName("Antunes");
            customer.setEmail("aaantune@ryerson.ca");
            customer.setPhone("(416) 888-7268");
            customer.setAddress("2349 Rideau Dr.");
            customer.setCity("Oakville");
            customer.setState("ON");
            customer.setZipcode("L6H7R6");

            customerDAO.create(customer);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
