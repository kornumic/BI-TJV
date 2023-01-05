package cz.cvut.fit.tjv.semestral.business;

import org.springframework.http.ResponseEntity;

import java.util.Optional;

public class EntityStateException extends RuntimeException{

    public EntityStateException() {
    }

    public <E> EntityStateException(E entity) {
        super("Illegal state of entity " + entity);
    }

    public EntityStateException(String s) {
        super(s);
    }
}
