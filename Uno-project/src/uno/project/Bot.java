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
    
    public char getPriorityColor()
    {
        int[] colors = new int[4]; //[r, g, b ,y]
        int priorityColor=0;
        
        for (int i = 0; i < colors.length; i++)
            colors[i]=0;
        
        for (int i = 0; i < cards.size(); i++)
        {
            if(cards.get(i).getCard().getColor()=='r')
                colors[0]++;
            else if(cards.get(i).getCard().getColor()=='g')
                colors[1]++;
            else if(cards.get(i).getCard().getColor()=='b')
                colors[2]++;
            else if(cards.get(i).getCard().getColor()=='y')
                colors[3]++;
        }
        
        for (int i = 0; i < colors.length; i++)
        {
            for (int j = 0; j < colors.length; j++)
            {
                if(colors[i]>=colors[priorityColor])
                    priorityColor=i;
            }
        }
        
        if(priorityColor==0)
            return 'r';
        else if(priorityColor==1)
            return 'g';
        else if(priorityColor==2)
            return 'b';
        else return 'y';
    }
    
    @Override
    public void turn(Game g)
    {
        int i;
        char priorityColor=getPriorityColor();
        setRevealed(true);
        
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
            for (int step = 0; step < 4; step++)
            {
                for(i = 0; i < cards.size(); ++i)
                {
                    if(cards.get(i).getCard().canPlayOn(g.getRevealedTop()))
                    {
                        if(step==0)
                        {
                            if(cards.get(i).getCard() instanceof DrawCard || cards.get(i).getCard() instanceof SkipCard)
                            {
                                JOptionPane.showMessageDialog(null, "Joue d'abord +2 / skip");
                                cards.get(i).getCard().play(g);
                                cards.remove(cards.get(i));
                                break outerloop;
                            }
                        }

                        else if(step==1)
                        {
                            if(cards.get(i).getCard().getColor()!='d')
                            {
                                if(cards.get(i).getCard().getColor()==priorityColor)
                                {
                                    JOptionPane.showMessageDialog(null, "Priority color");
                                    cards.get(i).getCard().play(g);
                                    cards.remove(cards.get(i));
                                    break outerloop;
                                }
                            }
                        }
                        
                        else if(step==2)
                        {
                            if(cards.get(i).getCard().getColor()!='d')
                            {
                                JOptionPane.showMessageDialog(null, "Non priority number");
                                cards.get(i).getCard().play(g);
                                cards.remove(cards.get(i));
                                break outerloop;
                            }
                        }

                        else if(step==3)
                        {
                            if(cards.get(i).getCard().getColor()=='d')
                            {
                                JOptionPane.showMessageDialog(null, "Couleur");
                                if(cards.get(i).getCard() instanceof WildDrawCard)
                                    ((WildDrawCard) cards.get(i).getCard()).botPlay(g,priorityColor);
                                else if(cards.get(i).getCard() instanceof WildCard)
                                    ((WildCard) cards.get(i).getCard()).botPlay(g,priorityColor);

                                cards.remove(cards.get(i));
                                break outerloop;
                            }
                        }
                    }
                }
            }
            JOptionPane.showMessageDialog(null, "Pioche");
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
        char priorityColor=getPriorityColor();
        
        if(c.canPlayOn(g.getRevealedTop()))
        {
            if(c instanceof WildCard)
                ((WildCard) c).botPlay(g,priorityColor);
            else if(c instanceof WildDrawCard)
                ((WildDrawCard) c).botPlay(g,priorityColor);
            else c.play(g);
        }
        else
        {
            addCard(c);
        }
        
    }
}