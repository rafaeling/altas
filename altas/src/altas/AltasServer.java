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
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rafaeling
 * 
 */
public class AltasServer extends Thread{

    // Airport class with landing tracks and garage
    private LandingTrack airport;
    
    // Port
    int port=8989;
           
    // Servidor
    ServerSocket serverSocket;
            
    // Servicio
    Socket socketServicio;
    
    
    public void run(){
                
        try {
                // Inicializamos el servidor
                serverSocket = new ServerSocket(port);
                
                // Inicializamos el aeropuerto
                airport = new LandingTrack(3,3);
                
            do {
                
                // Habilitamos la entreda de mensajes
                socketServicio = serverSocket.accept();
                
                // Comenzamos con el primer mensaje que nos llega del cliente
		BufferedReader inputStream = new BufferedReader(new InputStreamReader(socketServicio.getInputStream()));
                String datosRecibidos = inputStream.readLine();

                
                /****************
                *   ATERRIZAR   *
                *****************/
                
                // Comprobamos si el cliente quiere aterrizar
                if(datosRecibidos.equals("land"))
                {
                    // Creamos el mensaje de respuesta
                    PrintWriter outland = new PrintWriter(socketServicio.getOutputStream(),true);
                    
                    // Llamamos a la Funcion TestLandingTrackFree() que comprueba si hay pistas libres
                    int place = airport.TestLandingTrackFree();
                    
                    if( place == -1) // Si no hay pista libres
                    {
                        // Enviamos la respuesta de no_land
                        outland.println("no_land");
                        outland.flush();
                        
                    }else
                    {
                        // Si hay pista enviamos el entero recibido anteriormente
                        outland.println(Integer.toString(place));
                        outland.flush();
                        
                        
                        // Espero un nuevo mensaje del cliente con la pista enviada para comprobar que acepta
                        // la pista y se dispone a aterrizar
                        
                        // Capto el mensaje de llegada
                        BufferedReader add = new BufferedReader(new InputStreamReader(socketServicio.getInputStream()));

                        String num = add.readLine();
                        
                        // Parseamos la pista a entero
                        int n = Integer.parseInt(num);
                        
                        // Añadimos el avion a la pista n.
                        airport.add_plane_to_landing_track(n);
                        
                        //DESCOMENTAR SI SE QUIERE VER LAS SOLICITUDES DE ATERRIZAJE
                        // Y COMENTAR LOS OTROS airport.Mostrar();
                        //airport.Mostrar();
                    }
                
                    
                /**************
                *   APARCAR   *
                ***************/
                
                }else if(datosRecibidos.equals("garage"))
                {   
                    // Creamos el mensaje de respuesta
                    PrintWriter inPrinter = new PrintWriter(socketServicio.getOutputStream(),true);
                    
                    // Llamamos a la Funcion TestGarageFree() que comprueba si hay plaza libres en el garage
                    Pair<Integer,Integer> place = airport.TestGarageFree();
                    
                    if( place.first == -1) // Si no hay pista libres
                    { 
                    
                        // Enviamos la respuesta de no_garage
                        inPrinter.println("no_garage");
                        inPrinter.flush();
                        
                    }else
                    {
                        // Si hay plazas enviamos el mensaje garage
                        inPrinter.println("garage");
                        inPrinter.flush();
                        
                        // Añadimos la nueva plaza al garage
                        airport.add_plane_to_garage(place);
                        
                        // Enviamos las coordenada x del garage
                        PrintWriter x = new PrintWriter(socketServicio.getOutputStream(),true);
                        x.println(Integer.toString(place.first));
                        x.flush();
                        
                        // Enviamos la coordenada y del garaje
                        PrintWriter y = new PrintWriter(socketServicio.getOutputStream(),true);
                        y.println(Integer.toString(place.first));
                        y.flush();
                        
                        
                        // Esperamos a que el cliente nos envie la pista en la que se encuentra para marcarla como libre
                        BufferedReader delete = new BufferedReader(new InputStreamReader(socketServicio.getInputStream()));
                        String num = delete.readLine();
                        int n = Integer.parseInt(num);
                        
                        // Cambiamos el estado de la pista n a libre
                        airport.add_free_to_landing_track(n);
                        
                        //DESCOMENTAR SI SE QUIERE VER LAS SOLICITUDES 
                        // DE GARAJES Y COMENTAR LOS OTROS airport.Mostrar();
                        //airport.Mostrar();
                        
                    }
                    
                    
                    
                /*************************
                *   BUSCAR PISTA LIBRE   *
                **************************/
                    
                }else if(datosRecibidos.equals("pista"))
                {
                    // Creamos el mensaje de respuesta
                    PrintWriter outPrinter = new PrintWriter(socketServicio.getOutputStream(),true);

                    
                    // Llamamos a la Funcion TestLandingTrackFree() que comprueba si hay pistas libres
                    int place = airport.TestLandingTrackFree();
                    
                    if( place == -1) // Si no hay pista libres
                    {
                         // Enviamos la respuesta de no_pista
                        outPrinter.println("no_pista");
                        outPrinter.flush();
                        
                    }else
                    {
                        
                        // Si hay pista enviamos el entero recibido anteriormente
                        outPrinter.println(Integer.toString(place));
                        outPrinter.flush();
                        
                        // Añadimos el avion a la pista n.
                        airport.add_plane_to_landing_track(place);
                    
                        
                        
                        // Esperamos a que el cliente nos envie las coordenadas del garage en el que se encuentra para marcarla como libre
                        Pair<Integer, Integer> coor = new Pair(0,0);
                        
                        // Esperamos a que el cliente nos envie la coordenada x del garage
                        BufferedReader coor_x = new BufferedReader(new InputStreamReader(socketServicio.getInputStream())); 
                        String x = coor_x.readLine();
                        coor.first = Integer.parseInt(x);
                    
                        // Esperamos a que el cliente nos envie la coordenada y del garage
                        BufferedReader coor_y = new BufferedReader(new InputStreamReader(socketServicio.getInputStream())); 
                        String y = coor_y.readLine();
                        coor.second = Integer.parseInt(y);
                        
                        // Cambiamos el estado de la plaza del garage coor a libre
                        airport.add_free_to_garage(coor);
                        
                        //DESCOMENTAR SI SE QUIERE VER LAS ENTRADAS EN PISTA DE LOS 
                        //AVIONES Y COMENTAR LOS OTROS airport.Mostrar();
                        airport.Mostrar();
                        
                    }
                
                
                    
                /***************
                *   DESPEGUE   *
                ****************/
                    
                }if(datosRecibidos.equals("despegue"))
                {
                    // Creamos el mensaje de respuesta no necesita comprobar nada
                    PrintWriter outPrinter = new PrintWriter(socketServicio.getOutputStream(),true);
                    outPrinter.println("take_off");
                    outPrinter.flush();
                    
                    // Espero un nuevo mensaje del cliente con la pista enviada para comprobar que acepta
                    // la pista y se dispone a aterrizar
                        
                    // Capto el mensaje de llegada                    
                    BufferedReader add = new BufferedReader(new InputStreamReader(socketServicio.getInputStream()));
                    String num = add.readLine();
                    int n = Integer.parseInt(num);
                    
                    // Cambiamos el estado de la pista n a libre
                    airport.add_free_to_landing_track(n);
                    
                    
                    //DESCOMENTAR SI SE QUIERE VER LOS DESPEGUES Y COMENTAR LOS OTROS airport.Mostrar();
                    //airport.Mostrar();
    
                }

            }while(true);
                
            } catch (IOException ex) {
                Logger.getLogger(AltasServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        }
    
}
