package cz.cvut.fit.tjv.semestral.client.controller;

import cz.cvut.fit.tjv.semestral.client.clients.EmployeeClient;
import cz.cvut.fit.tjv.semestral.client.clients.JobClient;
import cz.cvut.fit.tjv.semestral.client.model.EmployeeModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
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

    @GetMapping("/employees/{id}")
    public String employeeInfo(Model model, @PathVariable("id") Long id) {
        model.addAttribute("employee", employeeClient.fetchOneEmployee(id).onErrorReturn(new EmployeeModel(true, "Employee not found")))
                .addAttribute("assignableJobs", jobClient.fetchAllJobs());

        return "employee";
    }

    @GetMapping("/employees/add")
    public String addEmployeeGet(Model model) {
        model.addAttribute("employeeModel", new EmployeeModel());
        return "employeeAdd";
    }

    @PostMapping("/employees/add")
    public String addEmployeeSubmit(Model model, @ModelAttribute EmployeeModel employeeModel){
        model.addAttribute("employeeModel", employeeClient.create(employeeModel)
                        .onErrorReturn(WebClientResponseException.BadRequest.class, new EmployeeModel(employeeModel, true, "Name, skill and salary must be filled!"))
                        .onErrorReturn(WebClientResponseException.UnprocessableEntity.class, new EmployeeModel(employeeModel, true, "Assigned jobs are not valid!")));

        return "employeeAdd";
    }

    @GetMapping("/employees/{id}/edit")
    public String editEmployeeGet(Model model, @PathVariable("id") Long id) {
        Mono<EmployeeModel> employee = employeeClient.fetchOneEmployee(id);
//        model.addAttribute("employeeModel", employeeClient.fetchOneEmployee(id));
//        model.addAttribute(new EmployeeModel());
        model.addAttribute("employee", employee);
        return "employeeEdit";
    }

    @PostMapping("/employees/{id}/edit")
    public String editEmployeeSubmit(Model model, @ModelAttribute EmployeeModel employee, @PathVariable("id") Long id){
        employee.setId(id);
        if(employee.myJobs == null)
            employee.myJobs = new ArrayList<>();
        model.addAttribute("employee", employeeClient.update(employee, id)
                        .onErrorReturn(WebClientResponseException.BadRequest.class, new EmployeeModel(employee, true, "Name, skill and salary must be filled!"))
                        .onErrorReturn(WebClientResponseException.UnprocessableEntity.class, new EmployeeModel(employee, true, "Assigned jobs are not valid!")));

        return "employeeEdit";
    }

    @GetMapping("/employees/{id}/delete")
    public String deleteEmployeeAsk(Model model, @PathVariable("id") Long id){
        model.addAttribute("id", id);

        return "employeeDelete";
    }

    @PostMapping("/employees/{id}/delete")
    public String deleteEmployee(Model model, @PathVariable("id") Long id){
        model.addAttribute("deleted", employeeClient.delete(id))
             .addAttribute("employees", employeeClient.fetchAllEmployees());

        return "redirect:/employees";
    }

//    @PutMapping("/employees/{id_employee}/assign/{id_job}")
//    public String assignJob(Model model, @PathVariable("id_employee") Long idEmployee, @PathVariable("id_job") Long idJob){
//        model.addAttribute("employee", employeeClient.assignJob(idJob))
//        return "employee";
//    }
}
