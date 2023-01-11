package cz.cvut.fit.tjv.semestral.client.controller;

import cz.cvut.fit.tjv.semestral.client.clients.EmployeeClient;
import cz.cvut.fit.tjv.semestral.client.clients.JobClient;
import cz.cvut.fit.tjv.semestral.client.model.EmployeeModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Optional;

@Controller
public class EmployeeWebController {
    private final EmployeeClient employeeClient;
    private final JobClient jobClient;
    public EmployeeWebController(EmployeeClient employeeClient, JobClient jobClient) {
        this.employeeClient = employeeClient;
        this.jobClient = jobClient;
    }

    @GetMapping("/employees")
    public String getAllEmployees(Model model){
        model.addAttribute("employees", employeeClient.fetchAllEmployees());
        return "employees";
    }

    @GetMapping("/employees/add")
    public String addEmployeeGet(Model model) {
        model.addAttribute("employeeModel", new EmployeeModel());
        return "employeeAdd";
    }

    @PostMapping("/employees/add")
    public String addEmployeeSubmit(Model model, @ModelAttribute EmployeeModel employeeModel){
        model.addAttribute("employeeModel",
                employeeClient.create(employeeModel)
                        .onErrorReturn(WebClientResponseException.BadRequest.class, new EmployeeModel(true, "Name, skill and salary must be filled!"))
                        .onErrorReturn(WebClientResponseException.UnprocessableEntity.class, new EmployeeModel(true, "Assigned jobs are not valid!")));

        return "employeeAdd";
    }

    @GetMapping("/employees/edit")
    public String editEmployeeGet(Model model) {
        model.addAttribute("employeeModel", new EmployeeModel());
        return "employeeEdit";
    }

    @PutMapping("/employees/{id}")
    public String editEmployeeSubmit(Model model, @ModelAttribute EmployeeModel employeeModel, @PathVariable("id") Long id){
        model.addAttribute("employeeModel",
                employeeClient.update(employeeModel, id)
                        .onErrorReturn(WebClientResponseException.BadRequest.class, new EmployeeModel(true, "Name, skill and salary must be filled!"))
                        .onErrorReturn(WebClientResponseException.UnprocessableEntity.class, new EmployeeModel(true, "Assigned jobs are not valid!")));

        return "employee";
    }

    @GetMapping("/employees/{id}")
    public String employeeInfo(Model model, @PathVariable("id") Long id) {
        model.addAttribute("employee", employeeClient.fetchOneEmployee(id))
             .addAttribute("assignableJobs", jobClient.fetchAllJobs());

        return "employee";
    }

//    @PutMapping("/employees/{id_employee}/assign/{id_job}")
//    public String assignJob(Model model, @PathVariable("id_employee") Long idEmployee, @PathVariable("id_job") Long idJob){
//        model.addAttribute("employee", employeeClient.assignJob(idJob))
//        return "employee";
//    }
}
