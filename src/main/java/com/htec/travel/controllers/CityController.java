package com.htec.travel.controllers;

import com.htec.travel.cities.CityDto;
import com.htec.travel.cities.CityService;
import com.htec.travel.cities.CreateCityDto;
import com.htec.travel.security.HasAnyRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/city")
public class CityController {

    @Autowired
    private CityService cityService;

    @HasAnyRole({"ADMIN"})
    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public CityDto create(@RequestBody @Valid CreateCityDto input) {
        return cityService.createCity(input);
    }

    @HasAnyRole({"USER", "ADMIN"})
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public List<CityDto> getAll(@RequestParam(name = "max-comments", required = false) Integer maxCommnents) {
        return cityService.getAll(valueOrMax(maxCommnents));
    }

    @HasAnyRole({"USER", "ADMIN"})
    @GetMapping(path = "/{id}", produces = APPLICATION_JSON_VALUE)
    public CityDto getCityById(@PathVariable("id") BigInteger id,
                               @RequestParam(name = "max-comments", required = false) Integer maxCommnents) {
        return cityService.getById(id, valueOrMax(maxCommnents));
    }

    @HasAnyRole({"USER", "ADMIN"})
    @GetMapping(path = "/search", produces = APPLICATION_JSON_VALUE, params = {"name"})
    @Validated
    public List<CityDto> searchCitiesByName(
            @RequestParam("name") @Pattern(regexp = "^[^\\/\\.\\;\\'\\\"\\\\\\#]{1,}$") @NotBlank String searchString,
            @RequestParam(name = "max-comments", required = false) Integer maxCommnents) {
        return cityService.findByName(searchString, valueOrMax(maxCommnents));
    }

    private Integer valueOrMax(Integer value) {
        return value == null ? Integer.MAX_VALUE : value;
    }

    @HasAnyRole({"ADMIN"})
    @PostMapping(path = "/upload", produces = APPLICATION_JSON_VALUE)
    public long upload(@RequestParam("file") MultipartFile file) throws IOException {
        return cityService.parseDataset(file.getInputStream());
    }
}
