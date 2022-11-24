package cz.cvut.fit.tjv.semestral.api.model.converter;

import cz.cvut.fit.tjv.semestral.api.model.PlaceDto;
import cz.cvut.fit.tjv.semestral.domain.Place;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class PlaceToDtoConverter implements Function<Place, PlaceDto> {
    @Override
    public PlaceDto apply(Place place) {
        var ret = new PlaceDto();

        ret.id = place.getId();
        ret.address = place.getAddress();
        ret.state = place.getState();

        return ret;
    }
}
