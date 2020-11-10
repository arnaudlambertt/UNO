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
        char colorToSet='e';
        
        super.play(g);
        
        while(setColor(colorToSet))
        {
            try
            {
                colorToSet = JOptionPane.showInputDialog("Enter a color:").charAt(0);
                
                if(colorToSet>='A')
                    colorToSet += 'a'-'A';
            } catch (Exception e)
            {
            }
        }
    }    
    
    public void botPlay(Game g, char priorityColor)
    {
        String selectedColor;
                
        setColor(priorityColor);
        
        if(priorityColor=='r')
            selectedColor="Red";
        else if(priorityColor=='g')
            selectedColor="Green";
        else if(priorityColor=='y')
            selectedColor="Yellow";
        else selectedColor="Blue";
        
        super.play(g);
        
        JOptionPane.showMessageDialog(null, selectedColor +" selected");
    }  
}
