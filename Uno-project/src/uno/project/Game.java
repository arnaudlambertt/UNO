/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uno.project;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

/**
 *
 * @author Utilisateur
 */
public final class Game extends JFrame implements ActionListener
{

    private ArrayList<BufferedImage[]> cardImages;
    private ArrayList<Player> players;
    private HiddenDeck hiddenDeck;
    private RevealedDeck revealedDeck;
    private int playerIndex;
    private int currentTurnIndex;
    private int playerIterator;
    private int playerCount;
    private int nbBot;
    private boolean botTurn;

    public Game()
    {
        super();
        init();
    }

    public void init()
    {
        loadImages();
        loadSettings();
        JPanel[] panels = createPanels();
        createUsers(panels);
        createDecks(panels);
        play();
    }

    public void loadImages()
    {
        JDialog jf = new JDialog();
        JProgressBar pb = new JProgressBar(0, 55);
        JLabel jl = new JLabel("Loading images");

        int p = 0;

        pb.setBounds(0, 0, 160, 30);
        pb.setValue(0);
        pb.setStringPainted(true);
        jf.setSize(200, 60);
        jf.setLayout(new GridBagLayout());
        jf.setUndecorated(true);
        jf.setLocationRelativeTo(null);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.NONE;
        jf.add(jl, gbc);
        jf.add(pb, gbc);

        jf.setVisible(true);

        cardImages = new ArrayList<>();

        cardImages.add(loadImage("sprites/deck.png"));
        pb.setValue(++p);
        cardImages.add(loadImage("sprites/wildCard.png"));
        pb.setValue(++p);
        cardImages.add(loadImage("sprites/wildDrawCard.png"));
        pb.setValue(++p);

        char[] letters =
        {
            'r', 'g', 'b', 'y'
        };

        for (char i : letters)
        {
            for (int j = 0; j < 10; ++j)
            {
                cardImages.add(loadImage("sprites/" + i + j + ".png"));
                pb.setValue(++p);
            }

            cardImages.add(loadImage("sprites/" + i + 'd' + ".png"));
            pb.setValue(++p);
            cardImages.add(loadImage("sprites/" + i + 'r' + ".png"));
            pb.setValue(++p);
            cardImages.add(loadImage("sprites/" + i + 's' + ".png"));
            pb.setValue(++p);
        }

        jf.dispose();
    }

    public BufferedImage[] loadImage(String source)
    {
        BufferedImage bi[] = new BufferedImage[2];
        try
        {
            Image image = ImageIO.read(new File(source));
            bi[0] = new BufferedImage(90, 138, BufferedImage.TYPE_INT_RGB);
            bi[0].getGraphics().drawImage(image.getScaledInstance(90, 138, java.awt.Image.SCALE_SMOOTH), 0, 0, null);

            bi[1] = new BufferedImage(180, 276, BufferedImage.TYPE_INT_RGB);
            bi[1].getGraphics().drawImage(image.getScaledInstance(180, 276, java.awt.Image.SCALE_SMOOTH), 0, 0, null);
        } catch (IOException e)
        {
            System.out.println("Cannot load " + source);
        }
        return bi;
    }

    public void loadSettings()
    {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Uno!");
        setSize(1600, 900);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void repaint()
    {
        revalidate();
        super.repaint();
    }

    public JPanel[] createPanels()
    {
        JPanel[] panels =
        {
            new JPanel(new GridBagLayout()),
            new JPanel(new GridBagLayout()),
            new JPanel(new GridBagLayout()),
            new JPanel(new GridBagLayout()),
            new JPanel(new GridBagLayout())
        };

        panels[0].setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.LIGHT_GRAY, 0), null, TitledBorder.CENTER, TitledBorder.ABOVE_TOP, new Font("Didot", Font.PLAIN, 16)));
        panels[1].setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.LIGHT_GRAY, 0), null, TitledBorder.CENTER, TitledBorder.ABOVE_TOP, new Font("Didot", Font.PLAIN, 16)));
        panels[2].setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.LIGHT_GRAY, 0), null, TitledBorder.CENTER, TitledBorder.BELOW_BOTTOM, new Font("Didot", Font.PLAIN, 16)));
        panels[3].setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.LIGHT_GRAY, 0), null, TitledBorder.CENTER, TitledBorder.ABOVE_TOP, new Font("Didot", Font.PLAIN, 16)));

        JPanel centralPanel = new JPanel();

        JScrollPane[] scrollPanes =
        {
            new JScrollPane(panels[0], ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER),
            new JScrollPane(panels[1], ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER),
            new JScrollPane(panels[2], ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER),
            new JScrollPane(panels[3], ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER)
        };

        scrollPanes[0].setHorizontalScrollBar(new JScrollBar(JScrollBar.HORIZONTAL)
        {
            @Override
            public boolean isVisible()
            {
                return true;
            }
        });
        scrollPanes[1].setVerticalScrollBar(new JScrollBar(JScrollBar.VERTICAL)
        {
            @Override
            public boolean isVisible()
            {
                return true;
            }
        });
        scrollPanes[2].setHorizontalScrollBar(new JScrollBar(JScrollBar.HORIZONTAL)
        {
            @Override
            public boolean isVisible()
            {
                return true;
            }
        });
        scrollPanes[3].setVerticalScrollBar(new JScrollBar(JScrollBar.VERTICAL)
        {
            @Override
            public boolean isVisible()
            {
                return true;
            }
        });

        for (JScrollPane i : scrollPanes)
        {
            i.getHorizontalScrollBar().setUnitIncrement(30);
            i.getVerticalScrollBar().setUnitIncrement(30);
            i.setBorder(BorderFactory.createEmptyBorder());
        }

        centralPanel.setLayout(new BoxLayout(centralPanel, BoxLayout.Y_AXIS));

        centralPanel.add(scrollPanes[2]);
        centralPanel.add(panels[4]);
        centralPanel.add(scrollPanes[0]);

        add(scrollPanes[1], BorderLayout.WEST);
        add(scrollPanes[3], BorderLayout.EAST);
        add(centralPanel, BorderLayout.CENTER);
        repaint();

        return panels;
    }

    public void createDecks(JPanel[] panels)
    {
        int id = 0;
        CardButton h = new CardButton(cardImages, -1, new NumberCard(0, '0', 'b'), this); //carte tempo
        hiddenDeck = new HiddenDeck(h);

        CardButton r = new CardButton(cardImages, -1, new NumberCard(0, '0', 'b'), this); //carte tempo
        revealedDeck = new RevealedDeck(r);

        panels[4].add(h);
        panels[4].add(r);

        repaint();
    }

    public void createUsers(JPanel[] panels) //4 deck
    {

        playerCount = 0;
        do
        {
            try
            {
                playerCount = Integer.max(2, Integer.min(4, Integer.parseInt(JOptionPane.showInputDialog("Enter player count (2 to 4) :"))));
            } catch (HeadlessException | NumberFormatException e)
            {
            }
        } while (playerCount < 2);
        players = new ArrayList<>();

        nbBot = -1;
        do
        {
            try
            {
                nbBot = Integer.max(0, Integer.min(playerCount - 1, Integer.parseInt(JOptionPane.showInputDialog("How many bot(s) (max " + (playerCount - 1) + ") :"))));
            } catch (HeadlessException | NumberFormatException e)
            {
            }
        } while (nbBot < 0);

        String name;
        int botCount = 0;
        for (int i = 0; i < playerCount; ++i)
            if (i < playerCount - nbBot)
            {
                name = JOptionPane.showInputDialog("Enter name for player " + (i + 1) + " :");
                int panelId = (playerCount == 2 && i == 1 ? 2 : i);
                players.add(new Player(name, panels[panelId], panelId, cardImages));
            } else
            {
                name = "Bot " + (++botCount);
                int panelId = (playerCount == 2 && i == 1 ? 2 : i);
                players.add(new Bot(name, panels[panelId], panelId, cardImages));
            }
    }

    public void distribution()
    {
        this.playerIndex = players.size() - 1; //c'est le dernier joueur qui "joue" la premiere carte
        this.playerIterator = 1;

        for (int i = 0; i < 7; ++i)
            for (int j = 0; j < players.size(); ++j)
                players.get(j).addCard(hiddenDeck.getTopCard(), this);

        repaint();

        firstCard();
        
        botTurn = isBot();
        
        this.currentTurnIndex = playerIndex;

        if (!isBot())
            players.get(playerIndex).setRevealed(true);
        else
            botTurn = isBot();
        
    }

    public void play()
    {
        distribution();
        
        while (nbBot > 0)
        {
            try
            {
                Thread.sleep(10);
            } catch (InterruptedException e)
            {
            }

            if (botTurn)
            {
                currentTurnIndex = playerIndex;
                hiddenDeck.setEnabled(false);
                players.get(playerIndex).setRevealed(false);

                this.currentTurnIndex = playerIndex;
                ((Bot) players.get(playerIndex)).turn(this);
                playerIndexIncrementation(true);

                repaint();

                if (players.size() < 2)
                    end();

                hiddenDeck.setEnabled(true);
                botTurn = isBot();
                if(!botTurn)
                    players.get(playerIndex).setRevealed(true);
            }
        }
    }

    public void playerIndexIncrementation(boolean swap)
    {
        playerIndex += playerIterator;

        if (playerIndex >= players.size())
            playerIndex = 0;

        else if (playerIndex < 0)
            playerIndex = players.size() - 1;

        if (hiddenDeck.isEmpty() && !revealedDeck.isEmpty())
            hiddenDeck.setDeckShuffle(revealedDeck.getDeck());

        if (swap)
            shift();
    }

    public void reverse()
    {
        playerIterator = -playerIterator;
    }

    public Card getHiddenTop()
    {
        if (hiddenDeck.isEmpty())
            if (!revealedDeck.isEmpty())
                hiddenDeck.setDeckShuffle(revealedDeck.getDeck());
            else
                return null;

        return hiddenDeck.getTopCard();
    }

    public Card getRevealedTop()
    {
        return revealedDeck.getTopCard();
    }

    public Card revealHiddenTop()
    {
        hiddenDeck.reveal();
        repaint();
        return hiddenDeck.peek();
    }

    public void playerDraw(int amount)
    {
        for (int i = 0; i < amount; ++i)
            players.get(playerIndex).addCard(getHiddenTop(), this);
    }

    public void playCard(Card c)
    {
        revealedDeck.addCard(c);
    }

    public ArrayList<Player> getPlayers()
    {
        return players;
    }

    public void removePlayer()
    {
        repaint();
        if (playerCount == players.size())
            JOptionPane.showMessageDialog(null, players.get(currentTurnIndex).getName() + " won !");

        if (!(getRevealedTop() instanceof SkipCard || getRevealedTop() instanceof WildDrawCard))
        {
            reverse();
            shift();
            reverse();
        }

        players.get(currentTurnIndex).hideBorder();
        players.remove(currentTurnIndex);
        if (playerIterator > 0)
        {
            reverse();
            playerIndexIncrementation(false);
            reverse();
        }
    }

    public void firstCard()
    {
        hiddenDeck.firstCard(this);
        if (playerIterator > 0) //si pas de reverse alors incrémentation dernierjoueur => premier joueur
        {
            playerIndexIncrementation(false);
            setNextPlayerRed();
        } else // sinon dernier joueur commence
            shift();
    }

    public void end()
    {
        players.get(0).setRevealed(true);
        JOptionPane.showMessageDialog(null, "Unfortunately, " + players.get(0).getName() + " lost.\nThank you for playing Uno!");
        nbBot = 0;
        dispose();
    }

    public void shift()
    {
        JPanel tempPanel;
        int tempPanelId;

        if (playerIterator < 0)
        {
            tempPanel = players.get(0).getPanel();
            tempPanelId = players.get(0).getPanelId();

            for (int i = 0; i < players.size() - 1; ++i)
                players.get(i).refreshPanel(players.get(i + 1).getPanel(), players.get(i + 1).getPanelId(), this);
            players.get(players.size() - 1).refreshPanel(tempPanel, tempPanelId, this);
        } else
        {
            tempPanel = players.get(players.size() - 1).getPanel();
            tempPanelId = players.get(players.size() - 1).getPanelId();

            for (int i = players.size() - 1; i > 0; --i)
                players.get(i).refreshPanel(players.get(i - 1).getPanel(), players.get(i - 1).getPanelId(), this);
            players.get(0).refreshPanel(tempPanel, tempPanelId, this);
        }
        setNextPlayerRed();
    }

    public void setNextPlayerRed()
    {
        playerIndexIncrementation(false);

        players.forEach((p) ->
        {
            p.setColorRed(false);
        });
        players.get(playerIndex).setColorRed(true);

        reverse();
        playerIndexIncrementation(false);
        reverse();

        repaint();
    }

    public HiddenDeck getHiddenDeck()
    {
        return hiddenDeck;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (!isBot())
        {
            currentTurnIndex = playerIndex;

            if (((CardButton) e.getSource()) == hiddenDeck.getTopCardButton() && !hiddenDeck.isRevealed())
            {
                players.get(playerIndex).setEnabled(false);
                revealHiddenTop();
            } 
            else if (players.get(playerIndex).tryMove(e, this))
            {
                playerIndexIncrementation(true);
                if (!(players.get(playerIndex) instanceof Bot))
                    players.get(playerIndex).setRevealed(true);

                repaint();

                this.currentTurnIndex = playerIndex;

                if (players.size() < 2)
                    end();

                botTurn = isBot();
            }
        }
    }

    public boolean isBot()
    {
        return players.get(playerIndex) instanceof Bot;
    }

    public void removeBot()
    {
        this.nbBot--;
    }
}
