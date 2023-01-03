package cz.cvut.fit.tjv.semestral.business;

import cz.cvut.fit.tjv.semestral.domain.DomainEntity;
import org.springframework.data.repository.CrudRepository;
import org.yaml.snakeyaml.events.Event;

import java.util.Collection;
import java.util.Optional;

public abstract class AbstractCrudService<E extends DomainEntity<K>, K>  {

    protected final CrudRepository<E, K> repository;
    protected AbstractCrudService(CrudRepository<E, K> repository) {
        this.repository = repository;
    }

    public E create(E entity) throws EntityStateException {
        return repository.save(entity);
    }

    public Collection<E> readAll(){
        return (Collection<E>) repository.findAll();
    }

    public E update(E entity) throws EntityStateException {
        if (repository.existsById(entity.getId()))
            return repository.save(entity);
        else
            throw new EntityStateException(entity);
    }
    public void deleteById(K id) {
        repository.deleteById(id);
    }
}
