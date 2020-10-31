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
    private final char symbol;
    protected final char color;
    
    public Card(char symbol, char color)
    {
        this.symbol = symbol;
        this.color = color;
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

    // get the file name for the sprite
    public String getFileName()
    {
        return "sprites/" + getColor() + getSymbol() + ".png";
    }
    
    // test if it can be played, not if the player can play!!!
    }

    // implement any effects
    public abstract void play(Game g);

}
