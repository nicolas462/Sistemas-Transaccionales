/*
 * Recibe solicitudes del cliente según la opción elegida
 */
package transaccionbancaria;

import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Label;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Nicolas
 */
public class Servidor extends Applet {

    final static int PUERTO = 5100;
    ServerSocket skServidor;
    Conexion c = new Conexion();
    PreparedStatement ps;
    ResultSet rs;
    Label l1 = new Label("");
    Label l2 = new Label("");
    
    public void init() 
    {
        add(l1);
        add(l2);
    }

    class Server
    {
        public Server(Graphics g) { g.drawString("Servidor iniciado puerto: " + 500, 50, 50); resize(300, 300); }
        
        /**
         * Recibe información del cliente
         * @return 1 ó 2, según corresponda al dato envíado desde el cliente
         */
        public int getOption()
        {
            try
            {
                Socket skCliente = skServidor.accept();
                DataInputStream datos = new DataInputStream ( skCliente.getInputStream() );
                return Integer.parseInt(datos.readUTF()); 
            } catch (Exception e) {System.out.println("Error option: " + e);}
            return -1;
        }
        
        /**
         * Realiza una consulta en la BBDD y envía los datos al cliente
         */
        public void consulta() 
        {
            try
            {
                Socket skCliente = skServidor.accept();

                ps = c.con.prepareStatement("SELECT * FROM saldo");
                rs = ps.executeQuery();
                rs.next();

                l2.setText("Obteniendo datos de la base de datos...");

                DataOutputStream dato = new DataOutputStream ( skCliente.getOutputStream() );

                String resultado = "Su saldo es: $" + rs.getInt("saldo");
                dato.writeUTF(resultado);
                skCliente.close();
            } 
            catch (Exception e) {
                System.out.println("Error consulta: " + e);
            }
        }
    
        /**
         * Recibe infromación del cliente y envía los datos a la BBDD
         * @throws IOException 
         */
        public void actualizar() throws IOException 
        {
                try 
                {
                    Socket skCliente = skServidor.accept();
 
                    InputStream aux = skCliente.getInputStream();
                    DataInputStream datos = new DataInputStream ( aux );

                    ps = c.con.prepareStatement("UPDATE saldo SET saldo = " + Integer.parseInt(datos.readUTF()));
                    ps.executeUpdate();
                    
                    skCliente.close();
                } catch (Exception ex) {
                    System.out.println("Error actualizar: " + ex);
                }
            }

        }
    
    public void paint (Graphics g)
    {
        try
        {
            skServidor = new ServerSocket (PUERTO);
            Server s = new Server(g);
            int opcion;            

            do 
            {
                opcion = s.getOption();
                System.out.println("Option seleccionada: " + opcion);
                switch (opcion)
                {
                    case 1: 
                        System.out.println("Opción 1");
                        s.consulta();
                        break;
                    case 2:
                        System.out.println("Opción 2");
                        s.actualizar();
                        break;
                }   
            } while (opcion != 3);

        } catch (Exception e) {System.out.println("Error paint: " + e);}
    }
}
