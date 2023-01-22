package com.springframeworkguru.sfgspringrestdocexample.web.mapper;

import com.springframeworkguru.sfgspringrestdocexample.domain.Beer;
import com.springframeworkguru.sfgspringrestdocexample.web.model.BeerDto;
import org.mapstruct.Mapper;

@Mapper (uses = {DateMapper.class})
public interface BeerMapper {

    BeerDto BeerToBeerDto(Beer beer);
    Beer BeerDtoToBeer(BeerDto dto);
}
