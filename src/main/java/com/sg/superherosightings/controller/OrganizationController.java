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
public class OrganizationController {

    SuperheroSightingsDao dao;

    @Inject
    public OrganizationController(SuperheroSightingsDao dao) {
        this.dao = dao;
    }

    @RequestMapping(value = "/displayOrganizations", method = RequestMethod.GET)
    public String displayOrganizations(Model model) {
        // Get all the organization from the DAO
        List<Organization> organizationList = dao.getAllOrganizations();

        List<Location> locationList = dao.getAllLocations();
        List<Hero> heroList = dao.getAllHeroes();

        // Put the List of Locations on the Model
        model.addAttribute("organizationList", organizationList);
        model.addAttribute("locationList", locationList);
        model.addAttribute("heroList", heroList);

        // Return the logical name of our View component
        return "/Organization/organizations";
    }

    @RequestMapping(value = "/displayOrganizationDetails", method = RequestMethod.GET)
    public String displayOrganizationDetails(HttpServletRequest request, Model model) {

        //getting the id of the Superpower object
        String organizationId = request.getParameter("organizationId");
        int organizationIdAsInt = Integer.parseInt(organizationId);

        // get the details from the Dao
        Organization organization = dao.getOrganizationById(organizationIdAsInt);

        List<Hero> heroList = organization.getHeroes();

        model.addAttribute("organization", organization);
        model.addAttribute("heroList", heroList);

        // redirect to displayHeroDetails
        return "/Organization/organizationDetails";
    }

    @RequestMapping(value = "/deleteOrganization", method = RequestMethod.GET)
    public String deleteOrganization(HttpServletRequest request) {

        //getting the id of the Organization object
        String organizationId = request.getParameter("organizationId");
        int organizationIdAsInt = Integer.parseInt(organizationId);

        // delete the Organization using Dao
        dao.deleteOrganization(organizationIdAsInt);

        // redirect to the displaySuperpowers
        return "redirect:displayOrganizations";
    }

    @RequestMapping(value = "/createOrganization", method = RequestMethod.POST)
    public String createOrganization(HttpServletRequest request) {

        // getting the input values from the form to create a new Organization
        Organization organization = new Organization();

        organization.setOrganizationName(request.getParameter("organizationNameInput"));
        organization.setDescription(request.getParameter("descriptionInput"));
        organization.setPhone(request.getParameter("phoneInput"));
        organization.setEmail(request.getParameter("emailInput"));

        String locationIdParameter = request.getParameter("selectedLocation");
        int locationId = Integer.parseInt(locationIdParameter);

        organization.setLocationId(locationId);

        String[] listOfHeroIds = request.getParameterValues("heroesInOrganization");

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

            organization.setHeroes(heroList);
        }

        // persist the new Organization using Dao
        dao.addOrganization(organization);

        // redirect to displayOrganizations
        return "redirect:displayOrganizations";
    }

    @RequestMapping(value = "/displayEditOrganizationForm", method = RequestMethod.GET)
    public String displayEditOrganizationForm(HttpServletRequest request, Model model) {
        String organizationIdParameter = request.getParameter("organizationId");
        int organizationId = Integer.parseInt(organizationIdParameter);

        Organization organization = dao.getOrganizationById(organizationId);

        List<Hero> heroList = organization.getHeroes();

        List<Hero> fullHeroList = dao.getAllHeroes();

        List<Location> locationList = dao.getAllLocations();

        model.addAttribute("organization", organization);
        model.addAttribute("heroList", heroList);
        model.addAttribute("locationList", locationList);
        model.addAttribute("fullHeroList", fullHeroList);
        return "/Organization/editOrganizationForm";
    }

    @RequestMapping(value = "/editOrganization", method = RequestMethod.POST)
    public String editOrganization(HttpServletRequest request) {

        Organization updatedOrganziation = new Organization();

        String organizationIdParameter = request.getParameter("organizationId");
        int organizationId = Integer.parseInt(organizationIdParameter);

        updatedOrganziation.setOrganizationId(organizationId);

        Organization oldOrganization = dao.getOrganizationById(organizationId);

        String newName = request.getParameter("organizationName");
        String newDescription = request.getParameter("description");
        String newPhone = request.getParameter("phone");
        String newEmail = request.getParameter("email");
        String locationIdParameter = request.getParameter("locationId");

        if (newName.equalsIgnoreCase("")) {
            //if name was blank, it will be set with the old name
            updatedOrganziation.setOrganizationName(oldOrganization.getOrganizationName());
        } else {
            updatedOrganziation.setOrganizationName(newName);
        }

        if (newDescription.equalsIgnoreCase("")) {
            updatedOrganziation.setDescription(oldOrganization.getDescription());
        } else {
            updatedOrganziation.setDescription(newDescription);
        }

        if (newPhone.equalsIgnoreCase("")) {
            updatedOrganziation.setPhone(oldOrganization.getPhone());
        } else {
            updatedOrganziation.setPhone(newPhone);
        }

        if (newEmail.equalsIgnoreCase("")) {
            updatedOrganziation.setEmail(oldOrganization.getEmail());
        } else {
            updatedOrganziation.setEmail(newEmail);
        }

        if (locationIdParameter.equalsIgnoreCase("")) {
            updatedOrganziation.setLocationId(oldOrganization.getLocationId());
        } else {
            int locationId = Integer.parseInt(locationIdParameter);

            updatedOrganziation.setLocationId(locationId);
        }

        //getting list of hero's id's from the editForm
        String[] listOfHeroIds = request.getParameterValues("heroes");

        if (listOfHeroIds == null) {
            //if no heroes are selected, superpower is set with the previous list of heroes
            updatedOrganziation.setHeroes(oldOrganization.getHeroes());
        } else {
            //Creating a new heroList to hold the heroes selected
            List<Hero> heroList = new ArrayList<>();

            for (String currentId : listOfHeroIds) {
                int heroId = Integer.parseInt(currentId);
                Hero hero = dao.getHeroById(heroId);

                heroList.add(hero);
            }
            updatedOrganziation.setHeroes(heroList);
        }

        dao.updateOrganization(updatedOrganziation);

        return "redirect:displayOrganizations";
    }
}
