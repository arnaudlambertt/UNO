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
public final class HiddenDeck
{
    private final CardButton topCardButton;
    private Stack<Card> deck;

    public HiddenDeck(CardButton topCardButton)
    {
        this.topCardButton = topCardButton;        
        this.deck  = new Stack<>();
                
        int id = 1;
        
        for(int i = 0; i < 4; ++i)
            deck.push(new WildCard(id));
        
        ++id;

        for(int i = 0; i < 4; ++i)
            deck.push(new WildDrawCard(id));
        
        char[] colors = {'r','g','b','y'};
       
        for (char i : colors)
        {
            deck.push(new NumberCard(++id,'0',i)); //Ajoute 1 carte 0
            for(int j = 0; j < 2 ; j++) //Ajoute la même carte 2x
            {
                for(char k = '1'; k < '9'+1; k++)
                    deck.push(new NumberCard(++id,k,i)); //Ajoute une carte de chaque chiffre (hors 0)
                
                deck.push(new DrawCard(++id, i));
                deck.push(new ReverseCard(++id, i));
                deck.push(new SkipCard(++id, i));
                
                if(j == 0)
                   id-=12;
            }
        }   
        setDeckShuffle(deck);
    }
    
    public Stack<Card> shuffle(Stack<Card> deck)
    {
        Collections.shuffle(deck);
        return deck;
    }

    public void setDeckShuffle(Stack<Card> deck)
    {
        this.deck = shuffle(deck);
        
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
            setVisible(false);
                
        return top;
    }

    private void setVisible(boolean isVisible)
    {
        topCardButton.setVisible(isVisible);
    }
}
