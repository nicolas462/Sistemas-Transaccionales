/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.applet.Applet;
import java.awt.Graphics;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author BOG-A306-E-017
 */
public class Servidor extends Applet {

    final static int Puerto = 5000;
    ServerSocket skServidor;
        
    public void init() {}
    
    class Server
    {
        public Server (Graphics g)
        {
            g.drawString("Escucha el puerto: " + Puerto, 50, 50);
        }
        
        public void enviar() 
        {
            try
            {
                          /* Recibe mensaje */
                Socket skCliente = skServidor.accept();
                InputStream aux1 = skCliente.getInputStream();
                DataInputStream hilo1 = new DataInputStream( aux1 );
                System.out.println("El mensaje del cliente es: " + hilo1.readUTF());
                
                          /* Envía mensaje */
                OutputStream aux2 = skCliente.getOutputStream();
                DataOutputStream hilo2 = new DataOutputStream( aux2 );
                hilo2.writeUTF("Hola desde servidor");
               
                skCliente.close();
            } catch (Exception e) {}
        }
    
    }
    
    public void paint(Graphics g)
    {
        try { skServidor = new ServerSocket(Puerto); } catch (Exception e) {}
        Server s = new Server(g);
        s.enviar();
    }
}
