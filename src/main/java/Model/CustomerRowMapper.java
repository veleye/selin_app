package Model;

import java.sql.ResultSet;
import java.sql.SQLException;

import Model.Customer;
import org.springframework.jdbc.core.RowMapper;

public class CustomerRowMapper implements RowMapper <Customer>
{
    public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
        Customer customer = new Customer();
        customer.setEmail(rs.getString("email"));
        customer.setPassword(rs.getString("password"));
        return customer;
    }

}