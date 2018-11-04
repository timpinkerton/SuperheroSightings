/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.dao;

import com.sg.superherosightings.model.Hero;
import com.sg.superherosightings.model.Location;
import com.sg.superherosightings.model.Organization;
import com.sg.superherosightings.model.Sighting;
import com.sg.superherosightings.model.Superpower;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author timpinkerton
 */
public interface SuperheroSightingsDao {

    //Methods for Hero **************************************
    //Create
    public void addHero(Hero hero);

    //Read one
    public Hero getHeroById(int heroId);

    //Read all
    public List<Hero> getAllHeroes();
    
    //report all of the superheroes sighted at a particular location
    public List<Hero> getHeroesByLocationId(int locationId); 
    
    //report all of the members of a particular organization
    public List<Hero> getHeroesByOrganization(Organization organization); 
    
    //Update
    public void updateHero(Hero hero);

    //Delete
    public void deleteHero(int heroId);

    //Methods for Location **********************************
    //Create
    public void addLocation(Location location);

    //Read one
    public Location getLocationById(int locationId);

    //Read all
    public List<Location> getAllLocations();

    //report all of the locations where a particular superhero has been seen
    public List<Location> getLocationsByHeroId(int heroId); 
    
    //Update
    public void updateLocation(Location location);

    //Delete
    public void deleteLocation(int locationId);

    //Methods for Oranization ******************************
    //Create
    public void addOrganization(Organization organization);

    //Read one
    public Organization getOrganizationById(int organizationId);

    //Read all
    public List<Organization> getAllOrganizations();
    
    //report all of the organizations a particular superhero/villain belongs to.
    public List<Organization> getOrganizationsByHeroId(int heroInt);

    //unused
//    public List<Organization> getOrganizationsByLocationId(int locationId);

    //Update
    public void updateOrganization(Organization organization);

    //Delete
    public void deleteOrganization(int organizationId);

    //Methods for Sighting ******************************
    //Create
    public void addSighting(Sighting sighting);

    //Read one
    public Sighting getSightingById(int sightingId);

    //Read all
    public List<Sighting> getAllSightings();
    
    //Read 10 recent sightings
    public List<Sighting> getRecentSightings();
    
    //report all sightings (hero and location) for a particular date
    public List<Sighting> getSightingsByDate(String searchDate); 

    public List<Sighting> getSightingsByHeroId(int heroId);

    public List<Sighting> getSightingsByLocationId(int locationId);

    //Update
    public void updateSighting(Sighting sighting);

    //Delete
    public void deleteSighting(int sightingId);

    //Methods for Superpower ******************************
    //Create
    public void addSuperpower(Superpower superpower);

    //Read one
    public Superpower getSuperpowerById(int superpowerId);

    //Read all
    public List<Superpower> getAllSuperpowers();
    
    public List<Superpower> getSuperpowersByHeroId(int heroId);

    //Update
    public void updateSuperpower(Superpower superpower);

    //Delete
    public void deleteSuperpower(int superpowerId);

}
