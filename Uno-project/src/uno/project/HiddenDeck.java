/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uno.project;
import java.util.Stack;
import java.util.Collections;
/**
 *
 * @author Utilisateur
 */
public class HiddenDeck
{
    private Stack<Card> deck;
    
    public HiddenDeck(Stack<Card> deck)
    {
        this.deck = deck;
    }
    
    public Stack<Card> shuffle()
    {
        Collections.shuffle(deck);
        return deck;
    }
    
    public Card getLastCard()
    {
        return deck.pop();
    }
}
