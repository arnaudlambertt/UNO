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
    }

    public Stack<Card> getDeck()
    {
        Stack<Card> oldDeck = new Stack<>();
        oldDeck.addAll(deck);
        deck.removeAllElements();
        
        return oldDeck;
    }
    
    public void addCard(Card card)
    {
        deck.push(topCardButton.getCard());
        topCardButton.setCard(card);
        topCardButton.setRevealed(true);
    }
    
    public Card getTopCard()
    {
        return topCardButton.getCard();
    }
}