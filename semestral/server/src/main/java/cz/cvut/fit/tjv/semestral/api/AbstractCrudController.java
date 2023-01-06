package cz.cvut.fit.tjv.semestral.api;

import cz.cvut.fit.tjv.semestral.api.model.AbstractEntityDto;
import cz.cvut.fit.tjv.semestral.business.AbstractCrudService;
import cz.cvut.fit.tjv.semestral.business.EntityStateException;
import cz.cvut.fit.tjv.semestral.domain.DomainEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.function.Function;

public abstract class AbstractCrudController <E extends DomainEntity<ID>, D extends AbstractEntityDto<ID>, ID> {

    protected AbstractCrudService<E, ID> service;
    protected Function<E, D> toDtoConverter;
    protected Function<D, E> toEntityConverter;

    public AbstractCrudController(AbstractCrudService<E, ID> service, Function<E, D> toDtoConverter, Function<D, E> toEntityConverter) {
        this.service = service;
        this.toDtoConverter = toDtoConverter;
        this.toEntityConverter = toEntityConverter;
    }

    @PostMapping
    public ResponseEntity<Object> create (@RequestBody D e) {
        try{
            return ResponseEntity.ok(toDtoConverter.apply(service.create(toEntityConverter.apply(e))));
        }
        catch (EntityStateException exc){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        catch (NullPointerException exc){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    public Collection<D> readAll() {
        return service.readAll().stream().map(toDtoConverter).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<D> readOne(@PathVariable ID id) {
        var res = service.readById(id);
        return res.map(e -> ResponseEntity.ok(toDtoConverter.apply(e))).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<D> update(@RequestBody D e , @PathVariable("id") ID id){
        if(!service.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        e.id = id;
        try{
            var res = toDtoConverter.apply(service.update(toEntityConverter.apply(e)));
            return ResponseEntity.ok(res);
        }
        catch(NullPointerException exc){
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<D> deleteById(@PathVariable("id") ID id){
        if(service.deleteById(id)){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}