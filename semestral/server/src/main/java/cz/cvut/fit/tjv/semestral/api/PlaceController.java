package cz.cvut.fit.tjv.semestral.api;

import cz.cvut.fit.tjv.semestral.api.model.EmployeeDto;
import cz.cvut.fit.tjv.semestral.api.model.JobDto;
import cz.cvut.fit.tjv.semestral.api.model.PlaceDto;
import cz.cvut.fit.tjv.semestral.api.model.converter.PlaceToDtoConverter;
import cz.cvut.fit.tjv.semestral.api.model.converter.PlaceToEntityConverter;
import cz.cvut.fit.tjv.semestral.business.EntityStateException;
import cz.cvut.fit.tjv.semestral.business.PlaceService;
import cz.cvut.fit.tjv.semestral.domain.Place;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/places")
public class PlaceController extends AbstractCrudController<Place, PlaceDto, Long>{
    public PlaceController(PlaceService service, PlaceToDtoConverter placeToDtoConverter, PlaceToEntityConverter placeToEntityConverter){
        super(service, placeToDtoConverter,placeToEntityConverter);
    }

    @Override
    @PostMapping
    public ResponseEntity<Object> create(@RequestBody PlaceDto e) {
        try{
            return ResponseEntity.ok(toDtoConverter.apply(service.create(toEntityConverter.apply(e))));
        }
        catch (EntityStateException exc){
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }
        catch (NullPointerException exc){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping
    public Collection<PlaceDto> readAll() {
        return super.readAll();
    }
}
