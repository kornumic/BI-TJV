package cz.cvut.fit.tjv.semestral.client.controller;

import cz.cvut.fit.tjv.semestral.client.clients.PlaceClient;
import cz.cvut.fit.tjv.semestral.client.clients.JobClient;
import cz.cvut.fit.tjv.semestral.client.model.PlaceModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.ArrayList;

@Controller
public class PlaceWebController {
    private final PlaceClient placeClient;
    private final JobClient jobClient;
    public PlaceWebController(PlaceClient placeClient, JobClient jobClient) {
        this.placeClient = placeClient;
        this.jobClient = jobClient;
    }

    @GetMapping("/places")
    public String getAllPlaces(Model model){
        model.addAttribute("places", placeClient.fetchAllPlaces());
        return "places";
    }

    @GetMapping("/places/{id}")
    public String placeInfo(Model model, @PathVariable("id") Long id) {
        model.addAttribute("place", placeClient.fetchOnePlace(id).onErrorReturn(new PlaceModel(true, "Place not found")))
                .addAttribute("assignableJobs", jobClient.fetchAllJobs());

        return "place";
    }

    @GetMapping("/places/add")
    public String addPlaceGet(Model model) {
        model.addAttribute("place", new PlaceModel());
        return "placeAdd";
    }

    @PostMapping("/places/add")
    public String addPlaceSubmit(Model model, @ModelAttribute PlaceModel placeModel){
        model.addAttribute("place", placeClient.create(placeModel)
                .onErrorReturn(WebClientResponseException.BadRequest.class, new PlaceModel(true, "Address must be filled!"))
                .onErrorReturn(WebClientResponseException.UnprocessableEntity.class, new PlaceModel(true, "Address must be unique!")));

        return "placeAdd";
    }

    @GetMapping("/places/{id}/edit")
    public String editPlaceGet(Model model, @PathVariable("id") Long id) {
        model.addAttribute("place", placeClient.fetchOnePlace(id));
        return "placeEdit";
    }

    @PostMapping("/places/{id}/edit")
    public String editPlaceSubmit(Model model, @ModelAttribute PlaceModel place, @PathVariable("id") Long id){
        place.setId(id);
        if(place.jobsInPlace == null)
            place.jobsInPlace = new ArrayList<>();
        model.addAttribute("place", placeClient.update(place, id)
                .onErrorReturn(WebClientResponseException.BadRequest.class, new PlaceModel(place, true, "Address must be filled!"))
                .onErrorReturn(WebClientResponseException.UnprocessableEntity.class, new PlaceModel(place, true, "Place with this address already exists (must be unique!)")));

        return "placeEdit";
    }

    @GetMapping("/places/{id}/delete")
    public String deletePlaceAsk(Model model, @PathVariable("id") Long id){
        model.addAttribute("id", id);

        return "placeDelete";
    }

    @PostMapping("/places/{id}/delete")
    public String deletePlace(Model model, @PathVariable("id") Long id){
        model.addAttribute("deleted", placeClient.delete(id))
                .addAttribute("places", placeClient.fetchAllPlaces());

        return "redirect:/places";
    }

//    @PutMapping("/places/{id_place}/assign/{id_job}")
//    public String assignJob(Model model, @PathVariable("id_place") Long idPlace, @PathVariable("id_job") Long idJob){
//        model.addAttribute("place", placeClient.assignJob(idJob))
//        return "place";
//    }
}
