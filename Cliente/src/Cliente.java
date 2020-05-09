
import java.io.*;
import java.net.*;
import java.applet.*;
import java.awt.*;

public class Cliente extends Applet {

    static final String HOST = "localhost";
    static final int Puerto = 5000;
    Socket skCliente;

    class Client 
    {
        Client() {}

        void proceso() 
        {
            try 
            {
                          /* Envía mensaje */
                OutputStream aux = skCliente.getOutputStream();
                DataOutputStream hilo1 = new DataOutputStream(aux);
                hilo1.writeUTF("Hola desde cliente");
                
                          /* Recibe mensaje */
                InputStream aux2 = skCliente.getInputStream();
                DataInputStream hilo2 = new DataInputStream( aux2 );
                System.out.println("El mensaje del servidor es: " + hilo2.readUTF());
                
                skCliente.close();
            } catch (Exception e) { System.out.println("Error en la lectura."); }
        }
    }

    public void paint(Graphics g) 
    {
        try 
        {
            skCliente = new Socket(HOST, Puerto);
            g.drawString("Se Activo el cliente", 50, 50);
        } catch (Exception e) { g.drawString("No se activo el cliente", 60, 60); }
        
        Client c = new Client();
        c.proceso();
    }
}
