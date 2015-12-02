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
 */
public class AltasServer extends Thread{

    private LandingTrack airport;
    
    String land = "land";
    String garage = "garage";
    
    int port=8989;
           
    // Servidor
    ServerSocket serverSocket;
            
    // Servicio
    Socket socketServicio;
    
    // stream de escritura (por aquí se envía los datos al cliente)
    private PrintWriter outputStream;
    
    String datosRecibidos="";
    
    public void run(){
                
        try {
                serverSocket = new ServerSocket(port);
                
                airport = new LandingTrack(3,3);
                
            do {
                
                
                
                socketServicio = serverSocket.accept();
                
		BufferedReader inputStream = new BufferedReader(new InputStreamReader(socketServicio.getInputStream()));
                datosRecibidos = inputStream.readLine();

                
                // Compruebo si hay sitio para aterrizar;
                if(datosRecibidos.equals(land))
                {
                    //System.out.println("Servidor envia respuesta");
                    
                    
                    
                    PrintWriter outPrinter = new PrintWriter(socketServicio.getOutputStream(),true);
                    
                    int place = airport.TestLandingTrackFree();
                    
                    if( place == -1)
                    {
                        outPrinter.println("no_land");
                        outPrinter.flush();
                        
                    }else
                    {
                        
                        
                        outPrinter.println(Integer.toString(place));
                        outPrinter.flush();
                        
                        
                        //socketServicio = serverSocket.accept();
                        //Compruebo si el avion quiere aterrizar
                        BufferedReader add = new BufferedReader(new InputStreamReader(socketServicio.getInputStream()));

                        String num = add.readLine();
                        
                        int n = Integer.parseInt(num);
                        
                        airport.add_plane_to_landing_track(n);
                        
                        //airport.Mostrar();
                    }
                
                }else if(datosRecibidos.equals(garage))
                {
                    //System.out.println("Envio respuesta");
                    
                    PrintWriter outPrinter = new PrintWriter(socketServicio.getOutputStream(),true);
                    
                    Pair<Integer,Integer> place = airport.TestGarageFree();
                    
                    if( place.first == -1)
                    {
                        outPrinter.println("no_garage");
                        outPrinter.flush();
                        
                    }else
                    {
                                             
                        outPrinter.println("garage");
                        outPrinter.flush();
                        
                        airport.add_plane_to_garage(place);
                        
                        PrintWriter x = new PrintWriter(socketServicio.getOutputStream(),true);
                        outPrinter.println(Integer.toString(place.first));
                        outPrinter.flush();
                        
                        PrintWriter y = new PrintWriter(socketServicio.getOutputStream(),true);
                        outPrinter.println(Integer.toString(place.first));
                        outPrinter.flush();
                        
                        
                        
                        BufferedReader delete = new BufferedReader(new InputStreamReader(socketServicio.getInputStream()));

                        String num = delete.readLine();
                        
                        int n = Integer.parseInt(num);
                        
                        airport.add_free_to_landing_track(n);
                        //airport.Mostrar();
                        
                    }
                }else if(datosRecibidos.equals("pista"))
                {
                    //System.out.println("Servidor envia respuesta");
                    
                    
                    
                    PrintWriter outPrinter = new PrintWriter(socketServicio.getOutputStream(),true);
                    
                    int place = airport.TestLandingTrackFree();
                    
                    if( place == -1)
                    {
                        outPrinter.println("no_pista");
                        outPrinter.flush();
                        
                    }else
                    {
                        
                        
                        outPrinter.println(Integer.toString(place));
                        outPrinter.flush();
                        
                        
                        airport.add_plane_to_landing_track(place);
                    
                        Pair<Integer, Integer> coor = new Pair(0,0);
                        
                        BufferedReader coor_x = new BufferedReader(new InputStreamReader(socketServicio.getInputStream())); 
                        String x = coor_x.readLine();
                        coor.first = Integer.parseInt(x);
                    
                        BufferedReader coor_y = new BufferedReader(new InputStreamReader(socketServicio.getInputStream())); 
                        String y = coor_y.readLine();
                        coor.second = Integer.parseInt(y);
                        
                        airport.add_free_to_garage(coor);
                        
                        
                    }
                
                }if(datosRecibidos.equals("despegue"))
                {
                    //System.out.println("Servidor envia respuesta");
                    
                    
                    
                    PrintWriter outPrinter = new PrintWriter(socketServicio.getOutputStream(),true);
                    
                    outPrinter.println("a volar");
                    outPrinter.flush();
                        
                    BufferedReader add = new BufferedReader(new InputStreamReader(socketServicio.getInputStream()));

                    String num = add.readLine();
                        
                    int n = Integer.parseInt(num);
                        
                    airport.add_free_to_landing_track(n);
                    
                    airport.Mostrar();
                }
                
                

            }while(true);
                
            } catch (IOException ex) {
                Logger.getLogger(AltasServer.class.getName()).log(Level.SEVERE, null, ex);
            }     
        }
    
    
}
