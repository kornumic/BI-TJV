package cz.cvut.fit.tjv.semestral.api;

import cz.cvut.fit.tjv.semestral.api.model.converter.PlaceToDtoConverter;
import cz.cvut.fit.tjv.semestral.api.model.converter.PlaceToEntityConverter;
import cz.cvut.fit.tjv.semestral.business.EntityStateException;
import cz.cvut.fit.tjv.semestral.business.PlaceService;
import cz.cvut.fit.tjv.semestral.domain.Place;
import cz.cvut.fit.tjv.semestral.domain.Job;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@WebMvcTest(PlaceController.class)
@Import({PlaceToDtoConverter.class, PlaceToEntityConverter.class})
class PlaceControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlaceService service;

    Job jobValid = new Job(1L, "jobValid", 3L, 50L, false);
    Job jobInvalid = new Job(2L, "jobInvalid", 3L, 161L, false);



    Place placeToAdd = new Place(1L, "address1",false, jobValid);
/*
    """
{
    "id": 1,
    "address": "address1",
    "state": false,
    "myJobs":
[
    {
        "id": "1",
        "name": "jobValid",
        "difficulty": 3,
        "time": 50,
        "finished": false
    }
]
}"""
*/
    Place placeToNotAdd = new Place(2L , "address2",false, jobInvalid);
    /*
    """
{
    "id": 2,
    "address": "address2",
    "state": false,
    "myJobs":
[
    {
        "id": "2",
        "name": "jobInvalid",
        "difficulty": 3,
        "time": 161,
        "finished": false
    }
]
}"""
*/
    Place placeUnprocessable = new Place(3L , "address", false, jobValid);
    /*
    """
{
    "id": 3,
    "address": "address",
    "state": false,
    "myJobs":
[
    {
        "id": "1",
        "name": "jobValid",
        "difficulty": 3,
        "time": 50,
        "finished": false
    }
]
}"""
*/
    @BeforeEach
    public void setUp(){
        Mockito.when(service.create(placeToAdd)).thenReturn(placeToAdd);
        Mockito.when(service.create(placeToNotAdd)).thenThrow(EntityStateException.class);

        Mockito.when(service.readById(placeToAdd.getId())).thenReturn(Optional.of(placeToAdd));
        //Mockito.when(service.readById(placeNotToAdd.getId())).thenReturn(Optional.empty());


        Mockito.when(service.existsById(placeToAdd.getId())).thenReturn(true);
        Mockito.when(service.existsById(placeToNotAdd.getId())).thenReturn(false);
        Mockito.when(service.existsById(placeUnprocessable.getId())).thenReturn(true);


        Mockito.when(service.update(placeToAdd)).thenReturn(placeToAdd);
        Mockito.when(service.update(placeToNotAdd)).thenReturn(placeToNotAdd);
        Mockito.when(service.update(placeUnprocessable)).thenThrow(EntityStateException.class);

        Mockito.when(service.deleteById(placeToAdd.getId())).thenReturn(true);
        Mockito.when(service.deleteById(placeToNotAdd.getId())).thenReturn(false);

    }

    @Test
    public void readAll() throws Exception {
        Collection<Place> allPlaces = new ArrayList<>();
        allPlaces.add(placeToAdd);
        allPlaces.add(placeToNotAdd);

        Mockito.when(service.readAll()).thenReturn(allPlaces);

        mockMvc.perform(MockMvcRequestBuilders.get("/places").accept("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)));
    }


    @Test
    void createValid() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/places")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                 {
                                     "id": 1,
                                     "address": "address1",
                                     "state": false,
                                     "myJobs":
                                 [
                                     {
                                         "id": "1",
                                         "name": "jobValid",
                                         "difficulty": 3,
                                         "time": 50,
                                         "finished": false
                                     }
                                 ]
                                 }"""))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.address", Matchers.is("address1")));
    }

    @Test
    void createInvalid() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/places")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                 {
                                     "id": 2,
                                     "address": "address2",
                                     "state": false,
                                     "myJobs":
                                 [
                                     {
                                         "id": "2",
                                         "name": "jobInvalid",
                                         "difficulty": 3,
                                         "time": 161,
                                         "finished": false
                                     }
                                 ]
                                 }"""))
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());
    }

    @Test
    void createUnprocessable() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/places")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                 {
                                     "id": 3,
                                     "address": "address",
                                     "state": false,
                                     "myJobs":
                                 [
                                     {
                                         "id": "1",
                                         "name": "jobValid",
                                         "difficulty": 3,
                                         "time": 50,
                                         "finished": false
                                     }
                                 ]
                                 }"""))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void readOneFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/places/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.address", Matchers.is("address1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)));
    }

    @Test
    void readOneNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/places/2"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void updateOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/places/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name": "place1",
                                    "salary": "500",
                                    "address": "address1",
                                    "skill": "3",
                                    "myJobs":
                                    [
                                        {
                                            "id": "1",
                                            "name": "jobValid",
                                            "difficulty": 3,
                                            "time": 50,
                                            "finished": false
                                        }
                                    ]
                                }"""))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.address", Matchers.is("address1")));
    }

    @Test
    void updateNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/places/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                    {
                                        "name": "place2",
                                        "salary": "400",
                                        "dateOfBirth": null,
                                        "address": "address2",
                                        "skill": "4",
                                        "myJobs":
                                        [
                                            {
                                                "id": "2",
                                                "name": "jobInvalid",
                                                "difficulty": 3,
                                                "time": 161,
                                                "finished": false
                                            }
                                        ]
                                    }"""))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
    @Test
    void updateUnprocessable() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/places/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                 {
                                     "id": 3,
                                     "address": "address",
                                     "state": false,
                                     "myJobs":
                                 [
                                     {
                                         "id": "1",
                                         "name": "jobValid",
                                         "difficulty": 3,
                                         "time": 50,
                                         "finished": false
                                     }
                                 ]
                                 }"""))
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());
    }

    @Test
    void updateBad() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/places/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                 {
                                     "state": false,
                                     "myJobs":
                                 [
                                     {
                                         "id": "1",
                                         "name": "jobValid",
                                         "difficulty": 3,
                                         "time": 50,
                                         "finished": false
                                     }
                                 ]
                                 }"""))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void deleteNoContent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/places/1").accept("application/json"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void deleteNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/places/2").accept("application/json"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}