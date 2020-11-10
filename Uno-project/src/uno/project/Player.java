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
public class Player
{
    private final String name;
    protected final ArrayListWithPanel<CardButton> cards;
    
    public Player(String name, JPanel panel, int panelId, ArrayList<BufferedImage[]> cardImages)
    {
        this.name = name;
        this.cards = new ArrayListWithPanel<>(panel,panelId,cardImages, name);
    }

    public String getName()
    {
        return name;
    }
    
    public void turn(Game g)
    {
        int i;
                
        setRevealed(true);
        
        outerloop:
        while(true)
        {
            for(i = 0; i < cards.size(); ++i)
            {
                if(cards.get(i).isClicked())
                {
                    if(cards.get(i).getCard().canPlayOn(g.getRevealedTop()))
                    {
                        cards.get(i).getCard().play(g);
                        cards.remove(cards.get(i));
                        break outerloop;
                    }
                }   
            }
            if(g.hiddenDeckClicked())
            {
                draw(g);
                break;
            }
            try
            {
                Thread.sleep(10);
            }
            catch (InterruptedException e)
            {
                       
            }
        }
        
        setRevealed(false);
        
        if(cards.isEmpty())
            g.removePlayer();
    }
    
    public void addCard(Card card, Game g)
    {
        if(card != null)
            cards.add(card, g);
    }
    
    public void draw(Game g)
    {
        Card c = g.revealHiddenTop();
        
        while(!g.hiddenDeckClicked())
        {
           try
            {
                Thread.sleep(10);
            }
            catch (InterruptedException e)
            {
                       
            } 
        }
        
        if(c.canPlayOn(g.getRevealedTop()) && (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(null, "Play this card?", null, JOptionPane.YES_NO_OPTION)))
        {    
            g.getHiddenTop().play(g);
        }
        else
        {
            g.getHiddenTop();
            addCard(c, g);
        }
        
    }
    
    public void setRevealed(boolean isRevealed)
    {
        for(int i = 0; i < cards.size(); ++i)
        {
            cards.get(i).setRevealed(isRevealed);
            cards.get(i).setDefinitiveDisable(!isRevealed);
        }
    }

    JPanel getPanel()
    {
        return cards.getPanel();
    }

    int getPanelId()
    {
        return cards.getPanelId();
    }
    
    public void refreshPanel(JPanel panel, int panelId, Game g)
    {
        cards.refreshPanel(panel, panelId, g);
    }
    
    public void setColorRed(boolean isRed)
    {
        cards.setColorRed(isRed);
    }
    
    public void hideBorder()
    {
        cards.hideBorder();
    }
}
