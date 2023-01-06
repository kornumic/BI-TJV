package cz.cvut.fit.tjv.semestral.api.model.converter;

import cz.cvut.fit.tjv.semestral.api.model.JobDto;
import cz.cvut.fit.tjv.semestral.domain.Job;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class JobToEntityConverter implements Function<JobDto, Job> {
    @Override
    public Job apply(JobDto jobDto) throws NullPointerException{
        return new Job(jobDto.id,
                       jobDto.name,
                       jobDto.difficulty,
                       jobDto.time,
                       jobDto.finished);
    }
}
