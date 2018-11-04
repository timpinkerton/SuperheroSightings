/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.controller;

import com.sg.superherosightings.dao.SuperheroSightingsDao;
import com.sg.superherosightings.model.Hero;
import com.sg.superherosightings.model.Organization;
import com.sg.superherosightings.model.Sighting;
import com.sg.superherosightings.model.Superpower;
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
public class SuperheroSightingsController {

    SuperheroSightingsDao dao;

    @Inject
    public SuperheroSightingsController(SuperheroSightingsDao dao) {
        this.dao = dao;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String displayRecentTen(Model model) {

        // Getting the 10 most recent sightings from the DAO
        List<Sighting> recentTenList = dao.getRecentSightings();
     
        // Put the List of Contacts on the Model
        model.addAttribute("recentTenList", recentTenList);

        //return the name on the View compoment
        return "index";
    }

    @RequestMapping(value = "/displayHeroesVillains", method = RequestMethod.GET)
    public String displayHeroesVillains(Model model) {
        // Get all the Contacts from the DAO
        List<Hero> heroList = dao.getAllHeroes();

        // Put the List of Contacts on the Model
        model.addAttribute("heroList", heroList);

        // Return the logical name of our View component
        return "/Hero/heroesVillains";
    }

    @RequestMapping(value = "/createHero", method = RequestMethod.POST)
    public String createHero(HttpServletRequest request) {
        // getting the input values from the form to create a new Hero
        Hero hero = new Hero();
        hero.setHeroName(request.getParameter("heroNameInput"));
        hero.setDescription(request.getParameter("descriptionInput"));

        // persist the new Hero using Dao
        dao.addHero(hero);

        // redirect to displayHeroesVillains
        return "redirect:displayHeroesVillains";
    }

    @RequestMapping(value = "/deleteHero", method = RequestMethod.GET)
    public String deleteHero(HttpServletRequest request) {

        //getting the id of the hero object
        String heroId = request.getParameter("heroId");
        int heroIdAsInt = Integer.parseInt(heroId);

        // delete the Hero using Dao
        dao.deleteHero(heroIdAsInt);

        // redirect to the displayHeroesVillains
        return "redirect:displayHeroesVillains";
    }

    @RequestMapping(value = "/displayHeroDetails", method = RequestMethod.GET)
    public String displayHeroDetails(HttpServletRequest request, Model model) {

        //getting the id of the hero object
        String heroId = request.getParameter("heroId");
        int heroIdAsInt = Integer.parseInt(heroId);

        // get the details from the Dao
        Hero hero = dao.getHeroById(heroIdAsInt);
        
        List<Superpower> superpowerList = dao.getSuperpowersByHeroId(heroIdAsInt); 
        List<Organization> organizationList = dao.getOrganizationsByHeroId(heroIdAsInt); 

        model.addAttribute("hero", hero);
        model.addAttribute("superpowerList", superpowerList); 
        model.addAttribute("organizationList", organizationList); 

        // redirect to displayHeroDetails
        return "/Hero/heroDetails";
    }

    @RequestMapping(value = "/displayEditHeroForm", method = RequestMethod.GET)
    public String displayEditHeroForm(HttpServletRequest request, Model model) {
        String heroIdParameter = request.getParameter("heroId");
        int heroId = Integer.parseInt(heroIdParameter);
        Hero hero = dao.getHeroById(heroId);
        model.addAttribute("hero", hero);

        return "/Hero/editHeroForm";
    }

    @RequestMapping(value = "/editHero", method = RequestMethod.POST)
    public String editHero(@Valid @ModelAttribute("hero") Hero hero, BindingResult result) {

        if (result.hasErrors()) {
            return "editHeroForm"; 
        }
        dao.updateHero(hero);

        return "redirect:displayHeroesVillains";
    }

}
