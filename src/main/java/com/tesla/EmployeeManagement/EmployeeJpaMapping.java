package com.tesla.EmployeeManagement;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeJpaMapping extends JpaRepository<Employee, Integer> {
    Optional<Employee> findByEmail(String email);
}
