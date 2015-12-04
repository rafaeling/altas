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
    
    // Booleanos del estado del cliente
    boolean waiting_land_track = false;
    boolean wating_garage = false;
    boolean waitng_take_off_landing = false;
    boolean take_off = false;
    
    // Guardan la pista de aterrizaje y despegue
    private int landing_track, landing_track_take_off;
    
    // Guardan las coordenadas en las que se guarda el avion
    private Pair<Integer, Integer> garage_coord = new Pair(0,0);
		
    // Nombre del host donde se ejecuta el servidor:
    String host="localhost";
                
    // Puerto en el que espera el servidor:
    int port=8989;
                
    // Socket para la conexión TCP
    Socket socketServicio=null;
    
    // Estado del avión
    String estado;
    
    
    
    public void run() {
        try {
            
          
            /****************************
             *   Esperando Aterrizaje   *
             ****************************/
            
            while(!waiting_land_track ){
                
                // Conectamos con servicio
                socketServicio =new Socket (host,port);
                
                // Envio la petición de aterrizar
                PrintWriter outPrinter = new PrintWriter(socketServicio.getOutputStream(),true);
                outPrinter.println("land");
                outPrinter.flush();
                
                // COMPRUEBO LA RESPUESTA
                BufferedReader inReader = new BufferedReader(new InputStreamReader(socketServicio.getInputStream())); 
                respuesta = inReader.readLine();
                
                
                //SI ME DEJA ATERRIZAR
                if (!(respuesta.equals("no_land"))){
                                       
                    //GUARDO MI PISTA DE ATERRIZAJE
                    landing_track = Integer.parseInt(respuesta);
                    
                    //ENVIO ACEPTACION
                    PrintWriter land_accepted = new PrintWriter(socketServicio.getOutputStream(),true); 
                    land_accepted.println(respuesta); 
                    land_accepted.flush();
                    
                    // Cambiamos el estado del avion a aterrizado
                    waiting_land_track  = true;
                    
                    estado = "Aterrizado";
                    //System.out.println(estado);
                    
                }else
                {
                    //System.out.println("No puede aterrizar");
                    
                    estado = "Esperando Pista";
                    
                    //System.out.println(estado);
                }
                
                socketServicio.close(); 
            }
            
            
            
            /************************
             *   Esperando Garage   *
             ************************/
            
            while(!wating_garage)
            {
                // Conectamos con servicio
                socketServicio =new Socket (host,port);
                
                // Envio la petición de aterrizar
                PrintWriter outPrinter = new PrintWriter(socketServicio.getOutputStream(),true);
                outPrinter.println("garage");
                outPrinter.flush();
                
                // COMPRUEBO LA RESPUESTA
                BufferedReader inReader = new BufferedReader(new InputStreamReader(socketServicio.getInputStream())); 
                respuesta = inReader.readLine();
                
                
                //SI ME DEJA APARCAR
                if (!(respuesta.equals("no_garage"))){
                    
                    // Guardo las coordenadas de mi garage
                    
                    // Recibo la coordenada x
                    BufferedReader coor_x = new BufferedReader(new InputStreamReader(socketServicio.getInputStream())); 
                    String x = coor_x.readLine();
                    garage_coord.first = Integer.parseInt(x);
                    
                    // Recibo la coordenada y
                    BufferedReader coor_y = new BufferedReader(new InputStreamReader(socketServicio.getInputStream())); 
                    String y = coor_y.readLine();
                    garage_coord.second = Integer.parseInt(y);
                    
                    
                    // Envio que dejo la pisa al servidor
                    PrintWriter pista = new PrintWriter(socketServicio.getOutputStream(),true);
                    pista.println(Integer.toString(landing_track));
                    pista.flush();
                    
                    // Cambiamos el estado del avion a aparcado
                    wating_garage  = true;
                    
                    estado = "Aparcando";
                    //System.out.println(estado);
                    
                }else
                {
                    //System.out.println("No Puede Aparcar");
                    
                    estado = "Esperando Aparcar";
                    
                    //System.out.println(estado);
                }
                
                socketServicio.close(); 
                
            }
            
            
            
            
            
            /***********************************
             *   Esperando Pista de Despegue   *
             ***********************************/
            
            while(!waitng_take_off_landing)
            {
                // Conectamos con servicio
                socketServicio =new Socket (host,port);
                
                // Envio la petición de aterrizar
                PrintWriter outPrinter = new PrintWriter(socketServicio.getOutputStream(),true);
                outPrinter.println("pista");
                outPrinter.flush();
                
                
                // COMPRUEBO LA RESPUESTA
                BufferedReader inReader = new BufferedReader(new InputStreamReader(socketServicio.getInputStream())); 
                respuesta = inReader.readLine();
                
                
                //SI HAY PISTA PARA DESPEGAR
                if (!(respuesta.equals("no_pista"))){
                    
                    //GUARDO MI PISTA DE ATERRIZAJE
                    landing_track_take_off = Integer.parseInt(respuesta);
                    
                    //ENVIO ACEPTACION
                    PrintWriter pista = new PrintWriter(socketServicio.getOutputStream(),true);
                    pista.println(respuesta);
                    pista.flush();
                    
                    
                    //ENVIAR LA POSICION DE GARAGE QUE DEJO
                    
                    
                    // Envio la coordenada x
                    PrintWriter x = new PrintWriter(socketServicio.getOutputStream(),true);
                    x.println(Integer.toString(garage_coord.first));
                    outPrinter.flush();
                                        
                    // Envio la coordenada y
                    PrintWriter y = new PrintWriter(socketServicio.getOutputStream(),true);
                    y.println(Integer.toString(garage_coord.first));
                    outPrinter.flush();
                    
                    
                    
                    // Cambiamos el estado del avion a esperando pista
                    waitng_take_off_landing  = true;
                    
                    estado = "En pista";
                    
                    //System.out.println(estado);
                    
                    
                }else
                {
                    //System.out.println("No puede ir a pista");
                    
                    estado = "Esperando Pista";
                   // System.out.println(estado);
                }
                
                socketServicio.close();  
            }
            
            
            
            
            /******************
             *   Despegando   *
             ******************/
            
            while(!take_off){
                
                // Conectamos con servicio
                socketServicio =new Socket (host,port);
                
                
               // Envio la petición de despegar
                PrintWriter outPrinter = new PrintWriter(socketServicio.getOutputStream(),true);
                outPrinter.println("despegue");
                outPrinter.flush();
                
                // COMPRUEBO LA RESPUESTA
                BufferedReader inReader = new BufferedReader(new InputStreamReader(socketServicio.getInputStream())); 
                respuesta = inReader.readLine();
                
                
                //SI ME DEJA DESPEGAR
                if ((respuesta.equals("take_off"))){
                    
                    //ENVIO ACEPTACION DIRECTAMENTE
                    PrintWriter land_accepted = new PrintWriter(socketServicio.getOutputStream(),true);
                    land_accepted.println(Integer.toString(landing_track));
                    land_accepted.flush();
                    
                    // Cambiamos el estado del avion a depegando
                    take_off  = true;
                    
                    estado = "Aterrizando";
                    //System.out.println(estado);
                    
                }else
                {
                    //System.out.println("No puede despegar");
                    
                    estado = "No Despegando";
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
