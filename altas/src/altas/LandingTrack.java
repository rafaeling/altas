/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package altas;


/**
 * 
 * @author rafaeling
 */
public class LandingTrack {
    
    // Garage
    private int[][] garage;
    
    // Pistas
    private int[] landingtrack;
    
    // Tamaño garage
    private int tam_garage;
    
    // Numero de pistas
    private int num_landing_track;
    
    /**
     * 
     * Constructor de la clase LandingTrack
     * 
     * @param garage_size
     * @param landingtrack_size 
     */
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
    
    
    
    /**
    *
    * @author rafaeling
    * 
    * Método que devuelve -1 si no hay ninguna pista libre o un número entero que es la pista libre
    * 
    */
    
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
    
    /**
    *
    * @author rafaeling
    * 
    * Método que cambia la pista de aterrizaje n con un 1, para marcarla como ocupada.
    * 
    */
    public void add_plane_to_landing_track(int n)
    {
        landingtrack[n] = 1;
    }
    
    /**
    *
    * @author rafaeling
    * 
    * Método que consulta si hay plazas libres en el garage.
    * Devuelve -1 si no hay plazas libres o un par de enteros
    * que son las coordendas del garage
    * 
    */
    public Pair<Integer, Integer> TestGarageFree()
    {
        boolean free_garage = false;
        
        Pair<Integer, Integer> coord = new Pair(0,0);
        
        for(int i = 0 ; i < this.Get_Tam_Garage() && !free_garage; i++)
        {
            for(int j = 0 ; j < this.Get_Tam_Garage() && !free_garage ; j++)
            {
                if(garage[i][j] == 0)
                {
                    free_garage = true;
                    
                    coord.first = i;
                    coord.second = j;
                    
                }
            }
        }
        
        if(free_garage)
        {
            return coord;
        }else
        {
            coord.first = -1;
            coord.second = -1;
            
            return coord;
        }
    }
    
    /**
    *
    * @author rafaeling
    * 
    * Método que cambia al plaza de garage (x,y) con un 1, para marcarla como ocupada.
    * 
    */
    public void add_plane_to_garage(Pair<Integer, Integer> n)
    {
        garage[n.first][n.second] = 1;
    }
    
    
    /**
     * @author rafaeling
     * 
     * Método que cambia la pista n con un 0, para marcarla como libre.
     * 
     * @param n
     * 
     */
    public void add_free_to_landing_track(int n)
    {
        landingtrack[n] = 0;
    }
   
    /**
     * @author rafaeling
     * 
     * Método que cambia la plaza n (x,y), como libre.
     * 
     * @param n 
     */
    public void add_free_to_garage(Pair<Integer, Integer> n)
    {
        garage[n.first][n.second] = 0;
    }
    
}