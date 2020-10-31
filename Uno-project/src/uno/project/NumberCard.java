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
public class NumberCard extends Card
{
    public NumberCard(char symbol, char color)
    {
        super(symbol, color);
    }
    
    @Override
    public boolean canPlayOn(Card c)
    {
        return c.getSymbol() == this.getSymbol() || this.getColor() == c.getColor();
    }

    @Override
    public void play(Game g)
    {
    }

}
