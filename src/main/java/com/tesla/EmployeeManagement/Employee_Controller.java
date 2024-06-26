package com.tesla.EmployeeManagement;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class Employee_Controller {
//    get | post | delete | put
    @Autowired
    private EmployeeJpaMapping employeeTable;

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

}




/*
Route to create new password or functional forgot password?
*/