/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosightings.model;

import java.util.List;
import lombok.Data;

/**
 *
 * @author timpinkerton
 */

@Data
public class Organization {
    
    private int organizationId;
    private String organizationName;
    private String description; 
    private String phone;
    private String email; 
    private int locationId;
    private Location location; 
    private List<Hero> heroes;
}
