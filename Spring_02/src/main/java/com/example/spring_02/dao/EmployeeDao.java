package com.example.spring_02.dao;

import com.example.spring_02.pojo.Department;
import com.example.spring_02.pojo.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class EmployeeDao {

    private static Map<Integer, Employee> employees = new HashMap<>() ;
    @Autowired
    private DepartmentDao departmentDao; //员工有所属的部门

    static
    {
        employees.put(1001,new Employee(1001,"AA","1111@qq.com",1,"教育部"));
        employees.put(1002,new Employee(1002,"BB","1111@qq.com",1,"教育部"));
        employees.put(1003,new Employee(1003,"CC","1111@qq.com",1,"教育部2"));
        employees.put(1004,new Employee(1004,"DD","1111@qq.com",1,"教育部3"));
        employees.put(1005,new Employee(1005,"EE","1111@qq.com",1,"教育部4"));
    }


    //添加员工
//    public void save(Employee employee)
//    {
//        if(employee.getId()==null)
//        {
//            for(int i =1001;i<employees.size()+1001+1;i++) {
//                if(employees.get(i)==null)
//                {
//                employee.setId(i);
//                break;
//                }
//            }
//        }
//
//        employees.put(employee.getId(),employee);
//    }



    //查询全部员工
    public Collection<Employee> getAll()
    {
        return employees.values();
    }

    //通过id查询员工
    public Employee getEmployeeById(Integer id)
    {
        return employees.get(id);
    }


    //通过name查员工
    public Collection<Employee> getEmployeeByName(String name)
    {
        Collection<Employee>  employee = new ArrayList<Employee>();
        Employee employee1;
        for(int i=1001;i<employees.size()+1001;i++)
        {
            if(employees.containsKey(i)) {
                employee1 = employees.get(i);
                if (employee1.getLastName().equals(name)) {
                    employee.add(employee1);
                }
            }
//                employee.add(employee1);
        }

        return employee;
    }

    //删除员工
    public void delete(Integer id)
    {
        employees.remove(id);

    }
}
