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
              
        //hiddenStack.push(new NumberCard(++id,'0','b'));
        //hiddenStack.push(new NumberCard(++id,'1','b'));
        
//                Stack<Card> newDeck = new Stack<>();
//        char color='/';
//        
//        for (int i = 0; i < 4; i++)
//        {
//            switch(i)
//            {
//                case 0 : color='r'; break;
//                case 1 : color='b'; break;
//                case 2 : color='j'; break;
//                case 3 : color='v'; break;
//            }
//            
//            newDeck.push(new NumberCard('0', color)); //Ajoute 1 carte 0
//            for(int j=0;j<2;j++) //Ajoute la même carte 2x
//            {
//                for (int k = 1; k < 10; k++)
//                    newDeck.push(new NumberCard(,Integer.toString(k).charAt(0), color)); //Ajoute une carte de chaque chiffre (hors 0)
//            }
//        }
//        
//        deck = newDeck;
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
