package cz.cvut.fit.tjv.semestral.api;

import cz.cvut.fit.tjv.semestral.api.model.JobDto;
import cz.cvut.fit.tjv.semestral.api.model.converter.JobToDtoConverter;
import cz.cvut.fit.tjv.semestral.api.model.converter.JobToEntityConverter;

import cz.cvut.fit.tjv.semestral.business.JobService;
import cz.cvut.fit.tjv.semestral.domain.Job;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/jobs")
public class JobController extends AbstractCrudController<Job, JobDto, Long>{
    public JobController(JobService service, JobToDtoConverter jobToDtoConverter, JobToEntityConverter jobToEntityConverter){
        super(service, jobToDtoConverter,jobToEntityConverter);
    }
}
