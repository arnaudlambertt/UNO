/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uno.project;

/**
 *
 * @author Arnaud
 */
public class NumberCard implements Card
{

    private final char symbol;
    private final char color;

    public NumberCard(char symbol, char color)
    {
        this.symbol = symbol;
        this.color = color;
    }

    @Override
    public char getSymbol()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public char getColor()
    {
        return color;
    }

    @Override
    public String getFileName()
    {
        return "sprites/" + getColor() + getSymbol() + ".png";
    }
    
    @Override
    public boolean canPlayOn(Card c)
    {
        return c.getSymbol() == this.symbol || this.color == c.getColor();
    }

    @Override
    public void play(Game g)
    {
    }

}
