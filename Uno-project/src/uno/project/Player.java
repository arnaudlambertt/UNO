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
public class Player
{
    private final String name;
    private final int number;
    private final ArrayList<Card> cards;
    
    public Player(String name, int number)
    {
        this.name = name;
        this.number = number;
        this.cards = new ArrayList<>();
    }
}
