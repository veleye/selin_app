package Controllers;

import Model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@EnableAutoConfiguration
public class HomeController {

    @Autowired
    private Connection connection;

    @RequestMapping("/")
    String hello() {
        return "Hello World!";
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping("/sayHello")
    @ResponseBody
    ResponseEntity<?> home() {
        final Map<String, String> messageObject = new HashMap<>();

        Student student = connection.findById(1L);
        messageObject.put("name", student.getName());
        messageObject.put("job", student.getPassportNumber());
        return ResponseEntity.ok(messageObject) ;
    }
//@RequestParam("name") String name
    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping("/insertStudent")
    @ResponseBody
    ResponseEntity<?> insertStudent(@RequestBody Student student) {
        final Map<String, Integer> messageObject = new HashMap<>();
        Student newStudent = new Student();
        newStudent.setId(student.getId());
        newStudent.setName(student.getName());
        newStudent.setPassportNumber(student.getPassportNumber());
        int isOk = connection.insertStudent(newStudent);
        messageObject.put("isOk", isOk);
        return ResponseEntity.ok(messageObject) ;
    }


}