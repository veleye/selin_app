package Connections;

import Model.Customer;
import Model.TaskFields;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RegisterService {

     void registerCustomer(Customer customer) throws Exception;

     List<Customer> checkCustomer() throws Exception;

     void createNewTask (TaskFields taskFields) throws Exception;

     List<TaskFields> getTasksList (Customer customer) throws Exception;

     List<TaskFields> deleteTask (String email, String taskName) throws Exception;

}
