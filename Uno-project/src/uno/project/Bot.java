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
            switch (cards.get(i).getCard().getColor())
            {
                case 'r':
                    colors[0]++;
                    break;
                case 'g':
                    colors[1]++;
                    break;
                case 'b':
                    colors[2]++;
                    break;
                case 'y':
                    colors[3]++;
                    break;
                default:
                    break;
            }
        }
        
        for (int i = 0; i < colors.length; i++)
        {
            for (int j = 0; j < colors.length; j++)
            {
                if(colors[i]>=colors[priorityColor])
                    priorityColor=i;
            }
        }
        
        switch (priorityColor)
        {
            case 0:
                return 'r';
            case 1:
                return 'g';
            case 2:
                return 'b';
            default:
                return 'y';
        }
    }
    
    @Override
    public void turn(Game g)
    {
        int i;
        char priorityColor=getPriorityColor();
        
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
                        switch (step)
                        {
                            case 0:
                                if(cards.get(i).getCard() instanceof DrawCard || cards.get(i).getCard() instanceof SkipCard)
                                {
                                    cards.get(i).getCard().play(g);
                                    cards.remove(cards.get(i));
                                    break outerloop;
                                }   break;
                            case 1:
                                if(cards.get(i).getCard().getColor()!='d')
                                {
                                    if(cards.get(i).getCard().getColor()==priorityColor)
                                    {
                                        cards.get(i).getCard().play(g);
                                        cards.remove(cards.get(i));
                                        break outerloop;
                                    }
                                }   break;
                            case 2:
                                if(cards.get(i).getCard().getColor()!='d')
                                {
                                    cards.get(i).getCard().play(g);
                                    cards.remove(cards.get(i));
                                    break outerloop;
                                }   break;
                            case 3:
                                if(cards.get(i).getCard().getColor()=='d')
                                {
                                    if(cards.get(i).getCard() instanceof WildDrawCard)
                                        ((WildDrawCard) cards.get(i).getCard()).botPlay(g,priorityColor);
                                    else if(cards.get(i).getCard() instanceof WildCard)
                                        ((WildCard) cards.get(i).getCard()).botPlay(g,priorityColor);
                                    
                                    cards.remove(cards.get(i));
                                    break outerloop;
                                }   break;
                            default:
                                break;
                        }
                    }
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
            addCard(c,g);
        }
        
    }
}