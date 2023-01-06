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
public class PlaceToDtoConverter implements Function<Place, PlaceDto> {
    private final JobToDtoConverter jobConverter = new JobToDtoConverter();

    @Override
    public PlaceDto apply(Place place) {
        var ret = new PlaceDto();

        ret.id = place.getId();
        ret.address = place.getAddress();
        ret.state = place.getState();
        ret.jobsInPlace = jobsInPlaceDto(place.getJobsInPlace());

        return ret;
    }

    private Collection<JobDto> jobsInPlaceDto(Collection<Job> jobsInPlace){
        Collection<JobDto> myEntityJobs = new ArrayList<JobDto>();
        for (Job job : jobsInPlace) {
            myEntityJobs.add(jobConverter.apply(job));
        }
        return myEntityJobs;
    }
}
