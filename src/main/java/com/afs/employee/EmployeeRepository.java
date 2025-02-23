package com.afs.employee;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class EmployeeRepository {
    List<Employee> employees;

    public EmployeeRepository(){
        this.employees = new ArrayList<>();
        employees.add(new Employee(1,"Thomas", 20, "Male", 8000));
        employees.add(new Employee(2,"Michael", 21, "Male", 8000));
        employees.add(new Employee(3,"Amy", 22, "Female", 8000));
    }

    public List<Employee> findAll() {
        return employees;
    }

    public Employee findById(int id) {
        return employees.stream()
                .filter(employee -> employee.getId()==id)
                .findFirst()
                .orElseThrow(EmployeeNotFoundException::new);
    }

    public List<Employee> findByGender(String gender) {
        return employees.stream()
                .filter(employee -> employee.getGender().equals(gender))
                .collect(Collectors.toList());
    }

    public Employee create(Employee employee) {
        int nextId = generateNextId();
        employee.setId(nextId);
        employees.add(employee);
        return employee;
    }

    private int generateNextId() {
        int nextId = employees.stream()
                .mapToInt(employee -> employee.getId())
                .max()
                .orElse(0);
        return nextId + 1;
    }

    public Employee update(int id, Employee employee) {
        Employee existingEmployee = findById(id);
        if(employee.getAge() != null){
            existingEmployee.setAge(employee.getAge());
        }
        if(employee.getSalary() != null){
            existingEmployee.setSalary(employee.getSalary());
        }
        return existingEmployee;
    }

    public void delete(int id) {
        Employee deleteEmployee = findById(id);
        employees.remove(deleteEmployee);
    }

    public List<Employee> findWithPage(int page, int pageSize) {
        int startIndex = (pageSize*page)-pageSize;
        int endIndex = pageSize*page>employees.size()?employees.size():pageSize*page;
        return employees.subList(startIndex, endIndex);
    }
}
//{
//    "id": 5,
//    "name": "Lily",
//    "age": 20,
//    "gender": "Female",
//    "salary": 8000
//}
