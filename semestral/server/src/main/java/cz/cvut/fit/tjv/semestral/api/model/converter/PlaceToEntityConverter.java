package cz.cvut.fit.tjv.semestral.api.model.converter;

import cz.cvut.fit.tjv.semestral.api.model.JobDto;
import cz.cvut.fit.tjv.semestral.api.model.PlaceDto;
import cz.cvut.fit.tjv.semestral.domain.Job;
import cz.cvut.fit.tjv.semestral.domain.Place;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;

@Component
public class PlaceToEntityConverter implements Function<PlaceDto, Place> {
    private final JobToEntityConverter jobConverter = new JobToEntityConverter();
    @Override
    public Place apply(PlaceDto placeDto) throws NullPointerException{
        return new Place(placeDto.id,
                         placeDto.address,
                         placeDto.state,
                         jobsInPlaceEntity(placeDto.jobsInPlace));
    }

    public Collection<Job> jobsInPlaceEntity(Collection<JobDto> jobsInPlace){
        Collection<Job> myJobsInPlaceEntity = new ArrayList<Job>();
        for (JobDto jobDto : jobsInPlace) {
            myJobsInPlaceEntity.add(jobConverter.apply(jobDto));
        }
        return myJobsInPlaceEntity;
    }
}
