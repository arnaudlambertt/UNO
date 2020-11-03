/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uno.project;
import java.util.ArrayList;

/**
 *
 * @author Utilisateur
 */
public class Game
{
    private ArrayList<Player> player;
    private HiddenDeck hiddenDeck;
    private RevealedDeck revealedDeck;
    
    public Game()
    {
        hiddenDeck = new HiddenDeck();
        hiddenDeck.shuffle();
    }
    
    public void play()
    {
        for(int i=0; i<76; i++)
        {            
            Card lastCard = hiddenDeck.revealLastCard();
            System.out.println(lastCard.getSymbol() +" "+ lastCard.getColor());
        }
    }
}
