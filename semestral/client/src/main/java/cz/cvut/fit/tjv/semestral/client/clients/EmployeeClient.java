package cz.cvut.fit.tjv.semestral.client.clients;

import cz.cvut.fit.tjv.semestral.client.model.EmployeeModel;
import cz.cvut.fit.tjv.semestral.client.model.JobModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class EmployeeClient {
    private final WebClient employeeWebClient;

    private static final String ONE_URI = "/{id}";

    public EmployeeClient(@Value("http://localhost:8080") String backendUrl){
        employeeWebClient = WebClient.create(backendUrl + "/employees");
    }

    public Flux<EmployeeModel> fetchAllEmployees(){
        return employeeWebClient.get()
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(EmployeeModel.class);
    }

    public Mono<EmployeeModel> fetchOneEmployee(Long id){
        return employeeWebClient.get()
                .uri(ONE_URI, id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(EmployeeModel.class);
    }

    public Mono<EmployeeModel> create(EmployeeModel newEmployee){
        return employeeWebClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(newEmployee)
                .retrieve()
                .bodyToMono(EmployeeModel.class);
    }


    public Mono<EmployeeModel> update(EmployeeModel employee, Long id){
        return employeeWebClient.put()
                .uri(ONE_URI, id)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(employee)
                .retrieve()
                .bodyToMono(EmployeeModel.class);
    }

//    public Mono<EmployeeModel> assignJob(EmployeeModel employeeModel, Long idJob) {
//        return
//
//    }
}
