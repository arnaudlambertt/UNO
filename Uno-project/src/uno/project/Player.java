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
        
        setRevealed(true);
        
        outerloop:
        while(true)
        {
            for(i = 0; i < cards.size(); ++i)
            {
                if(cards.get(i).isClicked())
                {
                    System.out.println(cards.get(i).getCard().getId() + ' ' + cards.get(i).getCard().getSymbol() + ' ' + cards.get(i).getCard().getColor());
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
                draw(g.getHiddenDeckTop());
                break;
            }
        }
        
        setRevealed(false);
        
        if(cards.isEmpty())
            g.removePlayer();
    }
    
    public void draw(Card card)
    {
        cards.add(card);
    }
    
    public void setRevealed(boolean isRevealed)
    {
        for(int i = 0; i < cards.size(); ++i)
            cards.get(i).setRevealed(isRevealed);
    }
}
