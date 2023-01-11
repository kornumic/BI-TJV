package cz.cvut.fit.tjv.semestral.client.clients;


import cz.cvut.fit.tjv.semestral.client.model.JobModel;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class JobClient {
    private final WebClient jobWebClient;

    public JobClient(){
        jobWebClient = WebClient.create("http://localhost:8080/jobs");
    }

    public Flux<JobModel> fetchAllJobs(){
        return jobWebClient.get()
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(JobModel.class);
    }

    public Flux<JobModel> fetchOneJob(Long id){
        return jobWebClient.get()
                .uri("/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(JobModel.class);
    }

    public Mono<JobModel> create(JobModel newJob){
        return jobWebClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(newJob)
                .retrieve()
                .bodyToMono(JobModel.class);
    }
}
