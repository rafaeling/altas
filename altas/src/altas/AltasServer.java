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
    
    int port=8989;
           
    // Servidor
    ServerSocket serverSocket;
            
    // Servicio
    Socket socketServicio;
    
    // stream de lectura (por aquí se recibe lo que envía el cliente)
    private BufferedReader inputStream;
        
    // stream de escritura (por aquí se envía los datos al cliente)
    private PrintWriter outputStream;
    
    String datosRecibidos="";
    
    public void run(){
                
        try {
                // Abrimos el socket en modo pasivo, escuchando el en puerto indicado por "port"
                serverSocket = new ServerSocket(port);
                
                // Mientras ... siempre!
            do {

                // Aceptamos una nueva conexión con accept()
                socketServicio = serverSocket.accept();
                
                //InputStream inputStream = socketServicio.getInputStream();
			inputStream = new BufferedReader(new InputStreamReader(socketServicio.getInputStream()));
                        
                        //OutputStream outputStream = socketServicio.getOutputStream();
                        outputStream = new PrintWriter(socketServicio.getOutputStream(),true);
			
			// Lee la frase a Yodaficar:
                        
                        datosRecibidos = inputStream.readLine();
                        
                        outputStream.println(datosRecibidos);

            }while(true);
                
            } catch (IOException ex) {
                Logger.getLogger(AltasServer.class.getName()).log(Level.SEVERE, null, ex);
            }     
        }
}
