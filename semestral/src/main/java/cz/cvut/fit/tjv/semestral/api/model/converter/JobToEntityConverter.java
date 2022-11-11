package cz.cvut.fit.tjv.semestral.api.model.converter;

import cz.cvut.fit.tjv.semestral.api.model.JobDto;
import cz.cvut.fit.tjv.semestral.domain.Job;

import java.util.function.Function;

public class JobToEntityConverter implements Function<JobDto, Job> {
    @Override
    public Job apply(JobDto jobDto) {
        return new Job(jobDto.getId(),
                       jobDto.getName(),
                       jobDto.getDifficulty(),
                       jobDto.getDifficulty(),
                       jobDto.getFinished());
    }
}
