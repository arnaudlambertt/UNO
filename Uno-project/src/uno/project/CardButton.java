/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uno.project;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 *
 * @author Arnaud
 */
final public class CardButton extends JButton
{

    private final ArrayList<BufferedImage[]> cardImages;
    private final int panelId;
    private Card card;
    private ImageIcon front;
    private final ImageIcon back;

    public CardButton(ArrayList<BufferedImage[]> cardImages, int panelId, Card card)
    {
        super();
        this.cardImages = cardImages;
        this.panelId = panelId;
        this.card = card;

        if (panelId > 0)
        {
            BufferedImage buffer = new BufferedImage(panelId % 2 == 1 ? 138 : 90, panelId % 2 == 1 ? 90 : 138, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = buffer.createGraphics();          //2w 3w 1h            //2h 3w 1h
            g2d.rotate(Math.toRadians(panelId * 90.0), panelId > 1 ? 90 / 2 : 138 / 2, panelId < 3 ? 138 / 2 : 90 / 2);
            g2d.drawImage(cardImages.get(card.getId())[0], 0, 0, null);
            g2d.dispose();
            this.front = new ImageIcon(buffer);

            buffer = new BufferedImage(panelId % 2 == 1 ? 138 : 90, panelId % 2 == 1 ? 90 : 138, BufferedImage.TYPE_INT_RGB);
            g2d = buffer.createGraphics();
            g2d.rotate(Math.toRadians(panelId * 90.0), panelId > 1 ? 90 / 2 : 138 / 2, panelId < 3 ? 138 / 2 : 90 / 2);
            g2d.drawImage(cardImages.get(0)[0], 0, 0, null);
            g2d.dispose();
            this.back = new ImageIcon(buffer);
        } else //-1 = big
        {
            this.front = new ImageIcon(cardImages.get(card.getId())[0 - panelId]);
            this.back = new ImageIcon(cardImages.get(0)[0 - panelId]);
        }
        setPreferredSize(new Dimension(back.getIconWidth(), back.getIconHeight()));
        setRevealed(false);
    }

    public Card getCard()
    {
        return card;
    }

    public void setCard(Card card)//
    {
        this.card = card;

        if (card != null)
        {
            if (panelId > 0)
            {
                BufferedImage buffer = new BufferedImage(panelId % 2 == 1 ? 138 : 90, panelId % 2 == 1 ? 90 : 138, BufferedImage.TYPE_INT_RGB);
                Graphics2D g2d = buffer.createGraphics();          //2w 3w 1h            //2h 3w 1h
                g2d.rotate(Math.toRadians(panelId * 90.0), panelId > 1 ? 90 / 2 : 138 / 2, panelId < 3 ? 138 / 2 : 90 / 2);
                g2d.drawImage(cardImages.get(card.getId())[0], 0, 0, null);
                g2d.dispose();
                this.front = new ImageIcon(buffer);
            } 
            else //-1 = big
                this.front = new ImageIcon(cardImages.get(card.getId())[0 - panelId]);

            setRevealed(false);
        }
    }

    public void setRevealed(boolean isRevealed)
    {
        if (isRevealed)
        {
            setIcon(front);
            setDisabledIcon(front);
        }
        else
        {
            setIcon(back);
            setDisabledIcon(back);
        }
    }

    public boolean isClicked()
    {
        if(getModel().isPressed())
        {
            setDefinitiveDisable(true);
            setDefinitiveDisable(false);
            return true;
        }
        
        return false;
    }

    void setDefinitiveDisable(boolean isDisabled)
    {
        getModel().setEnabled(!isDisabled);
    }

}
