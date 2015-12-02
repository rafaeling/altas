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
    
    String land = "land";
    
    boolean land_plane = false;
		
    // Nombre del host donde se ejecuta el servidor:
    String host="localhost";
                
    // Puerto en el que espera el servidor:
    int port=8989;
                
    // Socket para la conexión TCP
    Socket socketServicio=null;
    
    int i = 0;
    
    public void run() {
        try {
            // Creamos un socket que se conecte a "host" y "port":

            while(!land_plane){
                
                socketServicio =new Socket (host,port);
                
                i++;
                
                //socketServicio =new Socket (host,port);
                
                // stream de escritura (por aquí se envía los datos al cliente)
                PrintWriter outPrinter = new PrintWriter(socketServicio.getOutputStream(),true);
                
    // stream de lectura (por aquí se recibe lo que envía el cliente)
    //BufferedReader inReader = new BufferedReader(new InputStreamReader(socketServicio.getInputStream()));  
                

                // Enviamos peticion de aterrizar

                outPrinter.println("land");

                //System.out.println("Numero: " + i);
                
                // Aunque le indiquemos a TCP que queremos enviar varios arrays de bytes, sólo
                // los enviará efectivamente cuando considere que tiene suficientes datos que enviar...
                // Podemos usar "flush()" para obligar a TCP a que no espere para hacer el envío:

                outPrinter.flush();

                // Leemos la respuesta del servidor. Para ello le pasamos un array de bytes, que intentará
                // rellenar. El método "read(...)" devolverá el número de bytes leídos.

                //buferRecepcion = inReader.readLine();

                // Hay que reservar memoria para almacenar lo leído
                // Intenta leer tantos bytes como posiciones tiene el
                // array de bytes, aunque es posible que no haya
                // tantos datos, y sólo se lean bytesLeidos:

                //System.out.println("Recibido: ");
                //System.out.print(buferRecepcion);
                
                BufferedReader inReader = new BufferedReader(new InputStreamReader(socketServicio.getInputStream())); 
                
                respuesta = inReader.readLine();
                
                if (respuesta.equals(land)){
                    
                    System.out.println("Puede Aterrizar");
                    
                    PrintWriter land_accepted = new PrintWriter(socketServicio.getOutputStream(),true);

                    outPrinter.println("land_accepted");
                    
                    outPrinter.flush();
                    
                    land_plane = true;
                    
                }
                
                if(land_plane)
                {
                    BufferedReader inputStream = new BufferedReader(new InputStreamReader(socketServicio.getInputStream()));
                 
                    String accept = inputStream.readLine();
                    
                    // aqui decido aleatoriamente si puedo o no
                    
                    PrintWriter land_accepted = new PrintWriter(socketServicio.getOutputStream(),true);

                    outPrinter.println(accept);
                    
                    outPrinter.flush();
                }

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
