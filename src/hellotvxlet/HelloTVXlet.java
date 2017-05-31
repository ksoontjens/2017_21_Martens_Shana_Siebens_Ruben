package hellotvxlet;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
//import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Random;
import java.util.Timer;
import javax.tv.xlet.Xlet;
import javax.tv.xlet.XletContext;
import javax.tv.xlet.XletStateChangeException;
import org.dvb.event.EventManager;
import org.dvb.event.UserEvent;
import org.dvb.event.UserEventListener;
import org.dvb.event.UserEventRepository;
import org.havi.ui.HComponent;
import org.havi.ui.HScene;
import org.havi.ui.HSceneFactory;
import org.havi.ui.event.HRcEvent;

public class HelloTVXlet extends HComponent implements Xlet, ActionListener, UserEventListener {

    public static HelloTVXlet pongrs;
    
    public int breedte = 700, hoogte = 570; // hoogte en breedte van de kader 
    
    public StokjesTVXlet speler1;
    
    public StokjesTVXlet speler2;
    
    public BalletjeTVXlet kogel;
    
    public boolean bot = true /*, selecteren*/;
    
    public boolean left=false, right=false, up=false, down=false;

    public int spelStatus = 0, scoreLimiet = 10, Winnaar; //0 = Menu, 1 = Paused, 2 = Playing, 3 = End

    public Random randomNummer;
     
    public HelloTVXlet() 
    {
        
    }
    
    public void callback()
    {
        update();
    }
    
    public void start()
    {
	//this.repaint();
        spelStatus = 2;// beginnen bij beginscherm
	speler1 = new StokjesTVXlet(this, 1);
	speler2 = new StokjesTVXlet(this, 2);
	kogel = new BalletjeTVXlet(this);
    }
    
    public void update()
    {
	if (speler1.score >= scoreLimiet)
	{
            Winnaar = 1;
            spelStatus = 3;
	}

	if (speler2.score >= scoreLimiet)
	{
            Winnaar = 2;
            spelStatus = 3;
	}

	if (bot)
	{
                if (up)
		{
                    speler2.move(true);
		}
		if (down)
		{
                    speler2.move(false);
		}
                if (left)
                {
                    speler1.move(true);
                }
                if (right)
                {
                    speler1.move(false);
                }
	}

	kogel.update(speler1, speler2);
        //this.repaint();
    }
    
    public void paint(Graphics g) 
    {
        g.setColor(Color.BLACK);
	g.fillRect(0, 0, breedte, hoogte);
	//g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        
	/*MENU STARTPAGINA*/
        /*
        if (spelStatus == 0) // beginscherm
	{
                
		g.setColor(Color.ORANGE);
		g.setFont(new Font("Arial", 50, 90));
		g.drawString("PONGRS", breedte / 2 - 120, 200);
                
                g.setColor(Color.WHITE);
                g.setFont(new Font("Arial", 30, 50));
                g.drawString("Druk op 1 om te spelen", breedte / 2 - 200, hoogte / 2 + 25);
                
                //this.repaint();
        
	}*/
        
	if (spelStatus == 2) //gewoon spel spelen
	{
                
		g.setColor(Color.WHITE);
		g.drawLine(breedte / 2, 0, breedte / 2, hoogte);

		g.drawOval(breedte / 2 - 150, hoogte / 2 - 150, 300, 300);

		g.setFont(new Font("Arial", 1, 50));

		g.drawString(String.valueOf(speler1.score), breedte / 2 - 90, 50);
		g.drawString(String.valueOf(speler2.score), breedte / 2 + 65, 50);

		speler1.render(g);
		speler2.render(g);
		kogel.render(g);
                
                this.repaint();
	}

	if (spelStatus == 3) // eindscherm
	{
		
                g.setColor(Color.ORANGE);
		g.setFont(new Font("Arial", 50, 90));
		g.drawString("PONGRS", breedte / 2 - 120, 200);
            
                g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", 1, 50));

		if (bot && Winnaar == 1)
		{
			g.drawString("Speler 1 wint!", (breedte / 2) - 140, 300);
		}
		else
		{
			g.drawString("Speler 2 wint!", (breedte / 2) - 130, 300);
		}

		g.setFont(new Font("Arial", 1, 30));

		//g.drawString("Druk op 1 om opnieuw het spel te spelen", breedte / 2 - 250, 400);
                this.repaint();
	}
    }
        
    public void actionPerformed(ActionEvent e)
    {
        if (spelStatus == 2)
	{
		update();
	}
	this.repaint();
    }
    
    public static void main(String[] args) 
    {
        pongrs = new HelloTVXlet();
    }
    
     public void userEventReceived(UserEvent e) 
    {
        UserEvent k=e;
        //System.out.println("KEYPRESSED");
        if (e.getType()==HRcEvent.KEY_PRESSED)
        {
            int toets = e.getCode();

            if (toets == KeyEvent.VK_UP)
            {
		up = true;
            }
            else if (toets == KeyEvent.VK_DOWN)
            {
		down = true;
            }
            else if (toets == KeyEvent.VK_RIGHT)
            {
                right = true;      
            }
            else if (toets == KeyEvent.VK_LEFT)
            {
		left = true;
            }
            //*****************************************************
            /*else if (toets == KeyEvent.VK_1) // op 1 drukken 
            {
                //System.out.println("1");
		// MENU STARTPAGINA
                 
                if (spelStatus == 0)
		{
                        start();
                        spelStatus = 2;
                        this.repaint();
		}
                else 
                if(spelStatus == 2)
                {
                        spelStatus = 0;
                        this.repaint();
                }
                else if(spelStatus == 3)
                {
                        spelStatus = 0;
                        this.repaint();
                }
            }*/
        } else keyReleased(k);
        //this.repaint();
     }
    
    public void keyReleased(UserEvent e)
    {
	int toets = e.getCode();

	if (toets == KeyEvent.VK_LEFT)
	{
		left = false;
	}
	else if (toets == KeyEvent.VK_RIGHT)
	{
		right = false;
	}
	if (toets == KeyEvent.VK_UP)
	{
		up = false;
	}
	else if (toets == KeyEvent.VK_DOWN)
	{
		down = false;
	} 
    }
    
    public void keyTyped(KeyEvent e)
    {

    }

    public void destroyXlet(boolean unconditional) throws XletStateChangeException 
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void initXlet(XletContext ctx) throws XletStateChangeException 
    {
       HScene scene = HSceneFactory.getInstance().getDefaultHScene();
       UserEventRepository rep=new UserEventRepository("rep)");
       rep.addAllArrowKeys();
       rep.addAllNumericKeys();
       EventManager em=EventManager.getInstance();
       em.addUserEventListener(this, rep);

       scene.add (this);
       scene.validate();
       scene.setVisible(true);   
    }

    public void pauseXlet() 
    {
     
    }

    public void startXlet() throws XletStateChangeException 
    {
        start();
        this.setBounds(0,0,720,576);
        MijnTimerTask mtt=new MijnTimerTask(this);
        Timer timer =new Timer();
        timer.scheduleAtFixedRate(mtt, 0, 50);
	randomNummer = new Random();
        //this.repaint();
    }

}
