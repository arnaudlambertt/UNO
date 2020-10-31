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

// get the colour
 public int getColour();
 // get the symbol
 public char getSymbol();
 // test if it can be played
 public boolean canPlayOn(Card card);
 // implement any effects
 public void play(Game g);
}

