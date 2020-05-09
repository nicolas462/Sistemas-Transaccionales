/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.applet.Applet;
import java.awt.Button;
import java.awt.Event;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.TextField;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 *
 * @author Nicolas
 */
public class Cliente extends Applet {

    Cliente1 c;
    Socket skCliente;
    final int PUERTO = 5100;
    final String HOST = "localhost";
    Label l1 = new Label("Su saldo es: $000");
    Button b1 = new Button("Consultar saldo");
    TextField t1 = new TextField();
    Button b2 = new Button("Actualizar saldo");
    
    String saldo= "";
    
    public void init()
    {
        add(b1);
        add(l1);
        add(t1);
        add(b2);
    }
    
    class Cliente1
    {
        Cliente1() {}
        
        /**
         * Recibe información del servidor acerca de la consulta en la BBDD
         */
        public void proceso()
        {
            try
            {
                skCliente = new Socket(HOST, PUERTO);

                DataInputStream datos = new DataInputStream ( skCliente.getInputStream() );
                saldo = datos.readUTF();

                l1.setText(saldo); //Muestra en la Applet el saldo
                skCliente.close();
            } catch (Exception ex) {}
        }
        
        /**
         * Recibe dato del Texfield t1 y lo envía al servidor para actualizar
         * la BBDD
         */
        public void update()
        {
            try
            {
                skCliente = new Socket(HOST, PUERTO);

                OutputStream aux = skCliente.getOutputStream();
                DataOutputStream dato = new DataOutputStream ( aux );
                dato.writeUTF(t1.getText());

                skCliente.close();
            } catch (Exception ex) {}
        }
        
    }
        
    public void paint (Graphics g)
    {
        try { c = new Cliente1(); } 
        catch (Exception ex) {System.out.println("No se activó el cliente: " + ex);}       
    }
    
    public boolean action (Event e, Object obj) {
        
        if (e.target == b1) {
            try
            {    // Envía el entero '1' al servidor
                skCliente = new Socket(HOST, PUERTO);
                OutputStream aux1 = skCliente.getOutputStream();
                DataOutputStream dato = new DataOutputStream( aux1 );
                dato.writeUTF("1");
                
                c.proceso();
            } catch (Exception ex) {}
            return true;
        }
        
        if (e.target == b2) {
            try
            {    // Envía el entero '2' al servidor
                skCliente = new Socket(HOST, PUERTO);
                OutputStream aux2 = skCliente.getOutputStream();
                DataOutputStream dato1 = new DataOutputStream( aux2 );
                dato1.writeUTF("2");

                c.update();
            } catch (Exception ex) {}
            return true;
        }
        
        return false;
    }
}
