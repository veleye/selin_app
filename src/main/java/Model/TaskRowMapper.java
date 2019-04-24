package Model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TaskRowMapper implements RowMapper <TaskFields>
{
    public TaskFields mapRow(ResultSet rs, int rowNum) throws SQLException {
        TaskFields task = new TaskFields();
        task.setEmail(rs.getString("email"));
        task.setTaskname(rs.getString("taskName"));
        task.setDescription(rs.getString("description"));
        task.setDeadline(rs.getString("deadline"));
        return task;
    }

}