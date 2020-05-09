import java.applet.Applet;
import java.awt.Button;
import java.awt.Event;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.TextField;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

/**
 *
 * @author Nicolás David Espejo Bernal
 */
public class Cliente extends Applet 
{
    Client cliente = new Client();
    Socket skCliente;
    final static String HOST = "localhost";
    final static int PORT = 5123;
    
    int x = 10;
    int y = 20;

    Label l1 = new Label("----------------------------------------------------------------");
    Label labelResultado = new Label("................................................................");
    TextField nameField = new TextField();
    TextField valueField = new TextField();
    Button b1 = new Button("Realizar transferencia");
 
    public void init()
    {
        cliente.setValues();
        add(l1);
        add(nameField);
        add(valueField);
        add(b1);
        add(labelResultado);
    }
    
    public void paint(Graphics g)
    {
        g.drawString("Usuario: "+cliente.name, x, y);
        g.drawString("Saldo: $"+cliente.balance, x*20, y);
        l1.setLocation(x, y+5);
        g.drawString("Digite nombre de persona a transferir: ",x, y*3);
        nameField.setBounds(x*23, y+28, 57, 21);
        g.drawString("Digite cantidad a trasnferir: ",x, y*4);
        valueField.setBounds(x*23, y*2+28, 57, 21);
        b1.setLocation(x*15, y*6);
        labelResultado.setLocation(x*6, y*8);
    }
    
    public boolean action (Event e, Object obj)
    {
        if(e.target == b1)
        {
            if (cliente.dataCheck())
            {
                cliente.transaction();
                cliente.setValues();
                repaint();
            }
            else
            {
                labelResultado.setText("Verifique los valores ingresados.");
                repaint();
            }
        }
        return false; 
    }
    
    class Client
    {   
        String name;
        int balance;
       
        Client() {}
        
        /**
         * Obtiene el nombre y el saldo por parte del servidor y los muestra.
         */
        public void setValues()
        {
            try
            {    
                skCliente = new Socket(HOST, PORT);
                DataInputStream name = new DataInputStream ( skCliente.getInputStream() );
                this.name = name.readUTF();  
                DataInputStream balance = new DataInputStream ( skCliente.getInputStream() );
                this.balance = Integer.parseInt(balance.readUTF());
                skCliente.close();
            } catch (Exception ex) {System.out.println("Error setValues: "+ex);}
        }
        
        /**
         * Envía los datos ingresados al servidor.
         */
        public void transaction()
        {
            boolean successfulTransaction = false;
            try
            {    
                skCliente = new Socket(HOST, PORT);
                DataOutputStream currentName = new DataOutputStream ( skCliente.getOutputStream() );
                currentName.writeUTF(this.name);
                DataOutputStream name = new DataOutputStream ( skCliente.getOutputStream() );
                name.writeUTF(nameField.getText());
                DataOutputStream value = new DataOutputStream ( skCliente.getOutputStream() );
                value.writeUTF(valueField.getText());
                
                DataInputStream check = new DataInputStream ( skCliente.getInputStream() );
                successfulTransaction = check.readBoolean();
                skCliente.close();
            } catch (Exception ex) {System.out.println("Error transaction: "+ex);}
            finally
            {
                if(successfulTransaction)
                    labelResultado.setText("Operación extiosa.");
                else
                    labelResultado.setText("Verifique los valores ingresados.");
            }
        }
        
        /**
         * Verifica si los datos ingresados no corresponden a un valor negativo a la misma persona destino
         * @return true ó false según corresponda
         */
        public boolean dataCheck()
        {
            try{ return (nameField.getText().equalsIgnoreCase(this.name) || Integer.parseInt(valueField.getText()) <= 0) ? false : true ; }
            catch (Exception ex){ return false; }
        }
    }    
}
