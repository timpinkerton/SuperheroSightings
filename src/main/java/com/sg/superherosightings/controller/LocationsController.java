/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.controller;

import com.sg.superherosightings.dao.SuperheroSightingsDao;
import com.sg.superherosightings.model.Location;
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
public class LocationsController {

    SuperheroSightingsDao dao;

    @Inject
    public LocationsController(SuperheroSightingsDao dao) {
        this.dao = dao;
    }

    @RequestMapping(value = "/displayLocations", method = RequestMethod.GET)
    public String displayLocations(Model model) {
        // Get all the Locations from the DAO
        List<Location> locationList = dao.getAllLocations();

        // Put the List of Locations on the Model
        model.addAttribute("locationList", locationList);

        // Return the logical name of our View component
        return "/Location/locations";
    }

    @RequestMapping(value = "/displayLocationDetails", method = RequestMethod.GET)
    public String displayLocationDetails(HttpServletRequest request, Model model) {

        //getting the id of the Location object
        String locationId = request.getParameter("locationId");
        int locationIdAsInt = Integer.parseInt(locationId);

        // get the details from the Dao
        Location location = dao.getLocationById(locationIdAsInt);

        model.addAttribute("location", location);

        // redirect to displayHeroDetails
        return "/Location/locationDetails";
    }

    @RequestMapping(value = "/deleteLocation", method = RequestMethod.GET)
    public String deleteLocation(HttpServletRequest request) {

        //getting the id of the Location object
        String locationIdParameter = request.getParameter("locationId");
        int locationId = Integer.parseInt(locationIdParameter);

        dao.deleteLocation(locationId);

        return "redirect:displayLocations";
    }

    @RequestMapping(value = "/createLocation", method = RequestMethod.POST)
    public String createLocation(HttpServletRequest request) {
        // getting the input values from the form to create a new Hero
        Location location = new Location();

        location.setLocationName(request.getParameter("locationNameInput"));
        location.setDescription(request.getParameter("descriptionInput"));
        location.setStreet(request.getParameter("streetInput"));
        location.setCity(request.getParameter("cityInput"));
        location.setState(request.getParameter("stateInput"));

        String zipInput = request.getParameter("zipInput");

        if (zipInput.equalsIgnoreCase("")) {
            //do nothing
        } else {
            int zip = Integer.parseInt(zipInput);
            location.setZip(zip);
        }

        location.setCountry(request.getParameter("countryInput"));

        String latitudeInput = request.getParameter("latitudeInput");
        if (latitudeInput.equalsIgnoreCase("")) {
            //do nothing
        } else {
            double latitude = Double.parseDouble(latitudeInput);
            location.setLatitude(latitude);
        }

        String longitudeInput = request.getParameter("longitudeInput");

        if (longitudeInput.equalsIgnoreCase("")) {
            //do nothing
        } else {
            double longitude = Double.parseDouble(longitudeInput);
            location.setLongitude(longitude);
        }
        
        // persist the new Hero using Dao
        dao.addLocation(location);

        // redirect to displayHeroesVillains
        return "redirect:displayLocations";
    }

    @RequestMapping(value = "/displayEditLocationForm", method = RequestMethod.GET)
    public String displayEditLocationForm(HttpServletRequest request, Model model) {
        String locationIdParameter = request.getParameter("locationId");
        int locationId = Integer.parseInt(locationIdParameter);

        Location location = dao.getLocationById(locationId);

        model.addAttribute("location", location);

        return "/Location/editLocationForm";
    }

    @RequestMapping(value = "/editLocation", method = RequestMethod.POST)
    public String editLocation(@Valid @ModelAttribute("location") Location location, BindingResult result) {

        if (result.hasErrors()) {
            return "editLocationForm";
        }
        dao.updateLocation(location);

        return "redirect:displayLocations";
    }

}
