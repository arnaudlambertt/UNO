/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uno.project;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import java.util.ArrayList;
import javax.swing.JOptionPane;
/**
 *
 * @author Utilisateur
 */
public class Bot extends Player
{
    public Bot(String name, JPanel panel, int panelId, ArrayList<BufferedImage[]> cardImages)
    {
        super(name, panel, panelId, cardImages);
    }
    
    @Override
    public void turn(Game g)
    {
        int i;
        
        try
        {
            Thread.sleep(1000);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
        
        outerloop:
        while(true)
        {
            for(i = 0; i < cards.size(); ++i)
            {
                if(cards.get(i).getCard().canPlayOn(g.getRevealedTop()))
                {
                    if(cards.get(i).getCard() instanceof WildCard)
                        ((WildCard) cards.get(i).getCard()).botPlay(g,cards);
                    else if(cards.get(i).getCard() instanceof WildDrawCard)
                        ((WildDrawCard) cards.get(i).getCard()).botPlay(g,cards);
                    else cards.get(i).getCard().play(g);
                    cards.remove(cards.get(i));
                    break outerloop;
                }
            }
            
            draw(g);
            break;
        }
        
        if(cards.isEmpty())
            g.removePlayer();
    }
    
    @Override
    public void draw(Game g)
    {
        Card c = g.getHiddenTop();
        
        if(c.canPlayOn(g.getRevealedTop()))
        {
            if(c instanceof WildCard)
                ((WildCard) c).botPlay(g,cards);
            else if(c instanceof WildDrawCard)
                ((WildDrawCard) c).botPlay(g,cards);
            else c.play(g);
        }
        else
        {
            addCard(c);
        }
        
    }
}
