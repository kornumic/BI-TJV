package cz.cvut.fit.tjv.semestral.api.model;

public class JobDto extends AbstractEntityDto<Long> {

    public String name;

    public Long difficulty;

    public Long time;

    public Boolean finished;
}
