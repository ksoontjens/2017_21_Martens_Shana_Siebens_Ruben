package hellotvxlet;

/**
 * @authors Ruben Siebens en Shana Martens
 */

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class BalletjeTVXlet {

    public int x, y, breedte = 25, hoogte = 25;
    
    public int bewegingX, bewegingY;
    
    public Random randomNummer;
    
    private HelloTVXlet pongrs;
    
    public int aantalGeraakt;
    
    public BalletjeTVXlet(HelloTVXlet pongrs)
    {
        this.pongrs = pongrs;
        
        this.randomNummer = new Random();
        
        spawn();
    }
    
    public void update(StokjesTVXlet stokje1, StokjesTVXlet stokje2)
    {
        int snelheid = 10;
        
        this.x += bewegingX * snelheid;
        this.y += bewegingY * snelheid;
        
        if (this.y + hoogte - bewegingY > pongrs.hoogte || this.y + bewegingY < 0)
        {
            if (this. bewegingY < 0)
            {
                this.y = 0;
                this.bewegingY = randomNummer.nextInt(4);
                
                if (bewegingY == 0)
                {
                    bewegingY = 1;
                }
            }
            else 
            {
                this.bewegingY = -randomNummer.nextInt(4);
                this.y = pongrs.hoogte - hoogte;
                
                if (bewegingY == 0)
                {
                    bewegingY = -1;
                }
            }
        }
        
        if (checkCollision(stokje1) == 1)
        {
            this.bewegingX = 1 + (aantalGeraakt / 5);
            this.bewegingY = -2 + randomNummer.nextInt(4);
            
            if (bewegingY == 0)
            {
                bewegingY = 1;
            }
            
            aantalGeraakt++;
        }
        else if (checkCollision(stokje2) == 1)
        {
          this.bewegingX = -1 - (aantalGeraakt / 5); 
          this.bewegingY = -2 + randomNummer.nextInt(4);
          
          if (bewegingY == 0)
          {
              bewegingY = 1;
          }
          
          aantalGeraakt++;
        }
        
        if (checkCollision(stokje1) == 2)
        {
            stokje2.score++;
            spawn();
        }
        else if (checkCollision(stokje2) == 2)
        {
            stokje1.score++;
            spawn();
        }
    }
    
    public void spawn()
    {
        this.aantalGeraakt = 0;
        this.x = pongrs.breedte / 2 - this.breedte / 2;
        this.y = pongrs.hoogte / 2 - this.hoogte / 2;

        this.bewegingY = -2 + randomNummer.nextInt(4);

        if (bewegingY == 0)
        {
            bewegingY = 1;
        }

        if (randomNummer.nextBoolean())
        {
            bewegingX = 1;
        }
        else
        {
            bewegingY = -1;
        }
    }
    
    public int checkCollision(StokjesTVXlet stokje)
    {
        if (this.x < stokje.x + stokje.breedte && this.x + breedte > stokje.x && this.y < stokje.y + stokje.hoogte && this.y + hoogte > stokje.y)
        {
            return 1; //grens
	}
	else if ((stokje.x > x && stokje.stokNummer == 1) || (stokje.x < x - breedte && stokje.stokNummer == 2))
	{
            return 2; //score
	}

	return 0; //niets returnen
    }
    
    public void render(Graphics g)
    {
	g.setColor(Color.WHITE);
	g.fillOval(x, y, breedte, hoogte);
    }
}
