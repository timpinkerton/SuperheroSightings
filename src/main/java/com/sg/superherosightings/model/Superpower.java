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
public class Superpower {
    
    private int superpowerId; 
    private String description; 
    private List<Hero> heroes;
    
}
