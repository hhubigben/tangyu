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

            System.out.println("good");//连接成功
        } catch (
                Exception e) {
            System.out.println(e);
        }
    }



    @RequestMapping("emp")
    public String list(Model model) {

        String sql = "SELECT * FROM employee";
        LinkedList<Employee> employees = new LinkedList<>();//用链表存放

        try {   //mysql查询语句
            pStmt = con.prepareStatement(sql);
            res = pStmt.executeQuery();
            while (res.next()) {
                //查询结果集，添加链表
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

        model.addAttribute("emps", employees);//传递参数
        return "emp/list";//跳转到展示界面
    }

//添加员工
    @GetMapping("emps")
    public String toAddpage(Model model) {
        Collection<Department> departments = departmentDao.getDepartments();
        model.addAttribute("departments", departments);
        return "emp/add";
    }



    @PostMapping("emps")
    public String toAddemp(Employee employee) {
        Statement pStmt1;
        String lastName = employee.getLastName();
        String email = employee.getEmail();
        int gender = employee.getGender();
        String department = employee.getDepartment();
        String sql = "insert into employee(id,lastName,email,gender,department)value(?,?,?,?,?)";
        try {
            pStmt = con.prepareStatement(sql);
            String sql1 = "select * from employee";
            pStmt1 = con.createStatement(1004,1007);
            int id = 1;//如果数据空为空。从1001开始按编号增加
            res = pStmt1.executeQuery(sql1);
            res.last();
            res.previous();
            if(res.next())
            {
                id = res.getInt(1);
                id+=1;
            }
            pStmt.setInt(1,id);
            pStmt.setString(2,lastName);
            pStmt.setString(3,email);
            pStmt.setInt(4,gender);
            pStmt.setString(5,department);

            pStmt.execute();
            pStmt1.close();
            pStmt.close();

        } catch (SQLException throwables) {
            System.out.println(throwables);
        }
        return "redirect:/emp";//重定向去emp方法
    }




    //去员工的修改页面
    @GetMapping("/emp/{id}")
    public String toUpdateUmp(@PathVariable("id") Integer id, Model model) {
        //查出原来的数据
        ResultSet res1;
        Employee employeeById = new Employee();
        String sql = "select * from employee where id=?";
        try {
            pStmt = con.prepareStatement(sql);
            pStmt.setInt(1,id);
            res1 = pStmt.executeQuery();
            if (res1.next()) {
                employeeById.setId(res1.getInt(1));
                employeeById.setLastName(res1.getString(2));
                employeeById.setEmail(res1.getString(3));
                employeeById.setGender(res1.getInt(4));
                employeeById.setDepartment(res1.getString(5));
            }
            res1.close();
            pStmt.close();
        } catch (SQLException throwables) {
            System.out.println(throwables);
        }
        model.addAttribute("emp", employeeById);
        //查找部门信息
        Collection<Department> departments = departmentDao.getDepartments();
        model.addAttribute("departments", departments);
        return "emp/update";
    }
    //员工信息修改
    @PostMapping("/updateEmp")
    public String updateEmp(Employee employee) {

        String lastName = employee.getLastName();
        System.out.println(employee.getLastName());

        String email = employee.getEmail();
        int gender = employee.getGender();
        String department = employee.getDepartment();
        String sql = "update  employee set id = ?, lastName =?, email=?, gender=?, department=? where id=?";

        try {
            pStmt = con.prepareStatement(sql);
            pStmt.setInt(6,employee.getId());
            pStmt.setInt(1,employee.getId());
            pStmt.setString(2,lastName);
            pStmt.setString(3,email);
            pStmt.setInt(4,gender);
            pStmt.setString(5,department);

            pStmt.execute();
            pStmt.close();
        } catch (SQLException throwables) {
            System.out.println(throwables);
        }
        return "redirect:/emp";
    }



    //查询员工
    @GetMapping("semp")
    public String searchEmp(Model model, Employee employee1) {
        String sql = "SELECT * FROM employee where lastName =?";
        LinkedList<Employee> employees = new LinkedList<>();
        String lastName = employee1.getLastName();
        try {
            pStmt = con.prepareStatement(sql);
            pStmt.setString(1,lastName);

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
        model.addAttribute("emp1", employees);
        return "emp/search";

    }
    @PostMapping("semp")
    public String searchEmp2() {
        return "redirect:/emp";
    }



    //删除员工
    @GetMapping("/delemp/{id}")
    public String deleteEmp(@PathVariable("id") int id) {
        String sql ="delete  from employee where id =?";
        try {
            pStmt = con.prepareStatement(sql);
            pStmt.setInt(1,id);
            pStmt.execute();
            pStmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return "redirect:/emp";
    }



    @GetMapping("temp")
    public String search(Model model,Employee employee1) //
    {
        String department = employee1.getDepartment();
        System.out.println("dddddd");
        System.out.println(department);
        String sql = "SELECT * FROM employee where department =?";
        LinkedList<Employee> employees = new LinkedList<>();
        try {
            pStmt = con.prepareStatement(sql);
            pStmt.setString(1,department);

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

        model.addAttribute("emp1", employees);
        return "emp/search";
    }

    @PostMapping("temp")
    public String search1()
    {
        return "redirect:/emp";
    }



}