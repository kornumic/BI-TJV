package cz.cvut.fit.tjv.semestral.client.controller;

import cz.cvut.fit.tjv.semestral.client.clients.JobClient;
import cz.cvut.fit.tjv.semestral.client.model.JobModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Controller
public class JobWebController {
    private final JobClient jobClient;

    public JobWebController(JobClient jobClient) {
        this.jobClient = jobClient;
    }

    @GetMapping("/jobs")
    public String getAllJobs(Model model){
        model.addAttribute("jobs", jobClient.fetchAllJobs());
        return "jobs";
    }


    @GetMapping("/jobs/add")
    public String addJobGet(Model model) {
        model.addAttribute("jobModel", new JobModel());
        return "jobAdd";
    }

    @PostMapping("/jobs/add")
    public String addJobSubmit(Model model, @ModelAttribute JobModel JobModel){
        model.addAttribute("jobModel",
                jobClient.create(JobModel)
                        .onErrorReturn(WebClientResponseException.BadRequest.class, new JobModel(true, "Name, difficulty and time must be filled!"))
                        .onErrorReturn(WebClientResponseException.Conflict.class, new JobModel(true, "Job name must be unique")));

        return "jobAdd";
    }

    @GetMapping("/jobs/{id}")
    public String jobInfo(Model model, @PathVariable("id") Long id) {
        model.addAttribute("job", jobClient.fetchOneJob(id));
        return "job";
    }



}
