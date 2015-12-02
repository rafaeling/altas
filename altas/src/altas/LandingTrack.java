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
    
    private int tam_garage;
    
    private int num_landing_track;
    
    public LandingTrack(int garage_size, int landingtrack_size)
    {
        this.tam_garage = garage_size;
        
        this.num_landing_track = landingtrack_size;
        
        garage = new int[garage_size][garage_size];
        
        landingtrack = new int[landingtrack_size];
        
        for(int i = 0 ; i < tam_garage ; i++)
        {
            for(int j = 0 ; j < tam_garage ; j++)
            {
                garage[i][j] = 0;
            }
        }
        
        for(int i = 0 ; i < num_landing_track ; i++)
        {
            landingtrack[i] = 0;
        }
    }
    
    public void Mostrar()
    {
        System.out.println("Garage");
        for(int i = 0 ; i < Get_Tam_Garage() ; i++)
        {
            for(int j = 0 ; j < Get_Tam_Garage() ; j++)
            {
                System.out.print(garage[i][j] + " ");
            }
            
            System.out.println();
        }
        
        System.out.println("Pistas");
        
        for(int i = 0 ; i < Get_Num_Landing_Track() ; i++)
        {
            System.out.println(landingtrack[i]);
        }
    }
    
    private int Get_Tam_Garage()
    {
        return tam_garage;
    }
    
    private int Get_Num_Landing_Track()
    {   
        return num_landing_track;
    }
    
    public int TestLandingTrackFree()
    {
        boolean free_landing_track = false;
        
        int i = 0;
        
        while(i < Get_Num_Landing_Track() && !free_landing_track)
        {
            
            if(landingtrack[i] == 0)
            {
                free_landing_track = true;
            }else
            {
                i++;
            }
            
        }
        
        if(free_landing_track)
        {
            return i;
        }
        else{
            return -1;
        }
        
        
    }
    
    public void add_plane(int n)
    {
        landingtrack[n] = 1;
    }
}