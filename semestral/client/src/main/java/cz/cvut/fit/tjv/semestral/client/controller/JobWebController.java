package cz.cvut.fit.tjv.semestral.client.controller;

import cz.cvut.fit.tjv.semestral.client.clients.JobClient;
import cz.cvut.fit.tjv.semestral.client.model.JobModel;
import cz.cvut.fit.tjv.semestral.client.model.JobModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

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

    @GetMapping("/jobs/{id}")
    public String jobInfo(Model model, @PathVariable("id") Long id) {
        model.addAttribute("job", jobClient.fetchOneJob(id));
        return "job";
    }

    @PostMapping("/jobs/add")
    public String addJobSubmit(Model model, @ModelAttribute JobModel jobModel){
        model.addAttribute("jobModel", jobClient.create(jobModel)
                .onErrorReturn(WebClientResponseException.BadRequest.class, new JobModel(jobModel, true, "Name, difficulty and time must be filled!"))
                .onErrorReturn(WebClientResponseException.Conflict.class, new JobModel(jobModel, true, "Job name must be unique")));

        return "jobAdd";
    }

    @GetMapping("/jobs/{id}/edit")
    public String editJobGet(Model model, @PathVariable("id") Long id) {
        model.addAttribute("job", jobClient.fetchOneJob(id));
        return "jobEdit";
    }

    @PostMapping("/jobs/{id}/edit")
    public String editJobSubmit(Model model, @ModelAttribute JobModel job, @PathVariable("id") Long id){
        model.addAttribute("job", jobClient.update(job, id)
                .onErrorReturn(WebClientResponseException.BadRequest.class, new JobModel(job, true, "Name, difficulty and time must be filled!"))
                .onErrorReturn(WebClientResponseException.UnprocessableEntity.class, new JobModel(job, true, "Job name must be unique")));

        return "jobEdit";
    }

    @GetMapping("/jobs/{id}/delete")
    public String deleteJobAsk(Model model, @PathVariable("id") Long id){
        model.addAttribute("id", id);

        return "jobDelete";
    }

    @PostMapping("/jobs/{id}/delete")
    public String deleteJob(Model model, @PathVariable("id") Long id){
        model.addAttribute("deleted", jobClient.delete(id))
                .addAttribute("jobs", jobClient.fetchAllJobs());

        return "redirect:/jobs";
    }

}
