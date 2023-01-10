package cz.cvut.fit.tjv.semestral.client.controller;

import cz.cvut.fit.tjv.semestral.client.EmployeeClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EmployeeWebController {
    private final EmployeeClient employeeClient;


    public EmployeeWebController(EmployeeClient employeeClient) {
        this.employeeClient = employeeClient;
    }

    @GetMapping("/employees")
    public String getAllEmployees(Model model){
        model.addAttribute("employees", employeeClient.fetchAllEmployees());
        return "employees";
    }
}
