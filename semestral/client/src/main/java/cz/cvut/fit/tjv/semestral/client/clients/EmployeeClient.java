package cz.cvut.fit.tjv.semestral.client.clients;

import cz.cvut.fit.tjv.semestral.client.model.EmployeeModel;
import cz.cvut.fit.tjv.semestral.client.model.JobModel;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class EmployeeClient {
    private final WebClient employeeWebClient;
    public EmployeeClient(){
        employeeWebClient = WebClient.create("http://localhost:8080/employees");
    }

    public Flux<EmployeeModel> fetchAllEmployees(){
        return employeeWebClient.get()
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(EmployeeModel.class);
    }

    public Flux<EmployeeModel> fetchOneEmployee(Long id){
        return employeeWebClient.get()
                .uri("/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(EmployeeModel.class);
    }

    public Mono<EmployeeModel> create(EmployeeModel newEmployee){
        return employeeWebClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(newEmployee)
                .retrieve()
                .bodyToMono(EmployeeModel.class);
    }

    public Mono<EmployeeModel> update(EmployeeModel newEmployee, Long id){
        return employeeWebClient.put()
                .uri("/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(newEmployee)
                .retrieve()
                .bodyToMono(EmployeeModel.class);
    }

//    public Mono<EmployeeModel> assignJob(EmployeeModel employeeModel, Long idJob) {
//        return
//
//    }
}
