package com.example.spring_02.dao;

import com.example.spring_02.pojo.Department;
import com.example.spring_02.pojo.Employee;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
@Repository
public class DepartmentDao {

    private static Map<Integer, Department> departments = null;

    static
    {
        departments = new HashMap<Integer, Department>(); //创建一个部门表
        departments.put(101,new Department("教育部"));
        departments.put(102,new Department("市场部"));
        departments.put(103,new Department("研发部"));
        departments.put(104,new Department("教研部"));
        departments.put(105,new Department("后勤部"));
    }

    //获得部门信息
    public Collection<Department> getDepartments()
    {
        return departments.values();
    }



}
