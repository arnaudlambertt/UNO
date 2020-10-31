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
    private Stack<Card> deck=null;
    
    public RevealedDeck(Card firstCard)
    {
        deck.push(firstCard);
    }
}
