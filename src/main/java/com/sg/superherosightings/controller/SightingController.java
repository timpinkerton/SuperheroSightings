/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.controller;

import com.sg.superherosightings.dao.SuperheroSightingsDao;
import com.sg.superherosightings.model.Hero;
import com.sg.superherosightings.model.Location;
import com.sg.superherosightings.model.Organization;
import com.sg.superherosightings.model.Sighting;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author timpinkerton
 */
@Controller
public class SightingController {

    SuperheroSightingsDao dao;

    @Inject
    public SightingController(SuperheroSightingsDao dao) {
        this.dao = dao;
    }

    @RequestMapping(value = "/displaySightings", method = RequestMethod.GET)
    public String displaySightings(Model model) {
        // Get all the Contacts from the DAO
        List<Sighting> sightingList = dao.getAllSightings();
        List<Location> locationList = dao.getAllLocations();
        List<Hero> heroList = dao.getAllHeroes();

        // Put the List of Contacts on the Model
        model.addAttribute("sightingList", sightingList);
        model.addAttribute("locationList", locationList);
        model.addAttribute("heroList", heroList);
        // Return the logical name of our View component
        return "/Sighting/sightings";
    }

    @RequestMapping(value = "/displaySightingDetails", method = RequestMethod.GET)
    public String displaySightingDetails(HttpServletRequest request, Model model) {

        //getting the id of the Sighting object
        String sightingIdParameter = request.getParameter("sightingId");
        int sightingId = Integer.parseInt(sightingIdParameter);

        // get the details from the Dao
        Sighting sighting = dao.getSightingById(sightingId);

        model.addAttribute("sighting", sighting);

        // redirect to displaySightingDetails
        return "/Sighting/sightingDetails";
    }

    @RequestMapping(value = "/deleteSighting", method = RequestMethod.GET)
    public String deleteSighting(HttpServletRequest request) {

        String sightingIdParameter = request.getParameter("sightingId");
        int sightingId = Integer.parseInt(sightingIdParameter);

        dao.deleteSighting(sightingId);

        return "redirect:displaySightings";
    }

    @RequestMapping(value = "/createSighting", method = RequestMethod.POST)
    public String createSighting(HttpServletRequest request) {

        // getting the input values from the form to create a new Hero
        Sighting sighting = new Sighting();
        
        String sightingDate = request.getParameter("sightingDateInput");
        
        //getting the date and time; excluding the am/pm
        String formattedDate = sightingDate.substring(0, 16); 
        sighting.setSightingDate(formattedDate);

        String locationIdParameter = request.getParameter("selectedLocation");
        int locationId = Integer.parseInt(locationIdParameter);

        sighting.setLocationId(locationId);

        String[] listOfHeroIds = request.getParameterValues("selectedHeroes");

        //Creating a new heroList to hold the heroes selected
        List<Hero> heroList = new ArrayList<>();

        for (String currentId : listOfHeroIds) {
            int heroId = Integer.parseInt(currentId);
            Hero hero = dao.getHeroById(heroId);

            heroList.add(hero);
        }

        sighting.setHeroes(heroList);

        // persist the new Hero using Dao
        dao.addSighting(sighting);

        // redirect to displayHeroesVillains
        return "redirect:displaySightings";
    }
    
    
    @RequestMapping(value = "/displayEditSightingForm", method = RequestMethod.GET)
    public String displayEditSightingForm(HttpServletRequest request, Model model) {
        
        String sightingIdParameter = request.getParameter("sightingId");
        int sightingId = Integer.parseInt(sightingIdParameter);
        
        Sighting sighting = dao.getSightingById(sightingId); 
        List<Hero> heroList = sighting.getHeroes(); 
        List<Hero> fullHeroList = dao.getAllHeroes(); 

        List<Location> locationList = dao.getAllLocations();
        
        model.addAttribute("sighting", sighting);
        model.addAttribute("heroList", heroList); 
        model.addAttribute("locationList", locationList);
        model.addAttribute("fullHeroList", fullHeroList); 
        return "/Sighting/editSightingForm";
    }

    @RequestMapping(value = "/editSighting", method = RequestMethod.POST)
    public String editSighting(HttpServletRequest request) {
        
        Sighting updatedSighting = new Sighting(); 
        
        String sightingIdParameter = request.getParameter("sightingId"); 
        int sightingId = Integer.parseInt(sightingIdParameter); 
        
        updatedSighting.setSightingId(sightingId);
        
        Sighting oldSighting = dao.getSightingById(sightingId); 
        
        String newDate = request.getParameter("sightingDate"); 
        String locationIdParameter = request.getParameter("locationId");
        
        if (newDate.equalsIgnoreCase("")) {
            //if date was blank, it will be set with the old date
            updatedSighting.setSightingDate(oldSighting.getSightingDate());
        } else {
            updatedSighting.setSightingDate(newDate);
        }
        
        if (locationIdParameter.equalsIgnoreCase("")) {
            updatedSighting.setLocationId(oldSighting.getLocationId());
        } else {
            int locationId = Integer.parseInt(locationIdParameter);

            updatedSighting.setLocationId(locationId);
        }
        
        //getting list of hero's id's from the editForm
        String[] listOfHeroIds = request.getParameterValues("heroes");

        if (listOfHeroIds == null) {
            //if no heroes are selected, superpower is set with the previous list of heroes
            updatedSighting.setHeroes(oldSighting.getHeroes());
        } else {
            //Creating a new heroList to hold the heroes selected
            List<Hero> heroList = new ArrayList<>();

            for (String currentId : listOfHeroIds) {
                int heroId = Integer.parseInt(currentId);
                Hero hero = dao.getHeroById(heroId);

                heroList.add(hero);
            }
            updatedSighting.setHeroes(heroList);
        }
        
        dao.updateSighting(updatedSighting);
        
        return "redirect:dispalySightings"; 
    }
    
}
