package com.springframeworkguru.sfgspringrestdocexample.web.controller;

import com.springframeworkguru.sfgspringrestdocexample.domain.Beer;
import com.springframeworkguru.sfgspringrestdocexample.repository.BeerRepository;
import com.springframeworkguru.sfgspringrestdocexample.web.mapper.BeerMapper;
import com.springframeworkguru.sfgspringrestdocexample.web.model.BeerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/beer")
public class BeerController {
    private final BeerMapper mapper;
    private final BeerRepository repository;

    @GetMapping("/{beerId}")
    public ResponseEntity<BeerDto> getBeerById(@PathVariable("beerId")UUID beerId) {
        Optional<Beer> foundBeer = repository.findById(beerId);
        if(foundBeer.isPresent()) {
            return new ResponseEntity<>(mapper.BeerToBeerDto(foundBeer.get()), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Void> saveNewBeer(@RequestBody @Validated BeerDto beerDto) {
        repository.save(mapper.BeerDtoToBeer(beerDto));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{beerId}")
    public ResponseEntity<Void> updateBeer(@PathVariable("beerId") UUID beerId, @RequestBody @Validated BeerDto beerDto) {
        repository.findById(beerId).ifPresent(
                (beer) -> {
                    beer.setName(beerDto.getName());
                    beer.setStyle(beerDto.getStyle().name());
                    beer.setPrice(beerDto.getPrice());
                    beer.setUpc(beerDto.getUpc());

                    repository.save(beer);
                }
        );
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
