package cz.cvut.fit.tjv.semestral.api;

import cz.cvut.fit.tjv.semestral.api.model.converter.JobToDtoConverter;
import cz.cvut.fit.tjv.semestral.api.model.converter.JobToEntityConverter;
import cz.cvut.fit.tjv.semestral.business.JobService;
import cz.cvut.fit.tjv.semestral.business.EntityStateException;
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

@WebMvcTest(JobController.class)
@Import({JobToDtoConverter.class, JobToEntityConverter.class})
class JobControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JobService service;

    Job jobValid = new Job(1L, "jobValid", 3L, 50L, false);
    /*"""
                            {
                                "id": "1",
                                "name": "jobValid",
                                "difficulty": 3,
                                "time": 50,
                                "finished": false
                            }
                            """*/
    Job jobInvalid = new Job(2L, "jobInvalid", 3L, 161L, false);
    /*"""{
        "id": "2",
            "name": "jobInvalid",
            "difficulty": 3,
            "time": 161,
            "finished": false
    }"""*/
    Job jobCollision = new Job(3L, "jobInvalid", 3L, 161L, false);
    /*"""{
        "id": "3",
            "name": "jobInvalid",
            "difficulty": 3,
            "time": 161,
            "finished": false
    }"""*/

    @BeforeEach
    public void setUp(){
        Mockito.when(service.create(jobValid)).thenReturn(jobValid);
        Mockito.when(service.create(jobInvalid)).thenThrow(EntityStateException.class);
        Mockito.when(service.create(jobCollision)).thenThrow(EntityStateException.class);

        Mockito.when(service.readById(jobValid.getId())).thenReturn(Optional.of(jobValid));
        Mockito.when(service.readById(jobCollision.getId())).thenReturn(Optional.of(jobCollision));
        //Mockito.when(service.readById(jobInvalid.getId())).thenReturn(Optional.empty());


        Mockito.when(service.existsById(jobValid.getId())).thenReturn(true);
        Mockito.when(service.existsById(jobInvalid.getId())).thenReturn(false);
        Mockito.when(service.existsById(jobCollision.getId())).thenReturn(true);
        //Mockito.when(service.existsById(jobUnprocessable.getId())).thenReturn(true);


        Mockito.when(service.update(jobValid)).thenReturn(jobValid);
        Mockito.when(service.update(jobInvalid)).thenReturn(jobInvalid);
        Mockito.when(service.update(jobCollision)).thenReturn(jobCollision);
        //Mockito.when(service.update(jobUnprocessable)).thenThrow(EntityStateException.class);

        Mockito.when(service.deleteById(jobValid.getId())).thenReturn(true);
        Mockito.when(service.deleteById(jobInvalid.getId())).thenReturn(false);
        Mockito.when(service.deleteById(jobCollision.getId())).thenReturn(true);

    }

    @Test
    public void readAll() throws Exception {
        Collection<Job> allJobs = new ArrayList<>();
        allJobs.add(jobValid);
        allJobs.add(jobInvalid);

        Mockito.when(service.readAll()).thenReturn(allJobs);
        //Mockito.when(service.readAllAssignable()).thenThrow(EntityStateException.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/jobs").accept("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)));
    }
    
    @Test
    void createValid() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/jobs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                 {
                                     "id": "1",
                                     "name": "jobValid",
                                     "difficulty": 3,
                                     "time": 50,
                                     "finished": false
                                 }
                                 """))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("jobValid")));
    }

    @Test
    void createInvalid() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/jobs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                 {
                                     "id": "2",
                                     "name": "jobInvalid",
                                     "difficulty": 3,
                                     "time": 161,
                                     "finished": false
                                 }
                                 """))
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    void createUnprocessable() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/jobs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                 {
                                     "difficulty": 3,
                                     "time": 50,
                                     "finished": false
                                 }
                                 """))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void readOneFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/jobs/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("jobValid")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)));
    }

    @Test
    void readOneNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/jobs/2"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void updateOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/jobs/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                 {
                                     "name": "jobValid",
                                     "difficulty": 3,
                                     "time": 50,
                                     "finished": false
                                 }
                                 """))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("jobValid")));
    }

    @Test
    void updateNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/jobs/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                 {
                                     "name": "jobValid",
                                     "difficulty": 3,
                                     "time": 50,
                                     "finished": false
                                 }
                                 """))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void updateBad() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/jobs/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                 {
                                     "difficulty": 3,
                                     "time": 50,
                                     "finished": false
                                 }
                                 """))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void deleteNoContent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/jobs/1").accept("application/json"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void deleteNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/jobs/2").accept("application/json"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}