package hellotvxlet;

/**
 * @authors Ruben Siebens en Shana Martens
 */

import java.awt.Color;
import java.awt.Graphics;

public class StokjesTVXlet {

    HelloTVXlet pongrs; 
    
    public int stokNummer; // welk stokje
    
    public int x, y, breedte = 50, hoogte = 250; // afmetingen van balkje
    
    public int score; // score
    
    public StokjesTVXlet(HelloTVXlet pongrs, int stokNummer)
    {
        this.stokNummer = stokNummer;
        this.pongrs = pongrs;
        
        if (stokNummer == 1)
        {
            this.x = 0;
        }
        
        if (stokNummer == 2)
        {
            this.x = pongrs.breedte - breedte;
        }
        
        this.y = pongrs.hoogte / 2 - this.hoogte / 2;
    }
    
    public void render(Graphics g)
    {
        g.setColor(Color.WHITE);
        g.fillRect(x, y, breedte, hoogte);
    }
    
    public void move(boolean up)
    {
        int snelheid = 10;
        
        if (up)
        {
            if (y - snelheid > 0)
            {
                y -= snelheid;
            }
            else
            {
                y = 0;
            }
        }
        else
        {
            if (y + hoogte + snelheid < pongrs.hoogte)
            {
                y += snelheid;
            }
            else
            {
                y = pongrs.hoogte - hoogte;
            }
        }
        
    }
}
