package Connections;

import Model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Repository
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    String createTaskTblSql =
            "CREATE TABLE IF NOT EXISTS TASKS " +
                    "(taskid int AUTO_INCREMENT, " +
                    " email VARCHAR(255), " +
                    " taskname VARCHAR(255), "+
                    " description VARCHAR(255), "  +
                    " deadline VARCHAR(255)," +
                    " PRIMARY KEY ( taskid ))";

    @Override
    public void registerCustomer(Customer customer) throws Exception {
        //check if table is already defined

        String createRegisterTblSql =
                "CREATE TABLE IF NOT EXISTS REGISTRATION " +
                        "(email VARCHAR(255), " +
                        " password VARCHAR(255), " +
                        " PRIMARY KEY ( email ))";

        try{

            String url = "jdbc:h2:file:~/test";
            Connection conn = DriverManager.getConnection(url, "sa", "");
            DatabaseMetaData md2 = conn.getMetaData();
            ResultSet rsTables = md2.getColumns(null, null, "REGISTRATION", "name");
            if (rsTables.next()) {
                jdbcTemplate.update("insert into REGISTRATION (email, password) " + "values(?,  ?)",
                        new Object[] {
                                customer.getEmail(), customer.getPassword()
                        });
            }else {
                jdbcTemplate.execute(createRegisterTblSql);
                jdbcTemplate.update("insert into REGISTRATION (email, password) " + "values(?,  ?)",new Object[] {  customer.getEmail(), customer.getPassword()});
            }

        }catch (Exception ex){
            throw new Exception(ex.getMessage());
        }


    }

    @Override
    public List<Customer> checkCustomer() throws Exception {
        return jdbcTemplate.query("select * from REGISTRATION", new CustomerRowMapper());
    }

    @Override
    public void createNewTask(TaskFields taskFields) throws Exception {

        jdbcTemplate.execute(createTaskTblSql);
        jdbcTemplate.update("insert into TASKS (email,taskname,description,deadline) " + "values(?,?,?,?)",
                new Object[] {taskFields.getEmail(),taskFields.getTaskname(),
                        taskFields.getDescription(),taskFields.getDeadline()});
    }

    @Override
    public List<TaskFields> getTasksList(Customer customer) throws Exception {
        jdbcTemplate.execute(createTaskTblSql);
        return jdbcTemplate.query("select * from TASKS where email=?", new Object[] { customer.getEmail() }, new TaskRowMapper());
    }

    @Override
    public List<TaskFields> deleteTask(String email, String taskName) throws Exception {
         jdbcTemplate.update("delete from TASKS where email=? and taskname=?", new Object[] { email,taskName });
        return jdbcTemplate.query("select * from TASKS where email=?", new Object[] { email}, new TaskRowMapper());
    }

}
