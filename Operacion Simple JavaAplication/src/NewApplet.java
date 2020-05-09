/*
 * Ejemplo operación simple Java Application.
 */

import java.applet.Applet;
import java.awt.Button;
import java.awt.Event;
import java.awt.Label;
import java.awt.TextField;

/**
 *
 * @author Nicolas
 */
public class NewApplet extends Applet 
{
    
    Label m1 = new Label ("Ingrese precio del producto: ");
    TextField t1 = new TextField(10);
    Label m2 = new Label ("Ingrese cantidad de productos vendidos: ");
    TextField t2 = new TextField(10);
    Button b1 = new Button("Operar");
    Label m3 = new Label ("Dinero acumulado: ");
    Label res = new Label();
    
    int cantidadProducto, precioProducto, resultado;
    
    public void init() 
    {
        add(m1);
        add(t1);
        add(m2);
        add(t2);
        add(b1);
        add(m3);
        add(res);
    }
    
    public boolean action (Event e, Object obj)
    {
        if (e.target == b1)
        {
            precioProducto = Integer.parseInt(t1.getText());
            cantidadProducto = Integer.parseInt(t2.getText());
            resultado = precioProducto * cantidadProducto;
            res.setText(String.valueOf(resultado));
        }
        return false;
    }

    // TODO overwrite start(), stop() and destroy() methods
}
