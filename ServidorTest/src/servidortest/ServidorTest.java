/**
 * Espera solicitudes del proyecto Java ClienteBancario de la clase Cliente.java
 * realizando una conexión con la BBDD en MySQL para mostrar o modigicar 
 * los valores.
 */
package servidortest;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nicolas
 */
public class ServidorTest {

    static final int puerto = 4000;
    static ServerSocket serverSocket;
    
    static void enviar()
    {
        try {
            Socket clienteSocket = serverSocket.accept();
            DataInputStream in = new DataInputStream ( clienteSocket.getInputStream() );
            System.out.println(in.readUTF());
            clienteSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(ServidorTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public static void main(String[] args) 
    {
        try {
            serverSocket = new ServerSocket(puerto);
            enviar();
        } catch (Exception ex) {
            
        }
    }
    
}
