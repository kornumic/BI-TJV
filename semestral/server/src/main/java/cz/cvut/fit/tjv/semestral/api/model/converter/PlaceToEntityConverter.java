package cz.cvut.fit.tjv.semestral.api.model.converter;

import cz.cvut.fit.tjv.semestral.api.model.PlaceDto;
import cz.cvut.fit.tjv.semestral.domain.Place;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class PlaceToEntityConverter implements Function<PlaceDto, Place> {
    @Override
    public Place apply(PlaceDto placeDto) throws NullPointerException{
        return new Place(placeDto.id,
                         placeDto.address,
                         placeDto.state);
    }
}
