package cz.cvut.fit.tjv.semestral.api.model;

import cz.cvut.fit.tjv.semestral.domain.Job;

import java.util.ArrayList;
import java.util.Collection;

public class PlaceDto extends AbstractEntityDto<Long> {

    public String address;

    public Boolean state;

    public Collection<JobDto> jobsInPlace = new ArrayList<>();
}
