/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uno.project;

import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import java.util.ArrayList;

/**
 *
 * @author Utilisateur
 */
public class Player
{
    private final String name;
    private final ArrayListWithPanel<CardButton> cards;
    
    public Player(String name, JPanel panel, int panelId, ArrayList<BufferedImage[]> cardImages)
    {
        this.name = name;
        this.cards = new ArrayListWithPanel<>(panel,panelId,cardImages);
    }

    public String getName()
    {
        return name;
    }
    
    public void turn(Game g)
    {
        int i;
        
        int test = 0;
        
        setRevealed(true);
        
        outerloop:
        while(true)
        {
            for(i = 0; i < cards.size(); ++i)
            {
                if(cards.get(i).isClicked())
                {
                    if(cards.get(i).getCard().canPlayOn(g.getRevealedDeckTop()))
                    {
                        cards.get(i).getCard().play(g);
                        cards.remove(cards.get(i));
                        break outerloop;
                    }
                }   
            }
            if(g.hiddenDeckClicked())
            {
                g.hiddenDeckDisable();
                draw(g.getHiddenDeckTop());
                break;
            }
            else
                System.out.println(g.hiddenDeckClicked());
        }
        
        setRevealed(false);
        
        if(cards.isEmpty())
            g.removePlayer();
    }
    
    public void draw(Card card)
    {
        if(card != null)
            cards.add(card);
    }
    
    public void setRevealed(boolean isRevealed)
    {
        for(int i = 0; i < cards.size(); ++i)
        {
            cards.get(i).setRevealed(isRevealed);
            cards.get(i).setDefinitiveDisable(!isRevealed);
        }
    }
}
