package cz.cvut.fit.tjv.semestral.client.clients;

import cz.cvut.fit.tjv.semestral.client.model.PlaceModel;
import cz.cvut.fit.tjv.semestral.client.model.JobModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

@Component
public class PlaceClient {
    private final WebClient placeWebClient;

    private static final String ONE_URI = "/{id}";

    public PlaceClient(@Value("http://localhost:8080") String backendUrl){
        placeWebClient = WebClient.create(backendUrl + "/places");
    }

    public Flux<PlaceModel> fetchAllPlaces(){
        return placeWebClient.get()
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(PlaceModel.class);
    }

    public Mono<PlaceModel> fetchOnePlace(Long id){
        return placeWebClient.get()
                .uri(ONE_URI, id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(PlaceModel.class);
    }

    public Mono<PlaceModel> create(PlaceModel newPlace){
        if(newPlace.getJobsInPlace() == null)
            newPlace.setJobsInPlace(new ArrayList<>());
        return placeWebClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(newPlace)
                .retrieve()
                .bodyToMono(PlaceModel.class);
    }


    public Mono<PlaceModel> update(PlaceModel place, Long id){
        return placeWebClient.put()
                .uri(ONE_URI, id)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(place)
                .retrieve()
                .bodyToMono(PlaceModel.class);
    }

    public Mono<Void> delete(Long id) {
        return placeWebClient.delete() // HTTP DELETE
                .uri(ONE_URI, id) // URI
                .retrieve() // request specification finished
                .bodyToMono(Void.TYPE);
    }

//    public Mono<PlaceModel> assignJob(PlaceModel place, Long idJob) {
//        return
//
//    }
}
