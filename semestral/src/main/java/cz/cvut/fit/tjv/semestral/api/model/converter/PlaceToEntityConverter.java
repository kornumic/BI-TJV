package cz.cvut.fit.tjv.semestral.api.model.converter;

import cz.cvut.fit.tjv.semestral.api.model.PlaceDto;
import cz.cvut.fit.tjv.semestral.domain.Place;

import java.util.function.Function;

public class PlaceToEntityConverter implements Function<PlaceDto, Place> {
    @Override
    public Place apply(PlaceDto placeDto) {
        return new Place(placeDto.getId(),
                         placeDto.getAddress(),
                         placeDto.getState());
    }
}
