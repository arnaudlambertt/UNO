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
                String[] options =
                {
                    "Red", "Green", "Blue", "Yellow"
                };

                int x = JOptionPane.showOptionDialog(null, "Pick a color", "Pick a color",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                switch (x)
                {
                    case 0:
                        colorToSet = 'r';
                        break;
                    case 1:
                        colorToSet = 'g';
                        break;
                    case 2:
                        colorToSet = 'b';
                        break;
                    case 3:
                        colorToSet = 'y';
                        break;
                    default:;
                        break;
                }
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
