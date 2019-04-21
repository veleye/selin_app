package Controllers;

import Model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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


}