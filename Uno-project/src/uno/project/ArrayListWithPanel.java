/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uno.project;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author Arnaud
 * @param <E>
 */
public class ArrayListWithPanel<E> extends ArrayList<Object>
{
    private final JPanel panel;
    private final int panelId;
    private final ArrayList<BufferedImage[]> cardImages;
    //need player ptete
    
    public ArrayListWithPanel(JPanel panel, int panelId, ArrayList<BufferedImage[]> cardImages)
    {
        super();
        this.panel = panel;
        this.panelId = panelId;
        this.cardImages = cardImages;
    }

    @Deprecated
    @Override
    public boolean add(Object e)
    {
       throw new UnsupportedOperationException();
    }  
    
    public void add(Card card)
    {
        if(panelId%2 == 1)//player 1 3
        {
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridwidth = GridBagConstraints.NONE;
            
            panel.add(new CardButton(cardImages, panelId, card),gbc);
        }
        else
            panel.add(new CardButton(cardImages, panelId, card));
    }

    @Override
    public boolean remove(Object o)
    {
        panel.remove((Component) o);
        
        return super.remove(o);
    }
}
