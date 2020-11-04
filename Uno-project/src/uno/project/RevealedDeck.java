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
public final class RevealedDeck
{
    private final CardButton topCardButton;
    private final Stack<Card> deck;

    public RevealedDeck(CardButton topCardButton)
    {
        this.topCardButton = topCardButton;
        this.topCardButton.setRevealed(true);
        this.deck = new Stack<>();
        setVisible(false);
    }

    public Stack<Card> emptyDeck()
    {
        Stack<Card> oldDeck = new Stack<>();
        
        Card top = deck.pop();
        oldDeck.addAll(deck);
        deck.push(top);
        
        return oldDeck;
    }
    
    public void addCard(Card card)
    {
        deck.push(card);
        topCardButton.setCard(card);
        topCardButton.setRevealed(true);
    }
    
    public void setVisible(boolean isVisible)
    {
        topCardButton.setVisible(isVisible);
    }
}