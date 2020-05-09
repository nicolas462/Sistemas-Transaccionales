/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trans;

import java.applet.Applet;
import java.awt.Graphics;

/**
 *
 * @author BOG-A306-E-005
 */
public class Concurrencia extends Applet
{

    public void paint(Graphics g)
    {
        Nombres n = new Nombres (g);
        n.start();
        Direcciones dir = new Direcciones(g);
        dir.start();
        
        while(n.isAlive() || dir.isAlive())
        {
            g.drawString("Proceso en ejecución.", 100, 100);
        }
    }
    
}
