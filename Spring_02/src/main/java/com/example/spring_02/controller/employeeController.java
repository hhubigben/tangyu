package com.example.spring_02.controller;

import com.example.spring_02.dao.DepartmentDao;
import com.example.spring_02.dao.EmployeeDao;
import com.example.spring_02.pojo.Department;
import com.example.spring_02.pojo.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.standard.expression.Token;

import javax.servlet.http.HttpSession;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

@Controller
public class employeeController {


    @Autowired
    EmployeeDao employeeDao;
    @Autowired
    DepartmentDao departmentDao;

    //建立链接
    private static Connection con;
    private static PreparedStatement pStmt;//声明预处理 PreparedStatement 对象
    private static ResultSet res;//声明结果 ResultSet 对象

    employeeController() {
        String driverName = "com.mysql.cj.jdbc.Driver";
        try {
            Class.forName(driverName);
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/highway", "root", "Ty20021104.");

            System.out.println("good");
        } catch (
                Exception e) {
            System.out.println(e);
        }
    }



    @RequestMapping("emp")
    public String list(Model model) {

        String sql = "SELECT * FROM employee";
        LinkedList<Employee> employees = new LinkedList<>();
        System.out.println(employees.size());
        System.out.println("    sdsfdsf");


        try {   //mysql查询语句
            //代码块（4）：得到结果集
            pStmt = con.prepareStatement(sql);
            res = pStmt.executeQuery();
            while (res.next()) {
                Employee employee = new Employee();
                employee.setId(res.getInt(1));
                employee.setLastName(res.getString(2));
                employee.setEmail(res.getString(3));
                employee.setGender(res.getInt(4));
                employee.setDepartment(res.getString(5));
                employees.add(employee);
            }
            res.close();//释放资源
            pStmt.close();
        } catch (Exception e) {//捕获异常
            e.printStackTrace();
        }

        model.addAttribute("emps", employees);
        return "emp/list";
    }


    @GetMapping("emps")
    public String toAddpage(Model model) {
        Collection<Department> departments = departmentDao.getDepartments();
        model.addAttribute("departments", departments);
        return "emp/add";
    }

    @PostMapping("emps")
    public String toAddemp(Employee employee) {
        char[] lastName = employee.getLastName().toCharArray();
        char[] email = employee.getEmail().toCharArray();
        int gender = employee.getGender();
        char[] department = employee.getDepartment().toCharArray();
        String sql = "insert into employee(id,lastName,email,gender,department)value(?,?,?,?,?)";

        try {
            pStmt = con.prepareStatement(sql);
            pStmt.execute();

        } catch (SQLException throwables) {
            System.out.println(throwables);
        }
        //System.out.println("save=>"+employee);
//        employeeDao.save(employee);
        return "redirect:/emp";
    }


    @GetMapping("semp")
    public String searchEmp(Model model, Employee employee) {
        try {
            Collection<Employee> employeess = employeeDao.getEmployeeByName(employee.getLastName());
            model.addAttribute("emp1", employeess);
        } catch (Exception e) {
            Collection<Employee> employeess = employeeDao.getEmployeeByName(null);
            model.addAttribute("emp1", employeess);
        }
//        employee = employeeDao.getEmployeeByName(employee.getLastName());
//        model.addAttribute("emp",employee);
        return "emp/search";
    }

    @PostMapping("semp")
    public String searchEmp2() {
        return "redirect:/emp";
    }


    //去员工的修改页面
    @GetMapping("/emp/{id}")
    public String toUpdateUmp(@PathVariable("id") Integer id, Model model) {
        //查出原来的数据
        Employee employeeById = employeeDao.getEmployeeById(id);
        model.addAttribute("emp", employeeById);

        //查找部门信息
        Collection<Department> departments = departmentDao.getDepartments();
        model.addAttribute("departments", departments);

        return "emp/update";
    }


    //员工信息修改
    @PostMapping("/updateEmp")
    public String updateEmp(Employee employee) {

        return "redirect:/emp";
    }

    //删除员工
    @GetMapping("/delemp/{id}")
    public String deleteEmp(@PathVariable("id") int id) {
        employeeDao.delete(id);
        return "redirect:/emp";
    }









}