/**
 * 
 */
package clientetest;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nicolas
 */
public class ClienteTest {

    static final int puerto = 4000;
    static final String HOST = "localhost";
    static Socket clienteSocket;
    
    static void proceso()
    {
        try {
            
            DataOutputStream out = new DataOutputStream( clienteSocket.getOutputStream() );
            out.writeUTF("Hola");
            clienteSocket.close();
            
        } catch (IOException ex) {
            Logger.getLogger(ClienteTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public static void main(String[] args) 
    {
        try {
            clienteSocket  = new Socket(HOST, puerto);
            proceso();
        } catch (IOException ex) {
            Logger.getLogger(ClienteTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
