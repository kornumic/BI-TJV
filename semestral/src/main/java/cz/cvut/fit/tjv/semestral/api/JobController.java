package cz.cvut.fit.tjv.semestral.api;

import cz.cvut.fit.tjv.semestral.api.model.JobDto;
import cz.cvut.fit.tjv.semestral.business.AbstractCrudService;
import cz.cvut.fit.tjv.semestral.business.JobService;
import cz.cvut.fit.tjv.semestral.domain.Job;

import java.util.function.Function;

public class JobController extends AbstractCrudController<Job, JobDto, Long>{
}
