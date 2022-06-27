package com.example.spring_02.dao;

import com.example.spring_02.pojo.Department;
import com.example.spring_02.pojo.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
@Repository
public class EmployeeDao {

    private static Map<Integer, Employee> employees = null;
    @Autowired
    private DepartmentDao departmentDao; //员工有所属的部门
    static
    {
        employees = new HashMap<Integer, Employee>(); //创建一个部门表
        employees.put(1001,new Employee(1001,"AA","3333.@qq.com",0,new Department(101,"教育部")));
        employees.put(1002,new Employee(1002,"BB","4444.@qq.com",1,new Department(102,"市场部")));
        employees.put(1003,new Employee(1003,"CC","5555.@qq.com",1,new Department(103,"研发部")));
        employees.put(1004,new Employee(1004,"DD","6666.@qq.com",0,new Department(104,"教研部")));
        employees.put(1005,new Employee(1005,"EE","7777.@qq.com",0,new Department(105,"后勤部")));
    }

    private static Integer initId = 1006;
    //添加员工
    public void save(Employee employee)
    {
        if(employee.getId()==null)
        {
            employee.setId(initId++);
        }
        employee.setDepartment(departmentDao.getDepartmentById(employee.getDepartment().getId()));
        employees.put(employee.getId(),employee);
    }

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


    //删除员工
    public void delete(Integer id)
    {
        employees.remove(id);
    }
}
