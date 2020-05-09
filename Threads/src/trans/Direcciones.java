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
class Direcciones extends Thread
{
    int x = 80,y = 20;
    int i = 1;
    Graphics g;
    String dir="Calle 69";
    
    public Direcciones (Graphics g)
    {
        this.g=g;
    }
    
        public void run()
    {
        while (i < 5){
            g.drawString(dir, x, y);
            y += 15;
            i++;
            try
            {
                Thread.sleep(2000);
            } catch (Exception e){ System.out.println("Erro al dormir el proceso."+e.getMessage());}
        }
    }
}
