package cz.cvut.fit.tjv.semestral.domain;

import java.io.Serializable;

public interface DomainEntity<ID> extends Serializable {
    ID getId();
}