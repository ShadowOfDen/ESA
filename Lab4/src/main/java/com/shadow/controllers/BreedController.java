package com.shadow.controllers;

import com.shadow.JMS.JmsSenderService;
import com.shadow.entity.Breed;
import com.shadow.entity.EventType;
import com.shadow.repository.BreedRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.servlet.function.ServerResponse;
import response.BadResponse;
import response.GoodResponse;
import response.ServerResponse;

@RestController
public class BreedController
{

    private final BreedRepos breedRepos;
    private final JmsSenderService jmsSenderService;



    @Autowired
    public BreedController(BreedRepos breedRepos, JmsSenderService jmsSenderService) {
        this.breedRepos = breedRepos;
        this.jmsSenderService = jmsSenderService;
    }


    @GetMapping(path = "/breed", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    private Iterable<Breed> findAll()
    {
        return breedRepos.findAll();
    }

    @GetMapping(path = "/add/breed", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    private ServerResponse add(String breedName)
    {
        Breed breed = new Breed();
        breed.setBreedName(breedName);
        Breed newbreed = breedRepos.save(breed);
        jmsSenderService.sendEvent(Breed.class, newbreed, EventType.CREATE);
        return new GoodResponse(newbreed);
    }

    @GetMapping(path = "/delete/breed", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    private ServerResponse delete(Long id){
        Breed breed = breedRepos.findById(id).orElse(null);
        if (breed == null)
        {
            return new BadResponse("Breed not found");
        }
        breedRepos.delete(breed);
        jmsSenderService.sendEvent(Breed.class, breed, EventType.DELETE);
        return new GoodResponse();
    }
}
