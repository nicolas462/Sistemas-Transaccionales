import java.applet.Applet;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

/**
 *
 * @author Nicolas David Espejo Bernal
 */
public class Servidor extends Applet {

    final static int PORT = 5123;
    ServerSocket skServidor;
    Conexion c = new Conexion();

    public void paint(Graphics g)
    {
        try
        {
            skServidor = new ServerSocket(PORT);
            Server server = new Server();
            do
            {
                clear();
                server.showInformation(g);
                server.sendCurrentValues();
                server.executeTransaction();
            } while(true);
        } catch (Exception ex) { System.out.println("Error Paint: " + ex); }
    }
    
    /**
     * Limpia la pantalla del Applet
     */
    public void clear()
    {
        Graphics g = getGraphics();
        Dimension d = getSize();
        Color c = getBackground();
        g.setColor(c);
        g.fillRect(0, 0, d.width, d.height);
        repaint();
    }
    
    class Server
    {
        int y = 20;
        int x = 20;
        boolean successfulTransaction = false;
        public Server(){} 
        
        /**
         * Muestra la información de la base de datos
         * @param g 
         */
        public void showInformation(Graphics g)
        {
            try
            {              
                c.ps =  c.con.prepareStatement("SELECT * FROM cuenta");
                c.rs = c.ps.executeQuery();
                g.drawString("| NOMBRE ", x , y);
                g.drawString("______________________", x-10, y+5);
                g.drawString("| SALDO ", x*5, y);
                while(c.rs.next())
                {
                    g.drawString("| "+c.rs.getString("nombre"), x, 2*y);
                    g.drawString("| "+c.rs.getString("saldo"), x*5, 2*y);
                    y += 20;
                    x = 20;
                }
                y = 20;
                repaint();
            } catch (Exception ex) { System.out.println("Error showInformation"+ex); };
        }
        
        /**
         * Retorna el nombre mediante el id en la BBDD
         * @param id
         * @return 
         * @throws SQLException 
         */
        public String getName(int id) throws SQLException
        {
            try
            {
                c.ps = c.con.prepareStatement("SELECT * FROM cuenta WHERE idCuenta="+id);
                c.rs = c.ps.executeQuery();
                c.rs.next();
                this.successfulTransaction = true;
                return c.rs.getString("nombre");
            } catch (Exception ex) {
                this.successfulTransaction = false;
                System.out.println("Error getName: " + ex); 
            }
            return null;
        }
        
        /**
         * Retorna el saldo mediante el nombre en la BBDD
         * @param name
         * @return
         * @throws SQLException 
         */
        public int getBalance(String name) throws SQLException
        {
            try
            {
                c.ps = c.con.prepareStatement("SELECT * FROM cuenta WHERE nombre='"+name+"'");
                c.rs = c.ps.executeQuery();
                c.rs.next();
                this.successfulTransaction = true;
                return c.rs.getInt("saldo");
            } catch (Exception ex) { 
                this.successfulTransaction = false;
                c.con.rollback();
                System.out.println("Error getBalance: " + ex); 
                return -1;
            }
        }
        
        /**
         * Envía al cliente la información del nombre y el saldo actual
         */
        public void sendCurrentValues () 
        {
            try
            {
                Socket skCliente = skServidor.accept();
                String currentName = getName(1);
                DataOutputStream name = new DataOutputStream ( skCliente.getOutputStream() );
                name.writeUTF(currentName);
                DataOutputStream balance = new DataOutputStream ( skCliente.getOutputStream() );
                balance.writeUTF(Integer.toString(getBalance(currentName)));
                skCliente.close();
            } catch (Exception ex) {System.out.println("Error sendValues: "+ex);}
        }
        
        /**
         * Update a la base de datos
         * @param name = Nombre al que corresponde la información a modificar
         * @param value = Valor a sumar
         */
        public void updateBalance(String name, int value) throws SQLException
        {
            try
            {
                c.ps = c.con.prepareStatement("UPDATE cuenta SET saldo="+(getBalance(name)+value)+" WHERE nombre='"+name+"'");
                c.ps.executeUpdate();
            } catch (Exception ex) {
                c.con.rollback();
                System.out.println("Error updateBalance: "+ex);
            }
        }
        
        /**
         * Realiza la transferencia de una cuenta a otra
         * @throws SQLException 
         */
        public void executeTransaction() throws SQLException, IOException
        {
            Socket skCliente = skServidor.accept();
            try
            {
                DataInputStream name = new DataInputStream ( skCliente.getInputStream() );
                String currentName = name.readUTF();
                DataInputStream name1 = new DataInputStream ( skCliente.getInputStream() );
                String destinationName = name1.readUTF();
                DataInputStream value = new DataInputStream ( skCliente.getInputStream() );
                int amount = Integer.parseInt(value.readUTF());
                updateBalance(currentName, amount*-1);
                updateBalance(destinationName, amount);
                c.con.commit();
            } catch(Exception ex) {
                this.successfulTransaction = false;
                c.con.rollback();
                System.out.println("Error executeTransaction: "+ex);
            } finally {
                sendStatusTransaction(skCliente,this.successfulTransaction);
                skCliente.close();
                c.ps.close();
            }
        }
        
        /**
         * Envía al cliente si se concretó la transacción
         * @param check 
         */
        public void sendStatusTransaction(Socket skCliente, boolean check)
        {
            try
            {
                DataOutputStream checker = new DataOutputStream ( skCliente.getOutputStream() );
                checker.writeBoolean(check);
            } catch (Exception ex) {System.out.println("Error sendStatusTransaction: "+ex);}
        }
    }
}
