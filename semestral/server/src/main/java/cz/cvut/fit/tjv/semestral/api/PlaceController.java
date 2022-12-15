package cz.cvut.fit.tjv.semestral.api;

import cz.cvut.fit.tjv.semestral.api.model.PlaceDto;
import cz.cvut.fit.tjv.semestral.api.model.converter.PlaceToDtoConverter;
import cz.cvut.fit.tjv.semestral.api.model.converter.PlaceToEntityConverter;
import cz.cvut.fit.tjv.semestral.business.PlaceService;
import cz.cvut.fit.tjv.semestral.domain.Place;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/places")
public class PlaceController extends AbstractCrudController<Place, PlaceDto, Long>{
    public PlaceController(PlaceService service, PlaceToDtoConverter placeToDtoConverter, PlaceToEntityConverter placeToEntityConverter){
        super(service, placeToDtoConverter,placeToEntityConverter);
    }
}