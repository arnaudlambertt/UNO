/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uno.project;

/**
 *
 * @author Utilisateur
 */
public abstract class Card
{
    protected final int id;
    protected final char symbol;
    protected char color;
    
    public Card(int id,char symbol, char color)
    {
        this.id = id;
        this.symbol = symbol;
        this.color = color;
    }

    // get the symbol
    public int getId()
    {
        return id;
    }
    
    // get the symbol
    public char getSymbol()
    {
        return symbol;
    }  

    // get the color
    public char getColor()
    {
        return color;
    }
    
    // test if it can be played, not if the player can play!!!
    public boolean canPlayOn(Card c)
    {
        return c.getColor() == color || c.getSymbol() == getSymbol(); 
    }

    // implement any effects
    public abstract void play(Game g);

}
