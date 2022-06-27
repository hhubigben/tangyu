package com.example.spring_02.controller;

import com.example.spring_02.dao.DepartmentDao;
import com.example.spring_02.dao.EmployeeDao;
import com.example.spring_02.pojo.Department;
import com.example.spring_02.pojo.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.Collection;

@Controller
public class employeeController {

    @Autowired
    EmployeeDao employeeDao;


    @RequestMapping("cjj")
    public String cjj()
    {
        return "cjj";
    }



    @RequestMapping("emp")
    public String list(Model model){
        Collection<Employee> employees = employeeDao.getAll();
        model.addAttribute("emps",employees);
        return "emp/list";
    }


    @Autowired
    DepartmentDao departmentDao;

    @GetMapping("emps")
    public String toAddpage(Model model)
    {
        Collection<Department> departments = departmentDao.getDepartments();
        model.addAttribute("departments",departments);
        return "emp/add";
    }

    @PostMapping("emps")
    public String toAddemp(Employee employee)
    {
        employeeDao.save(employee);
//        System.out.println("save=>"+employee);
//        employeeDao.save(employee);

        return "redirect:/emp";
    }

    @GetMapping("semp")
    //员工查找
    public String toSearch(Model model)
    {

    }


    //去员工的修改页面
    @GetMapping("/emp/{id}")
    public String toUpdateUmp(@PathVariable("id")Integer id,Model model)
    {
        //查出原来的数据
        Employee employeeById = employeeDao.getEmployeeById(id);
        model.addAttribute("emp",employeeById);

        //查找部门信息
        Collection<Department> departments = departmentDao.getDepartments();
        model.addAttribute("departments",departments);

        return "emp/update";
    }


    //员工信息修改
    @PostMapping("/updateEmp")
    public String updateEmp(Employee employee)
    {
        employeeDao.save(employee);
        return "redirect:/emp";
    }



    //删除员工
    @GetMapping("/delemp/{id}")
    public String deleteEmp(@PathVariable("id")int id)
    {
        employeeDao.delete(id);
        return "redirect:/emp";
    }


}
