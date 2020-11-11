/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uno.project;

import java.awt.event.ActionEvent;
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
    
    public void addCard(Card card, Game g)
    {
        if(card != null)
            cards.add(card, g);
    }
    
    public void draw(Card c, Game g)//not event driven
    {        
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

    boolean tryMove(ActionEvent e, Game g)
    {
        CardButton cb = (CardButton) e.getSource();
        
        if(cb == g.getHiddenDeck().getTopCardButton())//hidden deck
        {
            draw(cb.getCard(),g);
            setRevealed(false);
            return true;
        }
        else if(cb.getCard().canPlayOn(g.getRevealedTop()))
        {
            cards.remove(cb);
            cb.getCard().play(g);
            setRevealed(false);
        
            if(cards.isEmpty())
                g.removePlayer();
            
            return true;
        }
        else    
            return false;    
    }
}
