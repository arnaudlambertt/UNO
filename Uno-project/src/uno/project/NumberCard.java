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
    
    
}
