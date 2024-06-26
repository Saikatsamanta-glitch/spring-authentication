package com.tesla.EmployeeManagement;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Table;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.Optional;

@RestController
public class Employee_Controller {
//    get | post | delete | put
    @Autowired
    private EmployeeJpaMapping employeeTable;
    @Autowired
    private ObjectMapper jacksonObjectMapper;

    @GetMapping("/")
    public String test(){
        return "Service is running successfully!";
    }

    @PostMapping("/register")
    public Employee register( @RequestBody Employee employee ){
        employeeTable.save(employee);
        return employee;
    }

    @PostMapping("/signIn")
    public Boolean signIn(@RequestBody Employee employee) {
        Optional<Employee> empOptional = employeeTable.findByEmail(employee.getEmail());
        if (empOptional.isPresent()) {
            Employee emp = empOptional.get();
            if (emp.getPassword().equals(employee.getPassword())) {
                return true;
            }
            return false;
        }
        return false;
    }

    @PutMapping("/forgotPassword")
    public ResponseEntity<Employee> forgotPassword(
            @RequestParam String email,
            @RequestParam String password
    ) {
        Optional<Employee> emp = employeeTable.findByEmail(email);
        if (emp.isPresent()) {
            Employee employee = emp.get();
            employee.setPassword(password);
            employeeTable.save(employee);  // Save the updated employee to the database
            return ResponseEntity.ok(employee);  // Return the updated employee with an OK status
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Employee());  // Return a not found status if the employee does not exist
    }

    @GetMapping("/getEmployee/{id}")
    public ResponseEntity<ReponseEmployee> getEmployee() {
        return getEmployee(null);
    }

    @GetMapping("/getEmployee")
    public ResponseEntity<ReponseEmployee> getEmployee( @RequestParam Integer id){
        ModelMapper mapper = new ModelMapper();
        Optional<Employee> emp = employeeTable.findById(id);
        if(emp.isPresent()){
            ReponseEmployee responseEmp = mapper.map(emp.get(), ReponseEmployee.class);
            return ResponseEntity.ok(responseEmp);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ReponseEmployee());
    }
}




/*
Route to create new password or functional forgot password?
To get one users data and not password : query | class
    1) build a Response class EmployeegetData
        id, name, email
    2) Get all Users
        stream--> EmployeegetData class
    3) return EmployeegetData

all user

Hash your password
*/