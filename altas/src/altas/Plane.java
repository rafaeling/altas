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
    
    
    boolean waiting_land_track = false;
    boolean wating_garage = false;
    boolean waitng_take_off_landing = false;
    boolean take_off = false;
    
    private int landing_track, landing_track_take_off;
    
    private Pair<Integer, Integer> garage_coord = new Pair(0,0);
		
    // Nombre del host donde se ejecuta el servidor:
    String host="localhost";
                
    // Puerto en el que espera el servidor:
    int port=8989;
                
    // Socket para la conexión TCP
    Socket socketServicio=null;
    
    
    String estado;
    
    public void run() {
        try {
            
            /*
            
                Esperando Aterrizaje
            
            */
            
            while(!waiting_land_track ){
                socketServicio =new Socket (host,port);
                
                
               // System.out.println("********************\n" + "   PIDO ATERRIZAR\n" + "********************");
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
                if (!(respuesta.equals("no_land"))){
                    
                    //System.out.println("Avion Puede Aterrizar");
                    
                    //GUARDO MI PISTA DE ATERRIZAJE
                    landing_track = Integer.parseInt(respuesta);
                    
                    //ENVIO ACEPTACION
                    PrintWriter land_accepted = new PrintWriter(socketServicio.getOutputStream(),true);

                    land_accepted.println(respuesta);
                    
                    land_accepted.flush();
                    
                    waiting_land_track  = true;
                    
                    estado = "Aterrizando";
                    //System.out.println(estado);
                    
                }else
                {
                    //System.out.println("No puede aterrizar");
                    
                    estado = "Esperando Pista";
                   // System.out.println(estado);
                }
                
                socketServicio.close(); 
            }
            
            
            /*
            
                Esperando Garage
            
            */
            
            //System.out.println("********************\n" + "   PIDO GARAGE\n" + "********************");
            
            
            
            while(!wating_garage)
            {
                /*
                    PIDO GARAGE
                */
                
                socketServicio =new Socket (host,port);
                PrintWriter outPrinter = new PrintWriter(socketServicio.getOutputStream(),true);
                outPrinter.println("garage");
                outPrinter.flush();
                
                /*
                    COMPRUEBO LA RESPUESTA
                */
                
                BufferedReader inReader = new BufferedReader(new InputStreamReader(socketServicio.getInputStream())); 
                respuesta = inReader.readLine();
                
                
                //SI ME DEJA APARCAR
                if (!(respuesta.equals("no_garage"))){
                    
                    //System.out.println("Puede Aparcar");
                    
                    //Guardo mi parking
                    
                    BufferedReader coor_x = new BufferedReader(new InputStreamReader(socketServicio.getInputStream())); 
                    String x = coor_x.readLine();
                    garage_coord.first = Integer.parseInt(x);
                    
                    BufferedReader coor_y = new BufferedReader(new InputStreamReader(socketServicio.getInputStream())); 
                    String y = coor_y.readLine();
                    garage_coord.second = Integer.parseInt(y);
                    
                    
                    wating_garage  = true;
                    
                    
                    // Envio que dejo pisa al servidor
                    PrintWriter pista = new PrintWriter(socketServicio.getOutputStream(),true);
                    pista.println(Integer.toString(landing_track));
                    pista.flush();
                    
                    estado = "Aparcando";
                    //System.out.println(estado);
                    
                }else
                {
                   // System.out.println("No Puede Aparcar");
                    
                    estado = "Esperando Aparcar";
                    
                    //System.out.println(estado);
                }
                socketServicio.close(); 
            }
            
            
            
            
            
            
            /*
            
                Esperando Pista Despegue
            
            */
            
            
            while(!waitng_take_off_landing)
            {
               
                
                socketServicio =new Socket (host,port);
                PrintWriter outPrinter = new PrintWriter(socketServicio.getOutputStream(),true);
                outPrinter.println("pista");
                outPrinter.flush();
                
                
                
                BufferedReader inReader = new BufferedReader(new InputStreamReader(socketServicio.getInputStream())); 
                respuesta = inReader.readLine();
                
                
                //SI HAY PISTA PARA DESPEGAR
                if (!(respuesta.equals("no_pista"))){
                    
                    //System.out.println("Avion Puede Aterrizar");
                    
                    landing_track_take_off = Integer.parseInt(respuesta);
                    
                    //ENVIO MI PISA
                    PrintWriter pista = new PrintWriter(socketServicio.getOutputStream(),true);

                    pista.println(respuesta);
                    
                    pista.flush();
                    
                    waitng_take_off_landing  = true;
                    
                    estado = "En pista";
                    //System.out.println(estado);
                    
                    //ENVIAR LA POSICION DE GARAGE QUE DEJO
                    
                    PrintWriter x = new PrintWriter(socketServicio.getOutputStream(),true);
                    outPrinter.println(Integer.toString(garage_coord.first));
                    outPrinter.flush();
                        
                    PrintWriter y = new PrintWriter(socketServicio.getOutputStream(),true);
                    outPrinter.println(Integer.toString(garage_coord.first));
                    outPrinter.flush();
                    
                }else
                {
                    //System.out.println("No puede aterrizar");
                    
                    estado = "Esperando Pista";
                   // System.out.println(estado);
                }
                
                socketServicio.close();  
            }
            
            
            
            
            /*
            
                Fuera del aeropuerto
            
            */
            
            
            
            
            
            
            while(!take_off ){
                socketServicio =new Socket (host,port);
                
                
               // System.out.println("********************\n" + "   PIDO ATERRIZAR\n" + "********************");
                /*
                    PIDO ATERRIZAR
                */
                
                PrintWriter outPrinter = new PrintWriter(socketServicio.getOutputStream(),true);
                outPrinter.println("despegue");
                outPrinter.flush();
                
                /*
                    COMPRUEBO LA RESPUESTA
                */
                
                BufferedReader inReader = new BufferedReader(new InputStreamReader(socketServicio.getInputStream())); 
                respuesta = inReader.readLine();
                
                
                //SI ME DEJA ATERRIZAR
                if ((respuesta.equals("a volar"))){
                    
                    //System.out.println("Avion Puede Aterrizar");
                    
                    
                    //ENVIO ACEPTACION
                    PrintWriter land_accepted = new PrintWriter(socketServicio.getOutputStream(),true);

                    land_accepted.println(Integer.toString(landing_track));
                    
                    land_accepted.flush();
                    
                    take_off  = true;
                    
                    estado = "Aterrizando";
                    //System.out.println(estado);
                    
                }else
                {
                    //System.out.println("No puede aterrizar");
                    
                    estado = "Esperando Pista";
                   // System.out.println(estado);
                }
                
                socketServicio.close(); 
            }
            
            
            
            
            
            
            
            
            
               socketServicio.close(); 
            
  
               
               
                // Excepciones:
        } catch (UnknownHostException e) {
                System.err.println("Error: Nombre de host no encontrado.");
            } catch (IOException ex) {
            Logger.getLogger(Plane.class.getName()).log(Level.SEVERE, null, ex);
        }
	}
    
}
