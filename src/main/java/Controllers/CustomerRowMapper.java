package Controllers;

import java.sql.ResultSet;
import java.sql.SQLException;

import Model.Student;
import org.springframework.jdbc.core.RowMapper;

public class CustomerRowMapper implements RowMapper
{
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        Student customer = new Student();
        customer.setName(rs.getString("name"));
        return customer;
    }

}