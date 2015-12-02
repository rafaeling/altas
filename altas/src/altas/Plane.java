/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package altas;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rafaeling
 */
public class Plane extends Thread{
    
    
    // Para guardar la lectura
    String respuesta="";  
    
    String no_land = "no_land";
    
    boolean land_plane = false;
		
    // Nombre del host donde se ejecuta el servidor:
    String host="localhost";
                
    // Puerto en el que espera el servidor:
    int port=8989;
                
    // Socket para la conexión TCP
    Socket socketServicio=null;
    
    int i = 0;
    
    String estado;
    
    public void run() {
        try {
            
            while(!land_plane){
                
                socketServicio =new Socket (host,port);
                
                
                /*
                    PIDO ATERRIZAR
                */
                
                PrintWriter outPrinter = new PrintWriter(socketServicio.getOutputStream(),true);
                outPrinter.println("land");
                outPrinter.flush();
                
                /*
                    COMPRUEBO LA RESPUESTA
                */
                
                BufferedReader inReader = new BufferedReader(new InputStreamReader(socketServicio.getInputStream())); 
                respuesta = inReader.readLine();
                
                
                //SI ME DEJA ATERRIZAR
                if (!(respuesta.equals(no_land))){
                    
                    System.out.println("Puede Aterrizar");
                    
                    
                    //ENVIO ACEPTACION
                    PrintWriter land_accepted = new PrintWriter(socketServicio.getOutputStream(),true);

                    land_accepted.println(respuesta);
                    
                    land_accepted.flush();
                    
                    land_plane = true;
                    
                    estado = "aterrizando";
                    
                }else
                {
                    System.out.println("No puede aterrizar");
                    
                    estado = "esperando_pista";
                }
                

            }
            
            
            /*
            
                Esperando Garage
            
            */
            
            
            /*
            
                Esperando Despege
            
            */
            
            /*
            
                Fuera del aeropuerto
            
            */
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
               socketServicio.close(); 
            
  
               
               
                // Excepciones:
        } catch (UnknownHostException e) {
                System.err.println("Error: Nombre de host no encontrado.");
            } catch (IOException ex) {
            Logger.getLogger(Plane.class.getName()).log(Level.SEVERE, null, ex);
        }
	}
    
}
