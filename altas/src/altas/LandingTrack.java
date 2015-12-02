/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package altas;

/**
 *
 * @author rafae
 */
public class LandingTrack {
    
    private int[][] garage;
    
    private int[] landingtrack;
    
    public LandingTrack(int garage_size, int landingtrack_size)
    {
        garage = new int[garage_size][garage_size];
        
        landingtrack = new int[landingtrack_size];
    }
    
    
}
