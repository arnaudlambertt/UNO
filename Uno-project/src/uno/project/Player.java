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
    private int score;

    public Player(String name, ArrayList<BufferedImage[]> cardImages)
    {
        this.name = name;
        this.cards = new ArrayListWithPanel<>(cardImages, name);
        this.score = 0;
    }

    public String getName()
    {
        return name;
    }

    public int getScore()
    {
        return score;
    }

    public void addScore(double score)
    {
        this.score += score;
    }
    
    public void addCard(Card card, Game g)
    {
        if (card != null)
            cards.add(card, g);
    }

    public void draw(Card c, Game g)//not event driven
    {
        if (c.canPlayOn(g.getRevealedTop()) && (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(null, "Play this card?", null, JOptionPane.YES_NO_OPTION)))
            g.getHiddenTop().play(g);
        else
        {
            g.getHiddenTop();
            addCard(c, g);
        }
    }

    public void setRevealed(boolean revealed)
    {
        for (int i = 0; i < cards.size(); ++i)
        {
            cards.get(i).setRevealed(revealed);
            cards.get(i).setDefinitiveDisable(!revealed);
        }
    }
    
    public void setEnabled(boolean enabled)
    {
        cards.forEach((c) ->
        {
            c.setDefinitiveDisable(!enabled);
        });

    }

    JPanel getPanel()
    {
        return cards.getPanel();
    }

    int getPanelId()
    {
        return cards.getPanelId();
    }
    
    public void setPanel(JPanel panel)
    {
        cards.setPanel(panel);
    }

    public void setPanelId(int panelId)
    {
        cards.setPanelId(panelId);
    }

    public void refreshPanel(JPanel panel, int panelId, Game g)
    {
        cards.refreshPanel(panel, panelId, g);
    }

    public void setColorRed(boolean red)
    {
        cards.setColorRed(red);
    }

    public void hideBorder()
    {
        cards.hideBorder();
    }

    boolean tryMove(ActionEvent e, Game g)
    {
        CardButton cb = (CardButton) e.getSource();

        if (cb == g.getHiddenDeck().getTopCardButton())//hidden deck
        {
            draw(cb.getCard(), g);
            setRevealed(false);
            return true;
        } else if (cb.getCard().canPlayOn(g.getRevealedTop()))
        {
            cards.remove(cb);
            cb.getCard().play(g);
            setRevealed(false);

            if (cards.isEmpty())
                g.removePlayer();

            return true;
        } else
            return false;
    }
    
    public void clear()
    {
        cards.clear();
    }
    
    public int calculateScore()
    {
        int points = 0;
        for(CardButton cb : cards)
        {
            if(cb.getCard() instanceof DrawCard || cb.getCard() instanceof ReverseCard || cb.getCard() instanceof SkipCard)
                points += 20;
            else if(cb.getCard() instanceof DrawCard)
                points += 50;
            else
                points += (int) cb.getCard().getSymbol() - 30;
        }
        return points;
    }
}
