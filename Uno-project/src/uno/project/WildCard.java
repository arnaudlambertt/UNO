/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uno.project;

import java.awt.HeadlessException;
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
        char colorToSet = 'e';

        super.play(g);

        do
        {
            try
            {
                colorToSet = JOptionPane.showInputDialog("Enter a color:").charAt(0);

                if (colorToSet <= 'Z')
                    colorToSet += 'a' - 'A';
            } catch (HeadlessException e)
            {
            }
        } while (setColor(colorToSet));
    }

    public void botPlay(Game g, char priorityColor)
    {
        String selectedColor;

        setColor(priorityColor);

        switch (priorityColor)
        {
            case 'r':
                selectedColor = "Red";
                break;
            case 'g':
                selectedColor = "Green";
                break;
            case 'y':
                selectedColor = "Yellow";
                break;
            default:
                selectedColor = "Blue";
                break;
        }

        super.play(g);

        JOptionPane.showMessageDialog(null, selectedColor + " selected");
    }
}
