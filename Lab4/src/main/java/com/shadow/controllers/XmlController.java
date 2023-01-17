package com.shadow.controllers;

import com.shadow.entity.Breed;
import com.shadow.entity.Cat;
import com.shadow.repository.BreedRepos;
import com.shadow.repository.CatRepos;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;

@Controller
@RequestMapping("/xml")
public class XmlController {

    private final CatRepos catRepos;
    private final BreedRepos breedRepos;

    @Autowired
    public XmlController(CatRepos catRepos, BreedRepos breedRepos) {
        this.catRepos = catRepos;
        this.breedRepos = breedRepos;


    }

    @ResponseBody
    @GetMapping(path = "/cat", produces = MediaType.APPLICATION_XML_VALUE)
    private ModelAndView getCat() throws JsonProcessingException {
        Iterable<Cat> list =  catRepos.findAll();
        return getModelAndView(list, "catXSL");
    }

    @ResponseBody
    @GetMapping(path = "/breed", produces = MediaType.APPLICATION_XML_VALUE)
    private ModelAndView getBreed() throws JsonProcessingException{
        Iterable<Breed> list =  breedRepos.findAll();
        return getModelAndView(list, "breedXSL");
    }

    private ModelAndView getModelAndView(Iterable<?> list, String viewName) throws JsonProcessingException {
        String str = new XmlMapper().writeValueAsString(list);
        ModelAndView mod = new ModelAndView(viewName);
        Source src = new StreamSource(new StringReader(str));
        mod.addObject("ArrayList", src);
        return mod;
    }
}
