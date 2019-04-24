package Controllers;

import Connections.RegisterService;
import Model.Customer;
import Model.TaskFields;
import Model.TodoList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.*;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@EnableAutoConfiguration
public class HomeController {

    @Autowired
    private Connection connection;

    @Autowired
    private RegisterService registerService;

    HttpSession session;

    @RequestMapping("/")
    String hello() {
        return "Hello World!";
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping("/registerCustomer")
    @ResponseBody
    ResponseEntity<?> registerCustomer(@RequestBody Customer customer) throws Exception {

        Customer newCustomer = new Customer();
        newCustomer.setEmail(customer.getEmail());
        newCustomer.setPassword(customer.getPassword());
        registerService.registerCustomer(customer);
        return ResponseEntity.ok(true) ;
    }


    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping("/createNewTask")
    @ResponseBody
    ResponseEntity<?> createNewTask(@RequestBody TaskFields taskFields, HttpServletRequest request) throws Exception {

        String email = (String) session.getAttribute("userEmail");
        taskFields.setEmail(email);
        registerService.createNewTask(taskFields);

        Customer customer = new Customer();
        customer.setEmail(email);

        final Map<String, String> todoMapList = new HashMap<>();

            List<TaskFields> todoList = registerService.getTasksList(customer);
            for(int i =0; i<todoList.size(); i++) {
                todoMapList.put("email",todoList.get(i).getEmail());
                todoMapList.put("taskname", todoList.get(i).getTaskname());
                todoMapList.put("description", todoList.get(i).getDescription());
                todoMapList.put("deadline", todoList.get(i).getDeadline());
            }


        return ResponseEntity.ok(todoMapList) ;
    }


    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping("/checkCustomer")
    @ResponseBody
    ResponseEntity<?> checkCustomer(@RequestBody Customer customer, HttpServletRequest request) throws Exception{

        boolean checkCustomer = false;
        List<Customer> customerList = registerService.checkCustomer();

        for(Customer cust : customerList) {
            if(cust.getEmail().equals(customer.getEmail())) {
                if(cust.getPassword().equals(customer.getPassword())) {
                    checkCustomer = true;
                    session = request.getSession();
                    session.setAttribute("userEmail", customer.getEmail());
                    session.setAttribute("checkCustomer", checkCustomer);
                }
            }
        }

        return ResponseEntity.ok(checkCustomer) ;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping("/retrieveTasks")
    @ResponseBody
    ResponseEntity<?> retrieveTasks() throws Exception{


        String email = (String) session.getAttribute("userEmail");
        boolean checkCustomer = (boolean) session.getAttribute("checkCustomer");
        Customer customer = new Customer();
        customer.setEmail(email);

        List<Map<String, String>> mapList = new ArrayList<>();
        if(checkCustomer) {
            List<TaskFields> todoList = registerService.getTasksList(customer);
            for(int i =0; i<todoList.size(); i++) {
                Map<String, String> todoMapList = new HashMap<>();
                todoMapList.put("email",todoList.get(i).getEmail());
                todoMapList.put("taskname", todoList.get(i).getTaskname());
                todoMapList.put("description", todoList.get(i).getDescription());
                todoMapList.put("deadline", todoList.get(i).getDeadline());
                mapList.add(todoMapList);
            }
        }


        return ResponseEntity.ok(mapList) ;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping("/deleteTask")
    @ResponseBody
    ResponseEntity<?> deleteTask(@RequestBody TaskFields taskFields) throws Exception{


        List<Map<String, String>> mapList = new ArrayList<>();

            List<TaskFields> todoList = registerService.deleteTask(taskFields.getEmail(),taskFields.getTaskname());
            for(int i =0; i<todoList.size(); i++) {
                Map<String, String> todoMapList = new HashMap<>();
                todoMapList.put("email",todoList.get(i).getEmail());
                todoMapList.put("taskname", todoList.get(i).getTaskname());
                todoMapList.put("description", todoList.get(i).getDescription());
                todoMapList.put("deadline", todoList.get(i).getDeadline());
                mapList.add(todoMapList);

            }


        return ResponseEntity.ok(mapList) ;
    }


}