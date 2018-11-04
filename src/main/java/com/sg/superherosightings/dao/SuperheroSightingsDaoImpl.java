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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author timpinkerton
 */
public class SuperheroSightingsDaoImpl implements SuperheroSightingsDao {

    private JdbcTemplate jdbcTemplate;

    //setter injection for JDBC Template
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //Prepared Statements for Hero *********************************************
    //Create
    private static final String SQL_INSERT_HERO
            = "insert into Hero (heroName, description) values (?, ?)";

    //Read one
    private static final String SQL_SELECT_HERO_BY_ID
            = "select * from Hero where heroId = ?";

    //Read all
    private static final String SQL_SELECT_ALL_HEROES
            = "select * from Hero";

    //report all of the Heroes sighted at a particular location
    private static final String SQL_SELECT_HERO_BY_SIGHTING_ID
            = "select h.heroId, h.heroName, h.description "
            + "from Hero h "
            + "join Hero_Sighting hs on h.heroId = hs.heroId "
            + "where sightingId = ?";

    //report all of the superheroes sighted at a particular location
    private static final String SQL_SELECT_ALL_HEROES_BY_LOCATION_ID
            = "select h.* "
            + "from Hero h "
            + "join Hero_Sighting hs on hs.heroId = h.heroId "
            + "join Sighting s on s.sightingId = hs.sightingId "
            + "join Location l on l.locationId = s.locationId "
            + "where l.locationId = ?";

    //report all of the Heroes with a particular Superpower
    private static final String SQL_SELECT_HEROES_BY_SUPERPOWER_ID
            = "select h.heroId, h.heroName, h.description "
            + "from Hero h join Hero_Superpower sp on h.heroId = sp.heroId "
            + "where sp.superpowerId = ?";

    //report all of the members of a particular organization
    private static final String SQL_SELECT_ALL_HEROES_BY_ORGANZATION_ID
            = "select h.* "
            + "from Hero_Organization ho "
            + "join Hero h on h.heroId = ho.heroId "
            + "where ho.organizationId = ?";

    //Update
    private static final String SQL_UPDATE_HERO
            = "update Hero set heroName = ?, description = ? where heroId = ?";

    //Delete
    private static final String SQL_DELETE_HERO
            = "delete from Hero where heroId = ?";

    //Prepared Statements for Location *****************************************
    //Create
    private static final String SQL_INSERT_LOCATION
            = "insert into Location (locationName, description, street, city, "
            + "state, zip, country, latitude, longitude) values (?, ?, ?, ?, ?,"
            + "?, ?, ?, ?)";

    //Read one
    private static final String SQL_SELECT_LOCATION_BY_ID
            = "select * from Location where locationId = ?";

    //Read all
    private static final String SQL_SELECT_ALL_LOCATIONS
            = "select * from Location";

    //report all of the locations where a particular superhero has been seen
    private static final String SQL_SELECT_ALL_LOCATIONS_BY_HERO_ID
            = "select * "
            + "from Location l "
            + "join Sighting s on s.locationId = l.locationId "
            + "join Hero_Sighting hs on hs.sightingId = s.sightingId "
            + "where heroId = ?";

    private static final String SQL_SELECT_LOCATION_BY_SIGHTING_ID
            = "select * "
            + "from Location l "
            + "join Sighting s on s.locationId = l.locationId "
            + "where s.sightingId = ?";

    //Update
    private static final String SQL_UPDATE_LOCATION
            = "update Location set locationName = ?, description = ?, "
            + "street = ?, city = ?, state = ?, zip = ?, country = ?, "
            + "latitude = ?, longitude = ? "
            + "where locationId = ?";

    //Delete
    private static final String SQL_DELETE_LOCATION
            = "delete from Location where locationId = ?";

    //Prepared Statements for Organization *************************************
    //Create
    private static final String SQL_INSERT_ORGANIZATION
            = "insert into Organization (organizationName, description, phone, "
            + "email, locationId) "
            + "values (?, ?, ?, ?, ?)";

    //Read one
    private static final String SQL_SELECT_ORGANIZATION_BY_ID
            = "select * from Organization where organizationId = ?";

    //SQL_SELECT_ORGANIZATIONS_BY_LOCATION_ID
    private static final String SQL_SELECT_ORGANIZATIONS_BY_LOCATION_ID
            = "select * from Organization where locationId = ?";

    //Read all
    private static final String SQL_SELECT_ALL_ORGANIZATIONS
            = "select * from Organization";

    //report all of the organizations a particular superhero/villain belongs to.
    private static final String SQL_SELECT_ORGANIZATIONS_BY_HERO_ID
            = "select o.* "
            + "from Organization o "
            + "join Hero_Organization ho on ho.organizationId = o.organizationId "
            + "where ho.heroId = ?";

    private static final String SQL_SELECT_LOCATION_BY_ORGANIZATION_ID
            = "select l.* "
            + "from Organization o "
            + "join Location l on l.locationId = o.locationId "
            + "where organizationId = ?";

    //Update
    private static final String SQL_UPDATE_ORGANIZATION
            = "update Organization set organizationName = ?, description = ?, "
            + "phone = ?, email = ?, locationId = ? where organizationId = ?";

    //Delete
    private static final String SQL_DELETE_ORGANIZATION
            = "delete from organization where organizationId = ?";

    private static final String SQL_DELETE_ORGANIZATION_BY_LOCATION_ID
            = "delete from organization where locationId = ?";

    //Prepared Statements for Sighting *****************************************
    //Create
    private static final String SQL_INSERT_SIGHTING
            = "insert into Sighting (sightingDate, locationId) values (?, ?)";

    //Read one
    private static final String SQL_SELECT_SIGHTING_BY_ID
            = "select * from Sighting where sightingId = ?";

    //Read all
    private static final String SQL_SELECT_ALL_SIGHTINGS
            = "select * from Sighting";

    //report all sightings (hero and location) for a particular date
    private static final String SQL_SELECT_SIGHTINGS_BY_DATE
            = "select h.heroName, l.*, s.* "
            + "from Sighting s "
            + "join Location l on l.locationid = s.locationId "
            + "join Hero_Sighting hs on hs.sightingId = s.sightingId "
            + "join Hero h on h.heroId = hs.heroId "
            + "where s.sightingDate = ?";

    private static final String SQL_SELECT_SIGHTINGS_BY_LOCATION_ID
            = "select * from Sighting where locationId = ?";

    //Reading 10 most recent sightings
    private static final String SQL_SELECT_TEN_RECENT_SIGHTINGS
            = "select * from Sighting "
            + "order by sightingDate desc "
            + "limit 10";

    //Update
    private static final String SQL_UPDATE_SIGHTING
            = "update Sighting set sightingDate = ?, locationId = ? "
            + "where sightingId = ?";

    //Delete
    private static final String SQL_DELETE_SIGHTING
            = "delete from Sighting where sightingId = ?";

    private static final String SQL_DELETE_SIGHTING_BY_LOCATION_ID
            = "delete from Sighting where locationId = ?";

    //Prepared Statements for Hero_Sighting ************************************
    private static final String SQL_INSERT_HERO_SIGHTING
            = "insert into Hero_Sighting (heroId, sightingId) values (?, ?)";

    private static final String SQL_DELETE_HERO_SIGHTING_BY_SIGHTING_ID
            = "delete from Hero_Sighting where sightingId = ?";

    private static final String SQL_DELETE_HERO_SIGHTING_BY_HERO_ID
            = "delete from Hero_Sighting where heroId = ?";

    //unused
    private static final String SQL_SELECT_HERO_SIGHTING_HERO_ID_BY_SIGHTING_ID
            = "select heroId from Hero_Sighting where sightingId = ?";

    private static final String SQL_SELECT_SIGHTING_BY_HERO_ID
            = "select s.sightingId, s.sightingDate, s.locationId "
            + "from Sighting s "
            + "join Hero_Sighting hs on s.sightingId = hs.sightingId "
            + "where hs.heroId = ?";

    //Prepared Statements for Superpower ***************************************
    //Create
    private static final String SQL_INSERT_SUPERPOWER
            = "insert into Superpower (description) values (?)";

    //Read one
    private static final String SQL_SELECT_SUPERPOWER_BY_ID
            = "select * from Superpower where superpowerId = ?";

    //Read all
    private static final String SQL_SELECT_ALL_SUPERPOWERS
            = "select * from Superpower";

    private static final String SQL_SELECT_SUPERPOWER_BY_HERO_ID
            = "select s.superpowerId, s.description "
            + "from Hero h "
            + "join Hero_Superpower hs on hs.heroId = h.heroId "
            + "join Superpower s on s.superpowerId = hs.superpowerId "
            + "where h.heroId = ?";

    //Update
    private static final String SQL_UPDATE_SUPERPOWER
            = "update Superpower set description = ? where superpowerId = ?";

    //Delete
    private static final String SQL_DELETE_SUPERPOWER
            = "delete from Superpower where superpowerId = ?";

    //Prepared Statements for Hero_Superpower **********************************
    private static final String SQL_INSERT_HERO_SUPERPOWER
            = "insert into Hero_Superpower (heroId, superpowerId) values (?, ?)";

    private static final String SQL_DELETE_HERO_SUPERPOWER_BY_SUPERPOWER_ID
            = "delete from Hero_Superpower where superpowerId = ?";

    private static final String SQL_DELETE_HERO_SUPERPOWER_BY_HERO_ID
            = "delete from Hero_Superpower where heroId = ?";

    //unused
    private static final String SQL_SELECT_HERO_SUPERPOWER_HERO_ID_BY_SUPERPOWER_ID
            = "select heroId from Hero_Superpower where superpowerId = ?";

    //unused
//    private static final String SQL_SELECT_SUPERPOWER_BY_HERO_ID
//            = "select s.superpowerId, s.description "
//            + "from Superpower s "
//            + "join Hero_Superpower hs on s.superpowerId = hs.superpowerId"
//            + "where heroId = ?";
    //Prepared Statements for Hero_Organization ********************************
    private static final String SQL_INSERT_HERO_ORGANIZATION
            = "insert into Hero_Organization (heroId, organizationId) values (?, ?)";

    private static final String SQL_DELETE_HERO_ORGANIZATION_BY_ORGANIZATION_ID
            // ****  should where be organizationId or heroId?? ****
            = "delete from Hero_Organization where organizationId = ?";

    //unused
    private static final String SQL_SELECT_HERO_ORGANIZATION_HERO_ID_BY_ORGANIZATION_ID
            = "select heroId from Hero_Organization where organizationId = ?";

    private static final String SQL_DELETE_HERO_ORGANIZATION_BY_HERO_ID
            = "delete from Hero_Organization where heroId = ?";

    //unused
    private static final String SQL_SELECT_ORGANIZATION_BY_HERO_ID
            = "select o.organizationId, o.organizationName. o.description, "
            + "o.phone, o.email, o.locationId "
            + "from Organization o "
            + "join Hero_Organization ho on o.organizationId = ho.organizationId"
            + "where heroId = ?";

    //END of Prepared Statements 
    //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
    //HeroMapper ***************************************************************
    //maps a row from the Hero table into an Hero DTO
    private static final class HeroMapper implements RowMapper<Hero> {

        @Override
        public Hero mapRow(ResultSet rs, int i) throws SQLException {

            Hero h = new Hero();
            h.setHeroId(rs.getInt("heroId"));
            h.setHeroName(rs.getString("heroName"));
            h.setDescription(rs.getString("description"));

            return h;
        }
    }

    //Hero interface implementations *******************************************
    //Create
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void addHero(Hero hero) {
        jdbcTemplate.update(SQL_INSERT_HERO,
                hero.getHeroName(),
                hero.getDescription());

        int heroId
                = jdbcTemplate.queryForObject("select LAST_INSERT_ID()",
                        Integer.class);

        hero.setHeroId(heroId);
    }

    //Read one
    @Override
    public Hero getHeroById(int heroId) {
        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_HERO_BY_ID,
                    new HeroMapper(),
                    heroId);

        } catch (EmptyResultDataAccessException ex) {
            System.err.println("Tried getting hero by Id, but "
                    + heroId + " does not exist");
            return null;
        }
    }

    //Read all
    @Override
    public List<Hero> getAllHeroes() {

        try {
            return jdbcTemplate.query(SQL_SELECT_ALL_HEROES,
                    new HeroMapper());
        } catch (EmptyResultDataAccessException ex) {
            System.err.println("Tried getting a list of heroes, but "
                    + "none exist");
            return null;
        }

    }

    @Override
    public List<Hero> getHeroesByLocationId(int locationId) {
        try {
            List<Hero> heroList = jdbcTemplate.query(SQL_SELECT_ALL_HEROES_BY_LOCATION_ID,
                    new HeroMapper(),
                    locationId);

            return heroList;
        } catch (EmptyResultDataAccessException ex) {
            System.err.println("Tried getting a list of heroes by location, "
                    + "but none exist");
            return null;
        }

    }

    //Update
    @Override
    public void updateHero(Hero hero) {
        jdbcTemplate.update(SQL_UPDATE_HERO,
                hero.getHeroName(),
                hero.getDescription(),
                hero.getHeroId());
    }

    //Delete
    @Override
    public void deleteHero(int heroId) {

        //delete from Hero_Sighting
        jdbcTemplate.update(SQL_DELETE_HERO_SIGHTING_BY_HERO_ID, heroId);

        //delete from Hero_Superpower
        jdbcTemplate.update(SQL_DELETE_HERO_SUPERPOWER_BY_HERO_ID, heroId);

        //delete from Hero_Organization
        jdbcTemplate.update(SQL_DELETE_HERO_ORGANIZATION_BY_HERO_ID, heroId);

        jdbcTemplate.update(SQL_DELETE_HERO, heroId);
    }

    @Override
    public List<Hero> getHeroesByOrganization(Organization organization) {

        try {
            List<Hero> heroList = jdbcTemplate.query(SQL_SELECT_ALL_HEROES_BY_ORGANZATION_ID,
                    new HeroMapper(),
                    organization.getOrganizationId());

            return heroList;
        } catch (EmptyResultDataAccessException ex) {
            System.err.println("Tried getting a list of heroes by organization, "
                    + "but none exist");
            return null;
        }
    }

    //LocationMapper ***********************************************************
    private static final class LocationMapper implements RowMapper<Location> {

        @Override
        public Location mapRow(ResultSet rs, int i) throws SQLException {

            Location l = new Location();
            l.setLocationId(rs.getInt("locationId"));
            l.setLocationName(rs.getString("locationName"));
            l.setDescription(rs.getString("description"));
            l.setStreet(rs.getString("street"));
            l.setCity(rs.getString("city"));
            l.setState(rs.getString("state"));
            l.setZip(rs.getInt("zip"));
            l.setCountry(rs.getString("country"));
            l.setLatitude(rs.getDouble("latitude"));
            l.setLongitude(rs.getDouble("longitude"));

            return l;
        }
    }

    //Location interface implementations ***************************************
    //Create
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void addLocation(Location location) {
        jdbcTemplate.update(SQL_INSERT_LOCATION,
                location.getLocationName(),
                location.getDescription(),
                location.getStreet(),
                location.getCity(),
                location.getState(),
                location.getZip(),
                location.getCountry(),
                location.getLatitude(),
                location.getLongitude());

        int locationId
                = jdbcTemplate.queryForObject("select LAST_INSERT_ID()",
                        Integer.class);

        location.setLocationId(locationId);
    }

    //Read one
    @Override
    public Location getLocationById(int locationId) {
        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_LOCATION_BY_ID,
                    new LocationMapper(),
                    locationId);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    //Read all
    @Override
    public List<Location> getAllLocations() {

        try {
            return jdbcTemplate.query(SQL_SELECT_ALL_LOCATIONS,
                    new LocationMapper());
        } catch (EmptyResultDataAccessException ex) {
            System.err.println("Tried getting all locations, but "
                    + "none not exist");
            return null;
        }
    }

    //report all of the locations where a particular superhero has been seen
    @Override
    public List<Location> getLocationsByHeroId(int heroId) {

        try {
            List<Location> locationList
                    = jdbcTemplate.query(SQL_SELECT_ALL_LOCATIONS_BY_HERO_ID,
                            new LocationMapper(),
                            heroId);

            return locationList;
        } catch (EmptyResultDataAccessException ex) {
            System.err.println("Tried getting all locations by heroId "
                    + heroId + ", but none not exist");
            return null;
        }

    }

    //Update
    @Override
    public void updateLocation(Location location) {
        jdbcTemplate.update(SQL_UPDATE_LOCATION,
                location.getLocationName(),
                location.getDescription(),
                location.getStreet(),
                location.getCity(),
                location.getState(),
                location.getZip(),
                location.getCountry(),
                location.getLatitude(),
                location.getLongitude(),
                location.getLocationId());
    }

    //Delete
    @Override
    public void deleteLocation(int locationId) {

        List<Sighting> sightingList = jdbcTemplate.
                query(SQL_SELECT_SIGHTINGS_BY_LOCATION_ID, new SightingMapper(),
                        locationId);

        //deleting Hero_Sighting relationship
        for (Sighting currentSighting : sightingList) {
            jdbcTemplate.update(SQL_DELETE_HERO_SIGHTING_BY_SIGHTING_ID, currentSighting.getSightingId());
        }

        //delete location from Sighting
        jdbcTemplate.update(SQL_DELETE_SIGHTING_BY_LOCATION_ID, locationId);

        //need to add prepared statment to select organization by locationid, 
        //  then delete that organization from Hero_Organization
        List<Organization> organizationList = jdbcTemplate.
                query(SQL_SELECT_ORGANIZATIONS_BY_LOCATION_ID, new OrganizationMapper(),
                        locationId);

        for (Organization currentOrganization : organizationList) {
            jdbcTemplate.update(SQL_DELETE_HERO_ORGANIZATION_BY_ORGANIZATION_ID, currentOrganization.getOrganizationId());
        }

        //delete from Organiztation
        jdbcTemplate.update(SQL_DELETE_ORGANIZATION_BY_LOCATION_ID, locationId);

        jdbcTemplate.update(SQL_DELETE_LOCATION, locationId);
    }

    //OrganizationMapper ***********************************************************
    private static final class OrganizationMapper implements RowMapper<Organization> {

        @Override
        public Organization mapRow(ResultSet rs, int i) throws SQLException {
            Organization o = new Organization();
            o.setOrganizationId(rs.getInt("organizationId"));
            o.setOrganizationName(rs.getString("organizationName"));
            o.setDescription(rs.getString("description"));
            o.setPhone(rs.getString("phone"));
            o.setEmail(rs.getString("email"));
            o.setLocationId(rs.getInt("locationId"));

            return o;
        }
    }

    //Organization interface implementations ***********************************
    //Create
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void addOrganization(Organization organization) {
        jdbcTemplate.update(SQL_INSERT_ORGANIZATION,
                organization.getOrganizationName(),
                organization.getDescription(),
                organization.getPhone(),
                organization.getEmail(),
                organization.getLocationId());

        organization.setOrganizationId(jdbcTemplate.
                queryForObject("select LAST_INSERT_ID()", Integer.class));

        //getting the location for this organization
        organization.setLocation(findLocationForOrganization(organization));

        //updating the Hero_Organization table
        insertHeroOrganization(organization);
    }

    //Read one
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Organization getOrganizationById(int organizationId) {
        try {
            Organization organization = jdbcTemplate.queryForObject(SQL_SELECT_ORGANIZATION_BY_ID,
                    new OrganizationMapper(),
                    organizationId);

            //getting the Heroes for this organization
            organization.setHeroes(findHeroesForOrganization(organization));

            //get the location for this organization
            organization.setLocation(findLocationForOrganization(organization));

            return organization;

        } catch (EmptyResultDataAccessException ex) {
            System.err.println("Tried getting organization by Id, but "
                    + organizationId + " does not exist");
            return null;
        }
    }

    //Read all
    @Override
    public List<Organization> getAllOrganizations() {

        try {
            return jdbcTemplate.query(SQL_SELECT_ALL_ORGANIZATIONS,
                    new OrganizationMapper());
        } catch (EmptyResultDataAccessException ex) {
            System.err.println("Tried getting all organizations, but "
                    + "none exist");
            return null;
        }

    }

    //report all of the organizations a particular superhero/villain belongs to.
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public List<Organization> getOrganizationsByHeroId(int heroId) {

        try {
            List<Organization> organizationList
                    = jdbcTemplate.query(SQL_SELECT_ORGANIZATIONS_BY_HERO_ID,
                            new OrganizationMapper(),
                            heroId);

            return associateLocationAndHeroesWithOrganizations(organizationList);
        } catch (EmptyResultDataAccessException ex) {
            System.err.println("Tried getting all organizations by heroId: "
                    + heroId + ", but none exist");
            return null;
        }

    }

    //Update
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void updateOrganization(Organization organization) {
        jdbcTemplate.update(SQL_UPDATE_ORGANIZATION,
                organization.getOrganizationName(),
                organization.getDescription(),
                organization.getPhone(),
                organization.getEmail(),
                organization.getLocationId(),
                organization.getOrganizationId());

        //delete the Hero_Organization relationship, then resetting
        jdbcTemplate.update(SQL_DELETE_HERO_ORGANIZATION_BY_ORGANIZATION_ID,
                organization.getOrganizationId());

        insertHeroOrganization(organization);
    }

    //Delete
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void deleteOrganization(int organizationId) {

        //first delete Hero_Organization relationship for this organization
        jdbcTemplate.update(SQL_DELETE_HERO_ORGANIZATION_BY_ORGANIZATION_ID, organizationId);

        //delete the organization
        jdbcTemplate.update(SQL_DELETE_ORGANIZATION, organizationId);
    }

    //HELPERMETHODS for Organization ******************************************
    //creates an entry in Hero_Organization, associating the Organization with 
    //  all of the heroes.
    private void insertHeroOrganization(Organization organization) {
        final int organizationId = organization.getOrganizationId();
        final List<Hero> heroes = organization.getHeroes();

        if (heroes == null) {
            //do nothing 
        } else {
            //updating bridge table w/ entry for each hero with organization
            for (Hero currentHero : heroes) {
                jdbcTemplate.update(SQL_INSERT_HERO_ORGANIZATION,
                        currentHero.getHeroId(),
                        organizationId);
            }
        }

    }

    private List<Hero> findHeroesForOrganization(Organization organization) {

//        try {
//           List<Hero> heroesList =
        return jdbcTemplate.query(SQL_SELECT_ALL_HEROES_BY_ORGANZATION_ID,
                new HeroMapper(),
                organization.getOrganizationId());

//           return  //associate Superpower w/ heroes w/ (heroesList) as param
//        } catch (EmptyResultDataAccessException ex) {
//            System.err.println("Tried getting heroes for an organization, but "
//                    + "none not exist");
//            return null;
//        }
    }

    private Location findLocationForOrganization(Organization organization) {

        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_LOCATION_BY_ORGANIZATION_ID,
                    new LocationMapper(),
                    organization.getOrganizationId());
        } catch (EmptyResultDataAccessException ex) {
            System.err.println("Tried getting location for organziation "
                    + ", but none exist");
            return null;
        }

    }

    private List<Organization>
            associateLocationAndHeroesWithOrganizations(List<Organization> organizationList) {

        //set Hero ids for each organization
        for (Organization currentOrganization : organizationList) {
            // adding heroes to current organization
            currentOrganization.setHeroes(findHeroesForOrganization(currentOrganization));
            // adding the Location to current organization
            currentOrganization.setLocation(
                    findLocationForOrganization(currentOrganization));
        }
        return organizationList;
    }

    //SightingMapper ***********************************************************
    private static final class SightingMapper implements RowMapper<Sighting> {

        @Override
        public Sighting mapRow(ResultSet rs, int i) throws SQLException {
            Sighting s = new Sighting();
            s.setSightingId(rs.getInt("sightingId"));
            s.setSightingDate(rs.getTimestamp("sightingDate").
                    //converting the SQL timestamp from the database into a LocalDate
                    toLocalDateTime().
                    format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm")));
//                    format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")));
            s.setLocationId(rs.getInt("locationId"));
            return s;
        }

    }

    //Sighting interface implementations ***************************************
    //Create
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void addSighting(Sighting sighting) {

        jdbcTemplate.update(SQL_INSERT_SIGHTING,
                sighting.getSightingDate(),
                sighting.getLocationId());

        sighting.setSightingId(jdbcTemplate.
                queryForObject("select LAST_INSERT_ID()", Integer.class));

        //get the location for this sighting
        sighting.setLocation(findLocationForSighting(sighting));

        //to update the Hero_Sighting table
        insertHeroSighting(sighting);
    }

    //Read one
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Sighting getSightingById(int sightingId) {
        try {
            Sighting sighting = jdbcTemplate.queryForObject(SQL_SELECT_SIGHTING_BY_ID,
                    new SightingMapper(),
                    sightingId);

            //getting all Heroes for this sighting and setting the list on the 
            //  sighting
            sighting.setHeroes(findHeroesForSighting(sighting));

            //get the location for this sighting
            sighting.setLocation(findLocationForSighting(sighting));

            return sighting;

        } catch (EmptyResultDataAccessException ex) {
            System.err.println("Tried getting sighting by Id, but "
                    + sightingId + " does not exist");
            return null;
        }
    }

    //Read all
    @Override
    public List<Sighting> getAllSightings() {

        try {
            return this.jdbcTemplate.query(SQL_SELECT_ALL_SIGHTINGS,
                    new SightingMapper());

        } catch (EmptyResultDataAccessException ex) {
            System.err.println("Tried getting all sightings, but "
                    + "none exist");
            return null;
        }
    }

    //Read 10 most recent sightins
    @Override
    public List<Sighting> getRecentSightings() {

        try {
            return this.jdbcTemplate.query(SQL_SELECT_TEN_RECENT_SIGHTINGS,
                    new SightingMapper());

        } catch (EmptyResultDataAccessException ex) {
            System.err.println("Tried getting recent sightings, but "
                    + "none exist");
            return null;
        }
    }

    //report all sightings (hero and location) for a particular date
    @Override
    public List<Sighting> getSightingsByDate(String searchDate) {
        List<Sighting> sightingList = jdbcTemplate.
                query(SQL_SELECT_SIGHTINGS_BY_DATE,
                        new SightingMapper(),
                        searchDate);

        associateLocationAndHeroesWithSighting(sightingList);

        return sightingList;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public List<Sighting> getSightingsByHeroId(int heroId) {

        try {
            List<Sighting> sightingList = jdbcTemplate.
                    query(SQL_SELECT_SIGHTING_BY_HERO_ID,
                            new SightingMapper(),
                            heroId);

            return associateLocationAndHeroesWithSighting(sightingList);
        } catch (EmptyResultDataAccessException ex) {
            System.err.println("Tried getting sightings by heroId: "
                    + heroId + "but none exist");
            return null;
        }

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public List<Sighting> getSightingsByLocationId(int locationId) {

        try {
            List<Sighting> sightingList = jdbcTemplate.
                    query(SQL_SELECT_SIGHTINGS_BY_LOCATION_ID, new SightingMapper(),
                            locationId);

            return associateLocationAndHeroesWithSighting(sightingList);
        } catch (EmptyResultDataAccessException ex) {
            System.err.println("Tried getting sightings by locationId: "
                    + locationId + "but none exist");
            return null;
        }

    }

    //Update
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void updateSighting(Sighting sighting) {
        //update the Sighting table
        jdbcTemplate.update(SQL_UPDATE_SIGHTING,
                sighting.getSightingDate(),
                sighting.getLocationId(),
                sighting.getSightingId());

        //deleting Hero_Sighting relationship, then resetting
        jdbcTemplate.update(SQL_DELETE_HERO_SIGHTING_BY_SIGHTING_ID, sighting.getSightingId());

        insertHeroSighting(sighting);
    }

    //Delete
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void deleteSighting(int sightingId) {

        //first delete from the bridge table
        jdbcTemplate.update(SQL_DELETE_HERO_SIGHTING_BY_SIGHTING_ID, sightingId);

        //then delete from the Sighting table
        jdbcTemplate.update(SQL_DELETE_SIGHTING, sightingId);
    }

    //HELPERMETHODS for Sighting **********************************************
    //creates an entry in Hero_Sighting, associating the given Sighting with 
    //  all of the heroes.
    private void insertHeroSighting(Sighting sighting) {
        final int sightingId = sighting.getSightingId();
        final List<Hero> heroes = sighting.getHeroes();

        //updating bridge table w/ entry for each hero at specific sighting
        for (Hero currentHero : heroes) {
            jdbcTemplate.update(SQL_INSERT_HERO_SIGHTING,
                    currentHero.getHeroId(),
                    sightingId);
        }
    }

    //finds all Heroes for a Sighting by joining the Hero and Hero_Sighting tables
    private List<Hero> findHeroesForSighting(Sighting sighting) {

        try {
            return jdbcTemplate.query(SQL_SELECT_HERO_BY_SIGHTING_ID,
                    new HeroMapper(),
                    sighting.getSightingId());
        } catch (EmptyResultDataAccessException ex) {
            System.err.println("Tried getting heroes for sightingId: "
                    + sighting.getSightingId() + " but none exist");
            return null;
        }

    }

    //finds Location for a Sighting by joing Location and Sighting tables
    private Location findLocationForSighting(Sighting sighting) {

        try {
            return jdbcTemplate.queryForObject(SQL_SELECT_LOCATION_BY_SIGHTING_ID,
                    new LocationMapper(),
                    sighting.getSightingId());
        } catch (EmptyResultDataAccessException ex) {
            System.err.println("Tried getting location for sightingId: "
                    + sighting.getSightingId() + " but none exist");
            return null;
        }

    }

    //uses the 2 methods above to connect Hero and Location to each Sighting
    private List<Sighting>
            associateLocationAndHeroesWithSighting(List<Sighting> sightingList) {

        //set the list of heroId's for each sighting
        for (Sighting currentSighting : sightingList) {
            //adding Heroes to the current sighting
            currentSighting.setHeroes(findHeroesForSighting(currentSighting));

            //adding the location to the currentSighting
            currentSighting.setLocation(findLocationForSighting(currentSighting));
        }

        return sightingList;
    }

    //SuperpowerMapper ***********************************************************
    private static final class SuperpowerMapper implements RowMapper<Superpower> {

        @Override
        public Superpower mapRow(ResultSet rs, int i) throws SQLException {

            Superpower s = new Superpower();
            s.setSuperpowerId(rs.getInt("superpowerId"));
            s.setDescription(rs.getString("description"));

            return s;
        }
    }

    //Superpower interface implementations *************************************
    //Create
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void addSuperpower(Superpower superpower) {
        jdbcTemplate.update(SQL_INSERT_SUPERPOWER,
                superpower.getDescription());

        superpower.setSuperpowerId(jdbcTemplate.
                queryForObject("select LAST_INSERT_ID()", Integer.class));

        //to update the Hero_Superpower table
        insertHeroSuperpower(superpower);
    }

    //Read one
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Superpower getSuperpowerById(int superpowerId) {
        try {
            Superpower superpower = jdbcTemplate.queryForObject(SQL_SELECT_SUPERPOWER_BY_ID,
                    new SuperpowerMapper(),
                    superpowerId);

            //getting all Heroes with this superpower and setting the list on the 
            //  superpower
            superpower.setHeroes(findHeroesWithSuperpower(superpower));

            return superpower;

        } catch (EmptyResultDataAccessException ex) {
            System.err.println("Tried getting superpower by superpowerId: "
                    + superpowerId + " but it does not exist");
            return null;
        }
    }

    //Read all
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public List<Superpower> getAllSuperpowers() {

        try {
            List<Superpower> superpowerList = jdbcTemplate.query(SQL_SELECT_ALL_SUPERPOWERS,
                    new SuperpowerMapper());

            return associateHeroesWithSuperpower(superpowerList);
        } catch (EmptyResultDataAccessException ex) {
            System.err.println("Tried getting all superpowers, "
                    + "but none exist");
            return null;
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public List<Superpower> getSuperpowersByHeroId(int heroId) {

        try {
            List<Superpower> superpowerList = jdbcTemplate.
                    query(SQL_SELECT_SUPERPOWER_BY_HERO_ID,
                            new SuperpowerMapper(),
                            heroId);
            return superpowerList;
        } catch (EmptyResultDataAccessException ex) {
            System.err.println("Tried getting a list of superpowers, "
                    + "but none exist");
            return null;
        }

    }

    //Update
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void updateSuperpower(Superpower superpower) {
        jdbcTemplate.update(SQL_UPDATE_SUPERPOWER,
                superpower.getDescription(),
                superpower.getSuperpowerId());

        //deleting the Hero_Superpower relationship
        jdbcTemplate.update(SQL_DELETE_HERO_SUPERPOWER_BY_SUPERPOWER_ID,
                superpower.getSuperpowerId());

        insertHeroSuperpower(superpower);
    }

    //Delete
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void deleteSuperpower(int superpowerId) {
        //deleting from the Hero_Superpower bridge table
        jdbcTemplate.update(SQL_DELETE_HERO_SUPERPOWER_BY_SUPERPOWER_ID,
                superpowerId);

        //deleting from the Superpower table
        jdbcTemplate.update(SQL_DELETE_SUPERPOWER, superpowerId);
    }

    //HELPERMETHODS for Superpower ********************************************
    //creates an entry in Hero_Superpower, associating the given Superpower with 
    //  all of the heroes.
    private void insertHeroSuperpower(Superpower superpower) {
        final int superpowerId = superpower.getSuperpowerId();
        final List<Hero> heroes = superpower.getHeroes();

        if (heroes == null) {
            //do nothing
        } else {
            //updating bridge table w/ entry for each hero with superpower
            for (Hero currentHero : heroes) {
                jdbcTemplate.update(SQL_INSERT_HERO_SUPERPOWER,
                        currentHero.getHeroId(),
                        superpowerId);
            }
        }

    }

    //finds all Heroes with a Superpower by joining the Hero and Hero_Superpower tables
    private List<Hero> findHeroesWithSuperpower(Superpower superpower) {

        try {
            return jdbcTemplate.query(SQL_SELECT_HEROES_BY_SUPERPOWER_ID,
                    new HeroMapper(),
                    superpower.getSuperpowerId());
        } catch (EmptyResultDataAccessException ex) {
            System.err.println("Tried getting heroes with superpower: "
                    + superpower.getSuperpowerId() + " but none exist");
            return null;
        }

    }

    //uses method above to connect Hero and Superpower to each Sighting
    private List<Superpower>
            associateHeroesWithSuperpower(List<Superpower> superpowerList) {

        //set the list of heroId's for each Superpower
        for (Superpower currentSuperpower : superpowerList) {
            //adding Heroes to the current Superpower
            currentSuperpower.setHeroes(findHeroesWithSuperpower(currentSuperpower));
        }

        return superpowerList;
    }

}
