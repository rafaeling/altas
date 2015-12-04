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
public class Airport {
    
    public static void main(String[] args){
    
        AltasServer servidor= new AltasServer();
        
        servidor.start();
        
        Plane cliente1= new Plane();
        Plane cliente2= new Plane();
        Plane cliente3= new Plane();
        Plane cliente4= new Plane();
        Plane cliente5= new Plane();
        Plane cliente6= new Plane();
        Plane cliente7= new Plane();
        Plane cliente8= new Plane();
        Plane cliente9= new Plane();
        
        
        
        
        
        
        cliente1.start();
        cliente2.start();
        cliente3.start();
        cliente4.start();
        cliente5.start();
        cliente6.start();
        cliente7.start();
        cliente8.start();
        cliente9.start();

    
    }
    
}
