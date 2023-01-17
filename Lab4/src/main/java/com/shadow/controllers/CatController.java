package com.shadow.controllers;

import com.shadow.JMS.JmsSenderService;
import com.shadow.entity.Breed;
import com.shadow.entity.Cat;
import com.shadow.entity.EventType;
import com.shadow.repository.BreedRepos;
import com.shadow.repository.CatRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.servlet.function.ServerResponse;
import response.BadResponse;
import response.GoodResponse;
import response.ServerResponse;

@RestController
public class CatController
{
    private final CatRepos catRepos;
    private final BreedRepos breedRepos;
    private final JmsSenderService jmsSenderService;

    @Autowired
    public CatController(CatRepos catRepos, BreedRepos breedRepos, JmsSenderService jmsSenderService ) {
        this.catRepos = catRepos;
        this.breedRepos = breedRepos;
        this.jmsSenderService= jmsSenderService;
    }


    @GetMapping(path = "/cat", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    private Iterable<Cat> findAll()
    {
        return catRepos.findAll();
    }

    @GetMapping(path = "/add/cat", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    private ServerResponse add(String name, String color, int age, long breed_id)
    {
        Breed breed = breedRepos.findById(breed_id).orElse(null);
        if (breed == null)
        {
            return new BadResponse("Breed not found");
        }
        Cat newCat = new Cat();
        newCat.setName(name);
        newCat.setColor(color);
        newCat.setAge(age);
        newCat.setBreed(breed);
        jmsSenderService.sendCatUpdate(newCat, EventType.CREATE);
        jmsSenderService.sendEvent(Cat.class, newCat, EventType.CREATE);
        return new GoodResponse(newCat);
    }

    @GetMapping(path = "/delete/cat", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    private ServerResponse delete(String name){
        Cat cat = catRepos.findById(name).orElse(null);
        if (cat == null)
        {
            return new BadResponse("Cat not found");
        }
        catRepos.delete(cat);
        jmsSenderService.sendCatUpdate(cat, EventType.DELETE);
        jmsSenderService.sendEvent(Cat.class, cat, EventType.DELETE);
        return new GoodResponse();
    }
}

