package Controllers;

import Model.CustomerRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class Connection {

    @Autowired
    private JdbcTemplate jdbcTemplate;



}
