/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uno.project;

import javax.swing.JOptionPane;

/**
 *
 * @author Arnaud
 */
public class WildCard extends Card
{

    public WildCard(int id)
    {
        super(id, 'w', 'd');
    }
    
    @Override
    public boolean canPlayOn(Card c)
    {
        return true;
    }

    public boolean setColor(char color)
    {
        this.color = color;
        
        return (color != 'r' && color != 'g' && color != 'b' && color != 'y');
    }
    
    @Override
    public void play(Game g)
    {
        super.play(g);
        
        while(setColor(JOptionPane.showInputDialog("Enter a color:").charAt(0))) {}
    }    
    
    @Override
    public void botPlay(Game g, ArrayListWithPanel<CardButton> cards)
    {
        String selectedColor = autoSelectColor(cards);
        
        super.play(g);
        
        JOptionPane.showMessageDialog(null, selectedColor +" selected");
    }  
    
    public String autoSelectColor(ArrayListWithPanel<CardButton> cards)
    {
        int red=0, green=0, yellow=0, blue=0;
        
        for (int i = 0; i < cards.size(); i++)
        {
            if(cards.get(i).getCard().getColor()=='r')
                red++;
            else if(cards.get(i).getCard().getColor()=='g')
                green++;
            else if(cards.get(i).getCard().getColor()=='y')
                yellow++;
            else if(cards.get(i).getCard().getColor()=='b')
                blue++;
        }
        
        if(red>=green && red>=yellow && red>=blue)
        {
            setColor('r');
            return "Red";
        }
        else if(green>=red && green>=yellow && green>=blue)
        {
            setColor('g');
            return "Green";
        }
        else if(yellow>=red && yellow>=green && yellow>=blue)
        {
            setColor('y');
            return "Yellow";
        }
        else {
            setColor('b');
            return "Blue";
        }
    }
}
