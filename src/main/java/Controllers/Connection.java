package Controllers;

import Model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
public class Connection {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    String sql = "SELECT * FROM student WHERE ID = ?";

    public int insertStudent(Student student) {
        return jdbcTemplate.update("insert into student (id, name, passportNumber) " + "values(?,  ?, ?)",
                new Object[] {
                        student.getId(), student.getName(), student.getPassportNumber()
                });
    }

    public Student findById(long id) {
        return (Student) jdbcTemplate.queryForObject(sql, new Object[] { id }, new CustomerRowMapper());
    }



}
