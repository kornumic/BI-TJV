package cz.cvut.fit.tjv.semestral.api.model.converter;

import cz.cvut.fit.tjv.semestral.api.model.JobDto;
import cz.cvut.fit.tjv.semestral.domain.Job;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class JobToDtoConverter implements Function<Job, JobDto> {
    @Override
    public JobDto apply(Job job) {
        var ret = new JobDto();

        ret.id = job.getId();
        ret.name = job.getName();
        ret.difficulty = job.getDifficulty();
        ret.time = job.getTime();
        ret.finished = job.getFinished();

        return ret;
    }
}
