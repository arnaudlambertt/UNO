/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uno.project;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;

/**
 *
 * @author Arnaud
 * @param <E>
 */
public final class ArrayListWithPanel<E> extends ArrayList<CardButton>
{

    private JPanel panel;
    private int panelId;
    private final ArrayList<BufferedImage[]> cardImages;
    private final String playerName;

    public ArrayListWithPanel(ArrayList<BufferedImage[]> cardImages, String playerName)
    {
        super();
        this.cardImages = cardImages;
        this.playerName = playerName;
    }

    public JPanel getPanel()
    {
        if (!SwingUtilities.isEventDispatchThread())
        {
            try
            {
                SwingUtilities.invokeAndWait(() ->
                {
                    panel.removeAll();
                });
            } 
            catch (InterruptedException | InvocationTargetException ex)
            {

            }
        }
        else
            panel.removeAll();

        return panel;
    }

    public int getPanelId()
    {
        return panelId;
    }

    public void setPanel(JPanel panel)
    {
        this.panel = panel;
        ((TitledBorder) panel.getBorder()).setTitle(playerName);
    }

    public void setPanelId(int panelId)
    {
        this.panelId = panelId;
    }

    public void refreshPanel(JPanel panel, int panelId, Game g)
    {
        setPanel(panel);
        setPanelId(panelId);

        allCards().forEach((c) ->
        {
            add(c, g);
        });
    }

    public ArrayList<Card> allCards()
    {
        ArrayList<Card> ar = new ArrayList<>();
        this.forEach((cb) ->
        {
            ar.add(cb.getCard());
        });
        removeAll(this);

        return ar;
    }

    public void add(Card card, Game g)
    {
        CardButton b = new CardButton(cardImages, panelId, card, g);
        b.setDefinitiveDisable(true);

        if (panelId % 2 == 1)//player 1 3
        {
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridwidth = GridBagConstraints.NONE;
            panel.add(b, gbc);
        } else
            panel.add(b);

        super.add(b);
    }

    @Override
    public boolean remove(Object o)
    {
        panel.remove((Component) o);
        return super.remove(o);
    }

    public void setColorRed(boolean red)
    {
        if (red)
            ((TitledBorder) panel.getBorder()).setTitleColor(Color.RED);
        else
            ((TitledBorder) panel.getBorder()).setTitleColor(Color.BLACK);
    }

    public void hideBorder()
    {
        ((TitledBorder) panel.getBorder()).setTitle("");
    }
    
    @Override
    public void clear()
    {
        getPanel();
        super.clear();
    }

}
