/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.test.dao;

import com.sg.superherosightings.dao.SuperheroSightingsDao;
import com.sg.superherosightings.model.Hero;
import com.sg.superherosightings.model.Location;
import com.sg.superherosightings.model.Organization;
import com.sg.superherosightings.model.Sighting;
import com.sg.superherosightings.model.Superpower;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author timpinkerton
 */
public class SuperheroSightingsDaoTest {

    SuperheroSightingsDao dao;

    public SuperheroSightingsDaoTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {

        ApplicationContext ctx
                = new ClassPathXmlApplicationContext("test-applicationContext.xml");

        dao = ctx.getBean("SuperheroSightingsDao", SuperheroSightingsDao.class);

        //delete Sighting table
        List<Sighting> sightings = dao.getAllSightings();
        for (Sighting currentSighting : sightings) {
            dao.deleteSighting(currentSighting.getSightingId());
        }

        //delete Organization table
        List<Organization> organizations = dao.getAllOrganizations();
        for (Organization currentOrganization : organizations) {
            dao.deleteOrganization(currentOrganization.getOrganizationId());
        }

        //delete Location table
        List<Location> locations = dao.getAllLocations();
        for (Location currentLocation : locations) {
            dao.deleteLocation(currentLocation.getLocationId());
        }

        //delete Hero table
        List<Hero> heroes = dao.getAllHeroes();
        for (Hero currentHero : heroes) {
            dao.deleteHero(currentHero.getHeroId());
        }

        //delete Superpower table
        List<Superpower> superpowers = dao.getAllSuperpowers();
        for (Superpower currentSuperpower : superpowers) {
            dao.deleteSuperpower(currentSuperpower.getSuperpowerId());
        }

    }

    @After
    public void tearDown() {
    }

    /**
     * Test of addHero method, of class SuperheroSightingsDao.
     */
    @Test
    public void testAddGetHero() {

        Hero hero = new Hero();
        hero.setHeroName("Bobby");
        hero.setDescription("Super loud");

        dao.addHero(hero);

        Hero heroFromDao = dao.getHeroById(hero.getHeroId());

        assertEquals(heroFromDao, hero);
    }

    /**
     * Test of getHeroById method, of class SuperheroSightingsDao.
     */
//    @Test
    public void testGetHeroById() {
        //tested in testAdGetHero() above
    }

    /**
     * Test of getAllHeroes method, of class SuperheroSightingsDao.
     */
    @Test
    public void testGetAllHeroes() {

        Hero hero1 = new Hero();
        hero1.setHeroName("Bobby");
        hero1.setDescription("fast");

        dao.addHero(hero1);

        Hero hero2 = new Hero();
        hero2.setHeroName("Whitney");
        hero2.setDescription("strong");

        dao.addHero(hero2);

        assertEquals(2, dao.getAllHeroes().size());
    }

    /**
     * Test of getHeroesByOrganization method, of class SuperheroSightingsDao.
     */
    @Test
    public void testGetHeroesByOrganization() {
        
        //creating a hero
        Hero hero = new Hero(); 
        hero.setHeroName("Jackie");
        hero.setDescription("fast");
        
        dao.addHero(hero);
        
        //creating a location to add as the foreign key in the organization
        Location location = new Location(); 
        location.setLocationName("HQ");
        location.setDescription("world headquarters");
        location.setStreet("123 nunya");
        location.setCity("Gennessee");
        location.setState("CO");
        location.setZip(12345);
        location.setCountry("USA");
        location.setLatitude(12.123456);
        location.setLongitude(120.123456);
        
        dao.addLocation(location);
                
        //creating an organization
        Organization organization = new Organization(); 
        organization.setOrganizationName("Jackson 5");
        organization.setDescription("musical group");
        organization.setPhone("9099990000");
        organization.setEmail("five@jackson.com");
        organization.setLocationId(location.getLocationId());
        
        List<Hero> heroes = new ArrayList<>();
        heroes.add(hero);

        organization.setHeroes(heroes);
        
        dao.addOrganization(organization);

        List<Hero> heroesList = dao.getHeroesByOrganization(organization);
        
        assertEquals(hero, heroesList.get(0)); 
    }
    
    /**
     * Test of updateHero method, of class SuperheroSightingsDao.
     */
    @Test
    public void testUpdateHero() {
        
        //creating a hero
        Hero hero = new Hero(); 
        hero.setHeroName("Jackie");
        hero.setDescription("fast");
        
        dao.addHero(hero);
        
        hero.setDescription("sneaky");
        
        dao.updateHero(hero);
        
        //get hero from dao. 
        Hero updatedHeroFromDao = dao.getHeroById(hero.getHeroId());
        
        //test that it matches
        assertEquals(updatedHeroFromDao, hero); 
    }

    /**
     * Test of deleteHero method, of class SuperheroSightingsDao.
     */
    @Test
    public void testDeleteHero() {

        Hero hero1 = new Hero();
        hero1.setHeroName("Kevin");
        hero1.setDescription("fast");

        dao.addHero(hero1);

        assertEquals(1, dao.getAllHeroes().size());
        
        dao.deleteHero(hero1.getHeroId());
        
        assertEquals(0, dao.getAllHeroes().size()); 
    }

    /**
     * Test of addLocation method, of class SuperheroSightingsDao.
     */
    @Test
    public void testAddGetLocation() {

        Location location = new Location();
        location.setLocationName("HQ");
        location.setDescription("world headquarters");
        location.setStreet("123 nunya");
        location.setCity("Gennessee");
        location.setState("CO");
        location.setZip(12345);
        location.setCountry("USA");
        location.setLatitude(12.123456);
        location.setLongitude(120.123456);

        dao.addLocation(location);

        Location locationFromDao = dao.getLocationById(location.getLocationId());

        assertEquals(locationFromDao, location);

    }

    /**
     * Test of getLocationById method, of class SuperheroSightingsDao.
     */
//    @Test
    public void testGetLocationById() {
        //tested in testAddGetLocation() above
    }

    /**
     * Test of getAllLocations method, of class SuperheroSightingsDao.
     */
    @Test
    public void testGetAllLocations() {

        Location location1 = new Location();
        location1.setLocationName("HQ");
        location1.setDescription("world headquarters");
        location1.setStreet("123 nunya");
        location1.setCity("Gennessee");
        location1.setState("CO");
        location1.setZip(12345);
        location1.setCountry("USA");
        location1.setLatitude(12.123456);
        location1.setLongitude(120.123456);

        dao.addLocation(location1);

        Location location2 = new Location();
        location2.setLocationName("HQ2");
        location2.setDescription("Second world headquarters");
        location2.setStreet("456 nunya");
        location2.setCity("Boulder");
        location2.setState("CO");
        location2.setZip(54321);
        location2.setCountry("UK");
        location2.setLatitude(19.123456);
        location2.setLongitude(109.123456);

        dao.addLocation(location2);

        assertEquals(2, dao.getAllLocations().size());

    }

    /**
     * Test of getLocationsByHeroId method, of class SuperheroSightingsDao.
     */
    @Test
    public void testGetLocationsByHeroId() {
        
        Hero hero1 = new Hero();
        hero1.setHeroName("Kevin");
        hero1.setDescription("fast");

        dao.addHero(hero1);
        
        Location location = new Location(); 
        location.setLocationName("HQ");
        location.setDescription("world headquarters");
        location.setStreet("123 nunya");
        location.setCity("Gennessee");
        location.setState("CO");
        location.setZip(12345);
        location.setCountry("USA");
        location.setLatitude(12.123456);
        location.setLongitude(120.123456);
        
        dao.addLocation(location);

        Sighting sighting = new Sighting();
        LocalDateTime ldt = LocalDateTime.now();
        sighting.setSightingDate(ldt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm")));
        sighting.setLocationId(location.getLocationId());

        List<Hero> heroes = new ArrayList<>();
        heroes.add(hero1);

        sighting.setHeroes(heroes);

        dao.addSighting(sighting);
        
        //get a list of locations by heroId
        List<Location> locationsList = dao.getLocationsByHeroId(hero1.getHeroId()); 
        
        assertEquals(locationsList.get(0), location); 
             
    }
    
    /**
     * Test of getHeroesByLocationId method, of class SuperheroSightingsDao.
     */
    @Test
    //report all of the superheroes sighted at a particular location
    public void testGetHeroesByLocationId() {
        
        Hero hero1 = new Hero();
        hero1.setHeroName("Kevin");
        hero1.setDescription("fast");

        dao.addHero(hero1);
        
        Location location = new Location(); 
        location.setLocationName("HQ");
        location.setDescription("world headquarters");
        location.setStreet("123 nunya");
        location.setCity("Gennessee");
        location.setState("CO");
        location.setZip(12345);
        location.setCountry("USA");
        location.setLatitude(12.123456);
        location.setLongitude(120.123456);
        
        dao.addLocation(location);

        Sighting sighting = new Sighting();
        LocalDateTime ldt = LocalDateTime.now();
        sighting.setSightingDate(ldt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm")));
        sighting.setLocationId(location.getLocationId());

        List<Hero> heroes = new ArrayList<>();
        heroes.add(hero1);

        sighting.setHeroes(heroes);

        dao.addSighting(sighting);
        
        List<Hero> heroesList = dao.getHeroesByLocationId(location.getLocationId());
        
        assertEquals(heroesList.get(0), hero1); 
        
    }

    /**
     * Test of updateLocation method, of class SuperheroSightingsDao.
     */
    @Test
    public void testUpdateLocation() {
        
        Location location = new Location(); 
        location.setLocationName("HQ");
        location.setDescription("world headquarters");
        location.setStreet("123 nunya");
        location.setCity("Gennessee");
        location.setState("CO");
        location.setZip(12345);
        location.setCountry("USA");
        location.setLatitude(12.123456);
        location.setLongitude(120.123456);
        
        dao.addLocation(location);
        
        assertEquals(1, dao.getAllLocations().size()); 
        
        location.setLocationName("Secret Hideout");

        dao.updateLocation(location);
        
        Location locationFromDao = dao.getLocationById(location.getLocationId());
        
        assertEquals(locationFromDao, location); 
    }

    /**
     * Test of deleteLocation method, of class SuperheroSightingsDao.
     */
    @Test
    public void testDeleteLocation() {
        
        Location location = new Location(); 
        location.setLocationName("HQ");
        location.setDescription("world headquarters");
        location.setStreet("123 nunya");
        location.setCity("Gennessee");
        location.setState("CO");
        location.setZip(12345);
        location.setCountry("USA");
        location.setLatitude(12.123456);
        location.setLongitude(120.123456);
        
        dao.addLocation(location);
        
        assertEquals(1, dao.getAllLocations().size()); 
        
        dao.deleteLocation(location.getLocationId());
        
        assertEquals(0, dao.getAllLocations().size());
    }

    /**
     * Test of addGetOrganization method, of class SuperheroSightingsDao.
     */
    @Test
    public void testAddGetOrganization() {
        
        Hero hero = new Hero();
        hero.setHeroName("Bobby");
        hero.setDescription("Super loud");

        dao.addHero(hero);
        
        //creating a location to add as the foreign key in the organization
        Location location = new Location(); 
        location.setLocationName("HQ");
        location.setDescription("world headquarters");
        location.setStreet("123 nunya");
        location.setCity("Gennessee");
        location.setState("CO");
        location.setZip(12345);
        location.setCountry("USA");
        location.setLatitude(12.123456);
        location.setLongitude(120.123456);
        
        dao.addLocation(location);
                
        Organization organization = new Organization(); 
        organization.setOrganizationName("Jackson 5");
        organization.setDescription("musical group");
        organization.setPhone("9099990000");
        organization.setEmail("five@jackson.com");
        organization.setLocationId(location.getLocationId());
        
        List<Hero> heroes = new ArrayList<>();
        heroes.add(hero);

        organization.setHeroes(heroes);
        
        dao.addOrganization(organization);
        
        Organization organizationFromDao = 
                dao.getOrganizationById(organization.getOrganizationId());
        
        assertEquals(organizationFromDao, organization);
    }
    
    /**
     * Test of getOrganizationById method, of class SuperheroSightingsDao.
     */
//    @Test
    public void testGetOrganizationById() {
        //tested in testAddGetOrganization() above
    }

    /**
     * Test of getAllOrganizations method, of class SuperheroSightingsDao.
     */
    @Test
    public void testGetAllOrganizations() {
        
        Hero hero = new Hero();
        hero.setHeroName("Bobby");
        hero.setDescription("Super loud");

        dao.addHero(hero);
        
        //creating a location to add as the foreign key in the organization
        Location location = new Location(); 
        location.setLocationName("HQ");
        location.setDescription("world headquarters");
        location.setStreet("123 nunya");
        location.setCity("Gennessee");
        location.setState("CO");
        location.setZip(12345);
        location.setCountry("USA");
        location.setLatitude(12.123456);
        location.setLongitude(120.123456);
        
        dao.addLocation(location);
                
        Organization organization = new Organization(); 
        organization.setOrganizationName("Jackson 5");
        organization.setDescription("musical group");
        organization.setPhone("9099990000");
        organization.setEmail("five@jackson.com");
        organization.setLocationId(location.getLocationId());
        
        List<Hero> heroes = new ArrayList<>();
        heroes.add(hero);

        organization.setHeroes(heroes);
        
        dao.addOrganization(organization);
        
        assertEquals(1, dao.getAllOrganizations().size()); 
    }

    /**
     * Test of getOrganizationsByHeroId method, of class SuperheroSightingsDao.
     */
    
    @Test
    public void testGetOrganizationsByHeroId() {
        
        Hero hero = new Hero();
        hero.setHeroName("Bobby");
        hero.setDescription("Super loud");

        dao.addHero(hero);
        
        //creating a location to add as the foreign key in the organization
        Location location = new Location(); 
        location.setLocationName("HQ");
        location.setDescription("world headquarters");
        location.setStreet("123 nunya");
        location.setCity("Gennessee");
        location.setState("CO");
        location.setZip(12345);
        location.setCountry("USA");
        location.setLatitude(12.123456);
        location.setLongitude(120.123456);
        
        dao.addLocation(location);
                
        Organization organization = new Organization(); 
        organization.setOrganizationName("Jackson 5");
        organization.setDescription("musical group");
        organization.setPhone("9099990000");
        organization.setEmail("five@jackson.com");
        organization.setLocationId(location.getLocationId());
        
        List<Hero> heroes = new ArrayList<>();
        heroes.add(hero);

        organization.setHeroes(heroes);
        
        dao.addOrganization(organization);
        
        List<Organization> organizationFromDao = dao.getOrganizationsByHeroId(hero.getHeroId());
        
        assertEquals(organizationFromDao.get(0), organization);
        
        //need to test if a hero belongs to multiple organizations
    }

    /**
     * Test of updateOrganization method, of class SuperheroSightingsDao.
     */

    @Test
    public void testUpdateOrganization() {
        
        //creating a new hero
        Hero hero = new Hero();
        hero.setHeroName("Bobby");
        hero.setDescription("Super loud");

        dao.addHero(hero);

        //creating a location to add as the foreign key in the sighting
        Location location = new Location();
        location.setLocationName("HQ");
        location.setDescription("world headquarters");
        location.setStreet("123 nunya");
        location.setCity("Gennessee");
        location.setState("CO");
        location.setZip(12345);
        location.setCountry("USA");
        location.setLatitude(12.123456);
        location.setLongitude(120.123456);

        dao.addLocation(location);
        
        //create a new organization
        Organization organization = new Organization(); 
        organization.setOrganizationName("Jackson 5");
        organization.setDescription("musical group");
        organization.setPhone("9099990000");
        organization.setEmail("five@jackson.com");
        organization.setLocationId(location.getLocationId());
        
        List<Hero> heroes = new ArrayList<>();
        heroes.add(hero);

        organization.setHeroes(heroes);
        
        dao.addOrganization(organization);
        
        //checking that there is one organization
        assertEquals(dao.getAllOrganizations().size(), 1); 
        
        //updating the description
        organization.setDescription("The Joe Jackson 5");
        
        dao.updateOrganization(organization);

        //getting the updated Organization back from the Dao
        Organization updatedOrganizationFromDao = 
                dao.getOrganizationById(organization.getOrganizationId());

        assertEquals(updatedOrganizationFromDao, organization);

    }

    /**
     * Test of deleteOrganization method, of class SuperheroSightingsDao.
     */
    @Test
    public void testDeleteOrganization() {
        
        //creating a new hero
        Hero hero = new Hero();
        hero.setHeroName("Bobby");
        hero.setDescription("Super loud");

        dao.addHero(hero);

        //creating a location to add as the foreign key in the sighting
        Location location = new Location();
        location.setLocationName("HQ");
        location.setDescription("world headquarters");
        location.setStreet("123 nunya");
        location.setCity("Gennessee");
        location.setState("CO");
        location.setZip(12345);
        location.setCountry("USA");
        location.setLatitude(12.123456);
        location.setLongitude(120.123456);

        dao.addLocation(location);
        
        //create a new organization
        Organization organization = new Organization(); 
        organization.setOrganizationName("Jackson 5");
        organization.setDescription("musical group");
        organization.setPhone("9099990000");
        organization.setEmail("five@jackson.com");
        organization.setLocationId(location.getLocationId());
        
        List<Hero> heroes = new ArrayList<>();
        heroes.add(hero);

        organization.setHeroes(heroes);
        
        dao.addOrganization(organization);
        
        //checking that there is one organization
        assertEquals(dao.getAllOrganizations().size(), 1); 
        
        dao.deleteOrganization(organization.getOrganizationId());
        
        //checking that getAllOrganizations now returns 0
        assertEquals(dao.getAllOrganizations().size(), 0);
    }
    
    
    /**
     * Test of addSighting method, of class SuperheroSightingsDao.
     */
    @Test
    public void testAddGetSighting() {

        Hero hero = new Hero();
        hero.setHeroName("Bobby");
        hero.setDescription("Super loud");

        dao.addHero(hero);

        //creating a location to add as the foreign key in the sighting
        Location location = new Location();
        location.setLocationName("HQ");
        location.setDescription("world headquarters");
        location.setStreet("123 nunya");
        location.setCity("Gennessee");
        location.setState("CO");
        location.setZip(12345);
        location.setCountry("USA");
        location.setLatitude(12.123456);
        location.setLongitude(120.123456);

        dao.addLocation(location);

        Sighting sighting = new Sighting();
        LocalDateTime ldt = LocalDateTime.now();
        sighting.setSightingDate(ldt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm")));
        sighting.setLocationId(location.getLocationId());

        List<Hero> heroes = new ArrayList<>();
        heroes.add(hero);

        sighting.setHeroes(heroes);

        dao.addSighting(sighting);

        Sighting sightingFromDao = dao.getSightingById(sighting.getSightingId());

        assertEquals(sighting, sightingFromDao);
    }

    /**
     * Test of getSightingById method, of class SuperheroSightingsDao.
     */
//    @Test
    public void testGetSightingById() {
        //tested in testAddGetSighting() above
    }

    /**
     * Test of getAllSightings method, of class SuperheroSightingsDao.
     */
    @Test
    public void testGetAllSightings() {

        Hero hero = new Hero();
        hero.setHeroName("Bobby");
        hero.setDescription("Super loud");

        dao.addHero(hero);

        //creating a location to add as the foreign key in the sighting
        Location location = new Location();
        location.setLocationName("HQ");
        location.setDescription("world headquarters");
        location.setStreet("123 nunya");
        location.setCity("Gennessee");
        location.setState("CO");
        location.setZip(12345);
        location.setCountry("USA");
        location.setLatitude(12.123456);
        location.setLongitude(120.123456);

        dao.addLocation(location);

        Sighting sighting = new Sighting();
        LocalDateTime ldt = LocalDateTime.now();
        sighting.setSightingDate(ldt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm")));
        sighting.setLocationId(location.getLocationId());

        List<Hero> heroes = new ArrayList<>();
        heroes.add(hero);

        sighting.setHeroes(heroes);

        dao.addSighting(sighting);

        assertEquals(1, dao.getAllSightings().size());
        
        dao.deleteSighting(sighting.getSightingId());
        
        //test for 0 sightings
        assertEquals(0, dao.getAllSightings().size());
        
        
        //need to test for multiple sightings
        
    }

    /**
     * Test of getSightingsByDate method, of class SuperheroSightingsDao.
     */
    @Test
    public void testGetSightingsByDate() {

        Hero hero = new Hero();
        hero.setHeroName("Bobby");
        hero.setDescription("Super loud");

        dao.addHero(hero);

        //creating a location to add as the foreign key in the sighting
        Location location = new Location();
        location.setLocationName("HQ");
        location.setDescription("world headquarters");
        location.setStreet("123 nunya");
        location.setCity("Gennessee");
        location.setState("CO");
        location.setZip(12345);
        location.setCountry("USA");
        location.setLatitude(12.123456);
        location.setLongitude(120.123456);

        dao.addLocation(location);

        Sighting sighting = new Sighting();
        LocalDateTime ldt = LocalDateTime.of(2018, 4, 11, 11, 23, 58);
        sighting.setSightingDate(ldt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm")));
        sighting.setLocationId(location.getLocationId());

        List<Hero> heroes = new ArrayList<>();
        heroes.add(hero);

        sighting.setHeroes(heroes);

        dao.addSighting(sighting);

        String searchDate = LocalDateTime.of(2018, 4, 11, 11, 23, 58).format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm"));

        List<Sighting> sightingFromDao = dao.getSightingsByDate(searchDate);

        assertEquals(sightingFromDao.get(0), sighting); 
    }

    /**
     * Test of getSightingsByHeroId method, of class SuperheroSightingsDao.
     */
    @Test
    public void testGetSightingsByHeroId() {
        Hero hero = new Hero();
        hero.setHeroName("Bobby");
        hero.setDescription("Super loud");

        dao.addHero(hero);

        //creating a location to add as the foreign key in the sighting
        Location location = new Location();
        location.setLocationName("HQ");
        location.setDescription("world headquarters");
        location.setStreet("123 nunya");
        location.setCity("Gennessee");
        location.setState("CO");
        location.setZip(12345);
        location.setCountry("USA");
        location.setLatitude(12.123456);
        location.setLongitude(120.123456);

        dao.addLocation(location);

        Sighting sighting = new Sighting();
        LocalDateTime ldt = LocalDateTime.now();
        sighting.setSightingDate(ldt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm")));
        sighting.setLocationId(location.getLocationId());

        List<Hero> heroes = new ArrayList<>();
        heroes.add(hero);

        sighting.setHeroes(heroes);

        dao.addSighting(sighting);

        List<Sighting> sightingList = dao.getSightingsByHeroId(hero.getHeroId());

        assertEquals(sightingList.get(0), sighting);

    }

    /**
     * Test of getSightingsByLocationId method, of class SuperheroSightingsDao.
     */
    @Test
    public void testGetSightingsByLocationId() {
        Hero hero = new Hero();
        hero.setHeroName("Bobby");
        hero.setDescription("Super loud");

        dao.addHero(hero);

        //creating a location to add as the foreign key in the sighting
        Location location = new Location();
        location.setLocationName("HQ");
        location.setDescription("world headquarters");
        location.setStreet("123 nunya");
        location.setCity("Gennessee");
        location.setState("CO");
        location.setZip(12345);
        location.setCountry("USA");
        location.setLatitude(12.123456);
        location.setLongitude(120.123456);

        dao.addLocation(location);

        Sighting sighting = new Sighting();
        LocalDateTime ldt = LocalDateTime.now();
        sighting.setSightingDate(ldt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm")));
        sighting.setLocationId(location.getLocationId());

        List<Hero> heroes = new ArrayList<>();
        heroes.add(hero);

        sighting.setHeroes(heroes);

        dao.addSighting(sighting);

        List<Sighting> sightingList = dao.getSightingsByLocationId(location.getLocationId());

        assertEquals(sightingList.get(0), sighting);

    }

    /**
     * Test of updateSighting method, of class SuperheroSightingsDao.
     */
    @Test
    public void testUpdateSighting() {
        
        //creating a new hero
        Hero hero = new Hero();
        hero.setHeroName("Bobby");
        hero.setDescription("Super loud");

        dao.addHero(hero);

        //creating a location to add as the foreign key in the sighting
        Location location = new Location();
        location.setLocationName("HQ");
        location.setDescription("world headquarters");
        location.setStreet("123 nunya");
        location.setCity("Gennessee");
        location.setState("CO");
        location.setZip(12345);
        location.setCountry("USA");
        location.setLatitude(12.123456);
        location.setLongitude(120.123456);

        dao.addLocation(location);

        Sighting sighting = new Sighting();
        LocalDateTime ldt = LocalDateTime.now();
        sighting.setSightingDate(ldt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm")));
        sighting.setLocationId(location.getLocationId());

        List<Hero> heroes = new ArrayList<>();
        heroes.add(hero);

        sighting.setHeroes(heroes);

        dao.addSighting(sighting);
        
        //creating a new date
        LocalDateTime updatedDate = LocalDateTime.of(2009, 5, 29, 12, 34, 56);
        
        sighting.setSightingDate(updatedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm")));

        dao.updateSighting(sighting);
        
        Sighting updatedSightingFromDao = dao.getSightingById(sighting.getSightingId());
        
        assertEquals(updatedSightingFromDao, sighting);
        
    }

    /**
     * Test of deleteSighting method, of class SuperheroSightingsDao.
     */
    @Test
    public void testDeleteSighting() {
        Hero hero = new Hero();
        hero.setHeroName("Bobby");
        hero.setDescription("Super loud");

        dao.addHero(hero);

        //creating a location to add as the foreign key in the sighting
        Location location = new Location();
        location.setLocationName("HQ");
        location.setDescription("world headquarters");
        location.setStreet("123 nunya");
        location.setCity("Gennessee");
        location.setState("CO");
        location.setZip(12345);
        location.setCountry("USA");
        location.setLatitude(12.123456);
        location.setLongitude(120.123456);

        dao.addLocation(location);

        Sighting sighting = new Sighting();
        LocalDateTime ldt = LocalDateTime.now();
        sighting.setSightingDate(ldt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm")));
        sighting.setLocationId(location.getLocationId());

        List<Hero> heroes = new ArrayList<>();
        heroes.add(hero);

        sighting.setHeroes(heroes);

        dao.addSighting(sighting);

        assertEquals(dao.getAllSightings().size(), 1);

        dao.deleteSighting(sighting.getSightingId());

        assertEquals(dao.getAllSightings().size(), 0);
    }

    /**
     * Test of addSuperpower method, of class SuperheroSightingsDao.
     */
    @Test
    public void testAddGetSuperpower() {
        
        Hero hero = new Hero();
        hero.setHeroName("Bobby");
        hero.setDescription("Super loud");

        dao.addHero(hero);
        
        //create a new Superpower
        Superpower superpower = new Superpower(); 
        superpower.setDescription("Hyperactive");
        
        List<Hero> heroes = new ArrayList<>();
        heroes.add(hero);
        
        superpower.setHeroes(heroes);
        
        dao.addSuperpower(superpower);
        
        //checking that there is one superpower
        assertEquals(dao.getAllSuperpowers().size(), 1); 
        
        Superpower superpowerFromDao 
                = dao.getSuperpowerById(superpower.getSuperpowerId()); 
        
        assertEquals(superpowerFromDao, superpower); 
    }

    /**
     * Test of getSuperpowerById method, of class SuperheroSightingsDao.
     */
//    @Test
    public void testGetSuperpowerById() {
        
        //tested in testAddGetSuperpower() above
    }

    /**
     * Test of getAllSuperpowers method, of class SuperheroSightingsDao.
     */
    @Test
    public void testGetAllSuperpowers() {
        
        Hero hero = new Hero();
        hero.setHeroName("Bobby");
        hero.setDescription("Super loud");

        dao.addHero(hero);
        
        //create a new Superpower
        Superpower superpower = new Superpower(); 
        superpower.setDescription("Hyperactive");
        
        List<Hero> heroes = new ArrayList<>();
        heroes.add(hero);
        
        superpower.setHeroes(heroes);
        
        dao.addSuperpower(superpower);
        
        //checking that there is one superpower
        assertEquals(dao.getAllSuperpowers().size(), 1); 
        
        List<Superpower> superpowerList = dao.getAllSuperpowers();
        
        assertEquals(superpowerList.get(0), superpower); 
    }
    
    
    /**
     * Test of updateSuperpower method, of class SuperheroSightingsDao.
     */
    @Test
    public void testUpdateSuperpower() {
        Hero hero = new Hero();
        hero.setHeroName("Bobby");
        hero.setDescription("Super loud");

        dao.addHero(hero);
        
        //create a new Superpower
        Superpower superpower = new Superpower(); 
        superpower.setDescription("Hyperactive");
        
        List<Hero> heroes = new ArrayList<>();
        heroes.add(hero);
        
        superpower.setHeroes(heroes);
        
        dao.addSuperpower(superpower);
        
        //checking that there is one superpower
        assertEquals(dao.getAllSuperpowers().size(), 1);
        
        superpower.setDescription("Slow motion");
        
        dao.updateSuperpower(superpower);
        
        Superpower superpowerFromDao 
                = dao.getSuperpowerById(superpower.getSuperpowerId());
        
        assertEquals(superpowerFromDao ,superpower);
        
    }

    /**
     * Test of deleteSuperpower method, of class SuperheroSightingsDao.
     */
    @Test
    public void testDeleteSuperpower() {
        Hero hero = new Hero();
        hero.setHeroName("Bobby");
        hero.setDescription("Super loud");

        dao.addHero(hero);
        
        //create a new Superpower
        Superpower superpower = new Superpower(); 
        superpower.setDescription("Hyperactive");
        
        List<Hero> heroes = new ArrayList<>();
        heroes.add(hero);
        
        superpower.setHeroes(heroes);
        
        dao.addSuperpower(superpower);
        
        //checking that there is one superpower
        assertEquals(dao.getAllSuperpowers().size(), 1); 
        
        dao.deleteSuperpower(superpower.getSuperpowerId());

        //checking that the one was deleted
        assertEquals(dao.getAllSuperpowers().size(), 0); 
        
    }
}
