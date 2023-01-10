package cz.cvut.fit.tjv.semestral.client;

import cz.cvut.fit.tjv.semestral.client.model.EmployeeModel;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

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
}
