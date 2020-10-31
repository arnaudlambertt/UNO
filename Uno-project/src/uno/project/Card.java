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
public interface Card {
 // get the symbol
 public char getSymbol();    
    
// get the color
 public char getColor();
 
 // test if it can be played, not if the player can play!!!
 public boolean canPlayOn(Card card);
 
 // implement any effects
 public void play(Game g);
 
 // get the file name for the sprite
 public String getFileName();
}

