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
    private final CardButton topCardButton;
    private Stack<Card> deck;

    public HiddenDeck(CardButton topCardButton)
    {
        this.topCardButton = topCardButton;
    }
    
    public Stack<Card> shuffle()
    {
        Collections.shuffle(deck);
        return deck;
    }

    public void setDeckShuffle(Stack<Card> deck)
    {
        this.deck = deck;
        deck = shuffle();
        
        topCardButton.setCard(deck.peek());
        topCardButton.setVisible(true);
    }
    
    public boolean isEmpty()
    {
        return deck.isEmpty();
    }
    
    public Card getTopCard()
    {
        Card top = deck.pop();
        
        if(!isEmpty())
            topCardButton.setCard(deck.peek());
        else
            topCardButton.setVisible(false);
        
        return top;
    }
}
