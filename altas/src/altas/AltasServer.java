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

                
                if(datosRecibidos.equals(land))
                {
                    System.out.println("Envio respuesta");
                    
                    // Compruebo si hay sitio para aterrizar;
                    
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
                        
                        airport.add_plane(n);
                        
                        airport.Mostrar();
                    }
                    
                    
                    
                    
                   
                }
                
                

            }while(true);
                
            } catch (IOException ex) {
                Logger.getLogger(AltasServer.class.getName()).log(Level.SEVERE, null, ex);
            }     
        }
    
    
}
