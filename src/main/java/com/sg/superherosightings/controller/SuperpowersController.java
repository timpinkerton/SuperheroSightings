/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.controller;

import com.sg.superherosightings.dao.SuperheroSightingsDao;
import com.sg.superherosightings.model.Hero;
import com.sg.superherosightings.model.Superpower;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author timpinkerton
 */
@Controller
public class SuperpowersController {

    SuperheroSightingsDao dao;

    @Inject
    public SuperpowersController(SuperheroSightingsDao dao) {
        this.dao = dao;
    }

    @RequestMapping(value = "/displaySuperpowers", method = RequestMethod.GET)
    public String displaySuperpowers(Model model) {
        // Get all the superpowers from the DAO
        List<Superpower> superpowerList = dao.getAllSuperpowers();

        List<Hero> heroList = dao.getAllHeroes();

        // Put the List of Supwerpowers on the Model
        model.addAttribute("superpowerList", superpowerList);
        model.addAttribute("heroList", heroList);

        // Return the logical name of our View component
        return "/Superpower/superpowers";
    }

    @RequestMapping(value = "/deleteSuperpower", method = RequestMethod.GET)
    public String deleteSuperpower(HttpServletRequest request) {

        //getting the id of the Superpower object
        String superpowerId = request.getParameter("superpowerId");
        int superpowerIdAsInt = Integer.parseInt(superpowerId);

        // delete the Superpower using Dao
        dao.deleteSuperpower(superpowerIdAsInt);

        // redirect to the displaySuperpowers
        return "redirect:displaySuperpowers";
    }

    @RequestMapping(value = "/displaySuperpowerDetails", method = RequestMethod.GET)
    public String displaySuperpowerDetails(HttpServletRequest request, Model model) {

        //getting the id of the Superpower object
        String superpowerId = request.getParameter("superpowerId");
        int superpowerIdAsInt = Integer.parseInt(superpowerId);

        // get the details from the Dao
        Superpower superpower = dao.getSuperpowerById(superpowerIdAsInt);

        List<Hero> heroList = superpower.getHeroes();

        model.addAttribute("superpower", superpower);
        model.addAttribute("heroList", heroList);

        // redirect to displayHeroDetails
        return "/Superpower/superpowerDetails";
    }

    @RequestMapping(value = "/displayEditSuperpowerForm", method = RequestMethod.GET)
    public String displayEditSuperpowerForm(HttpServletRequest request, Model model) {
        String superpowerIdParameter = request.getParameter("superpowerId");
        int superpowerIdAsInt = Integer.parseInt(superpowerIdParameter);

        Superpower superpower = dao.getSuperpowerById(superpowerIdAsInt);

        List<Hero> heroList = superpower.getHeroes();

        List<Hero> fullHeroList = dao.getAllHeroes();

        model.addAttribute("superpower", superpower);
        model.addAttribute("heroList", heroList);
        model.addAttribute("fullHeroList", fullHeroList);
        return "/Superpower/editSuperpowerForm";
    }

    @RequestMapping(value = "/createSuperpower", method = RequestMethod.POST)
    public String createSuperpower(HttpServletRequest request) {
        // getting the input values from the form to create a new Superpower

        Superpower superpower = new Superpower();
        superpower.setDescription(request.getParameter("superpowerInput"));

        String[] listOfHeroIds = request.getParameterValues("selectedHeroes");

        if (listOfHeroIds == null) {
            //do nothing
        } else {
            //Creating a new heroList to hold the heroes selected
            List<Hero> heroList = new ArrayList<>();

            for (String currentId : listOfHeroIds) {
                int heroId = Integer.parseInt(currentId);
                Hero hero = dao.getHeroById(heroId);

                heroList.add(hero);
            }

            superpower.setHeroes(heroList);
        }

        // persist the new Hero using Dao
        dao.addSuperpower(superpower);

        // redirect to displayHeroesVillains
        return "redirect:displaySuperpowers";
    }

    @RequestMapping(value = "/editSuperpower", method = RequestMethod.POST)
    public String editSuperpower(HttpServletRequest request) {

        //create a new superpower
        Superpower superpower = new Superpower();
        //get the Id of the superpower that is being updated
        String superpowerIdParameter = request.getParameter("superpowerId");
        int superpowerId = Integer.parseInt(superpowerIdParameter);
        //set the id of the new superpower
        superpower.setSuperpowerId(superpowerId);

        //getting the old superpower info
        Superpower oldSuperpower = dao.getSuperpowerById(superpowerId);
        //getting the new description that was input
        String newDescription = request.getParameter("superpower");

        if (newDescription.equalsIgnoreCase("")) {
            //if description was blank, it will be set with the old description
            superpower.setDescription(oldSuperpower.getDescription());
        } else {
            superpower.setDescription(newDescription);
        }

        //getting list of hero's id's from the editForm
        String[] listOfHeroIds = request.getParameterValues("selectedHeroes");

        if (listOfHeroIds == null) {
            //if no heroes are selected, superpower is set with the previous list of heroes
            superpower.setHeroes(oldSuperpower.getHeroes());
        } else {
            //Creating a new heroList to hold the heroes selected
            List<Hero> heroList = new ArrayList<>();

            for (String currentId : listOfHeroIds) {
                int heroId = Integer.parseInt(currentId);
                Hero hero = dao.getHeroById(heroId);

                heroList.add(hero);
            }
            superpower.setHeroes(heroList);
        }

        dao.updateSuperpower(superpower);

        return "redirect:displaySuperpowers";
    }

}
