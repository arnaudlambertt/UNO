/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uno.project;
import java.util.Stack;
/**
 *
 * @author Utilisateur
 */
public class RevealedDeck
{
    private final CardButton topCardButton;
    private Stack<Card> deck;

    public RevealedDeck(CardButton topCardButton)
    {
        this.topCardButton = topCardButton;
        this.topCardButton.setRevealing(true);
        
        this.deck = new Stack<>();
        deck.push(this.topCardButton.getCard());
    }

    public void setDeck(Stack<Card> deck)
    {
        this.deck = deck;
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