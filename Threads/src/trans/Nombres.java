/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trans;

import java.awt.Graphics;

/**
 *
 * @author BOG-A306-E-005
 */
public class Nombres extends Thread
{
    int x = 20,y = 20;
    int i = 1;
    Graphics g;
    String[] nombre={"Carlos", "Juan", "Pedro", "Andrés", "Andrea"};
    
    public Nombres (Graphics g){
        this.g=g;
    }
    
    public void run()
    {
        while (i < 5){
            g.drawString(nombre[i], x, y);
            y += 15;
            i++;
            try
            {
                Thread.sleep(1000);
            } catch (Exception e){ System.out.println("Erro al dormir el proceso."+e.getMessage());}
        }
    }
    
}
