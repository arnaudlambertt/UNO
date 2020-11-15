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
import java.util.Comparator;
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
    private ArrayList<Player> activePlayers;
    private HiddenDeck hiddenDeck;
    private RevealedDeck revealedDeck;
    private int playerIndex;
    private int currentTurnIndex;
    private int playerIterator;
    private int playerCount;
    private int botCount;
    private int activeBotCount;
    private boolean botTurn;
    private boolean ended;

    public Game()
    {
        super();
        this.ended = false;
    }

    public void init()
    {
        loadImages();
        loadSettings();
        JPanel[] panels = createPanels();
        createUsers();
        do
        {
            setRound(panels);
            play();
            repaint();
        } while (!ended);

        dispose();
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

    public void setDecks(JPanel[] panels)
    {
        if (hiddenDeck != null)
        {
            panels[4].remove(hiddenDeck.getTopCardButton());
            panels[4].remove(revealedDeck.getTopCardButton());
        }
        CardButton h = new CardButton(cardImages, -1, new NumberCard(0, '0', 'b'), this); //carte tempo
        hiddenDeck = new HiddenDeck(h);

        CardButton r = new CardButton(cardImages, -1, new NumberCard(0, '0', 'b'), this); //carte tempo
        revealedDeck = new RevealedDeck(r);

        panels[4].add(h);
        panels[4].add(r);

        repaint();
    }

    public void createUsers() //4 deck
    {

        this.players = new ArrayList<>();
        this.activePlayers = new ArrayList<>();
        this.playerCount = -1;
        do
        {
            try
            {
                Integer[] options =
                {
                    2, 3, 4
                };
                playerCount = JOptionPane.showOptionDialog(null, "How many players including bots?", "How many players",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            } catch (HeadlessException e)
            {
            }
        } while (playerCount < 0);

        this.playerCount += 2;

        this.botCount = -1;
        do
        {
            try
            {
                Integer[] options = new Integer[playerCount];
                for (int i = 0; i < playerCount; ++i)
                    options[i] = i;

                botCount = JOptionPane.showOptionDialog(null, "How many bots?", "How many bots",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            } catch (HeadlessException e)
            {
            }
        } while (botCount < 0);

        String name = "";
        int b = 0;
        for (int i = 0; i < playerCount; ++i)
            if (i < playerCount - botCount)
            {
                do
                {
                    try
                    {
                        name = JOptionPane.showInputDialog("Enter name for player " + (i + 1) + " :");
                    } catch (HeadlessException e)
                    {
                    }
                } while (name.equals(""));
                players.add(new Player(name, cardImages));
                name = "";
            } else
            {
                name = "Bot " + (++b);
                players.add(new Bot(name, cardImages));
            }
    }

    public void distribution()
    {
        this.playerIndex = activePlayers.size() - 1; //c'est le dernier joueur qui "joue" la premiere carte
        this.playerIterator = 1;

        for (int i = 0; i < 7; ++i)
            for (int j = 0; j < activePlayers.size(); ++j)
                activePlayers.get(j).addCard(hiddenDeck.getTopCard(), this);

        repaint();

        firstCard();

        botTurn = isBot();

        this.currentTurnIndex = playerIndex;

        if (!isBot())
            activePlayers.get(playerIndex).setRevealed(true);
        else
            botTurn = isBot();

    }

    public void play()
    {
        distribution();

        while (activeBotCount > 0)
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
                activePlayers.get(playerIndex).setRevealed(false);

                this.currentTurnIndex = playerIndex;
                ((Bot) activePlayers.get(playerIndex)).turn(this);
                playerIndexIncrementation(true);

                repaint();

                if (!ended && activePlayers.size() == 1)
                    endRound();

                hiddenDeck.setEnabled(true);
                botTurn = isBot();
                if (!botTurn)
                    activePlayers.get(playerIndex).setRevealed(true);
            }
        }

        while (!ended && activePlayers.size() > 1)
        {
            try
            {
                Thread.sleep(10);
            } catch (InterruptedException e)
            {
            }
        }

        if (activePlayers.size() == 1)
        {
            activePlayers.get(0).clear();
            activePlayers.remove(activePlayers.get(0));
        }
    }

    public void playerIndexIncrementation(boolean swap)
    {
        playerIndex += playerIterator;

        if (playerIndex >= activePlayers.size())
            playerIndex = 0;

        else if (playerIndex < 0)
            playerIndex = activePlayers.size() - 1;

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
            activePlayers.get(playerIndex).addCard(getHiddenTop(), this);
    }

    public void playCard(Card c)
    {
        revealedDeck.addCard(c);
    }

    public ArrayList<Player> getPlayers()
    {
        return activePlayers;
    }

    public void removePlayer()
    {
        repaint();
        int points = 0;

        points = activePlayers.stream().filter((p) -> (p != activePlayers.get(currentTurnIndex))).map((p) -> p.calculateScore()).reduce(points, Integer::sum);
        activePlayers.get(currentTurnIndex).addScore(points);

        if (!(getRevealedTop() instanceof SkipCard || getRevealedTop() instanceof WildDrawCard))
        {
            reverse();
            shift();
            reverse();
        }

        activePlayers.get(currentTurnIndex).hideBorder();
        activePlayers.remove(currentTurnIndex);
        if (playerIterator > 0)
        {
            reverse();
            playerIndexIncrementation(false);
            reverse();
        }

        if (isEnded())
        {
            activeBotCount = 0;
            ended = true;
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

    public void endRound()
    {
        activePlayers.get(0).setRevealed(true);
        JOptionPane.showMessageDialog(null, "Unfortunately, " + activePlayers.get(0).getName() + " lost the round.");
        activeBotCount = 0;
    }

    public void shift()
    {
        JPanel tempPanel;
        int tempPanelId;

        if (playerIterator < 0)
        {
            tempPanel = activePlayers.get(0).getPanel();
            tempPanelId = activePlayers.get(0).getPanelId();

            for (int i = 0; i < activePlayers.size() - 1; ++i)
                activePlayers.get(i).refreshPanel(activePlayers.get(i + 1).getPanel(), activePlayers.get(i + 1).getPanelId(), this);
            activePlayers.get(activePlayers.size() - 1).refreshPanel(tempPanel, tempPanelId, this);
        } else
        {
            tempPanel = activePlayers.get(activePlayers.size() - 1).getPanel();
            tempPanelId = activePlayers.get(activePlayers.size() - 1).getPanelId();

            for (int i = activePlayers.size() - 1; i > 0; --i)
                activePlayers.get(i).refreshPanel(activePlayers.get(i - 1).getPanel(), activePlayers.get(i - 1).getPanelId(), this);
            activePlayers.get(0).refreshPanel(tempPanel, tempPanelId, this);
        }
        setNextPlayerRed();
    }

    public void setNextPlayerRed()
    {
        playerIndexIncrementation(false);

        activePlayers.forEach((p) ->
        {
            p.setColorRed(false);
        });
        activePlayers.get(playerIndex).setColorRed(true);

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
                activePlayers.get(playerIndex).setEnabled(false);
                revealHiddenTop();
            } else if (activePlayers.get(playerIndex).tryMove(e, this))
            {
                playerIndexIncrementation(true);
                if (!(activePlayers.get(playerIndex) instanceof Bot))
                    activePlayers.get(playerIndex).setRevealed(true);

                repaint();

                this.currentTurnIndex = playerIndex;

                if (!ended && activePlayers.size() == 1)
                {
                    endRound();
                    activePlayers.get(0).clear();
                    activePlayers.remove(activePlayers.get(0));
                } else
                    botTurn = isBot();
            }
        }
    }

    public boolean isBot()
    {
        return activePlayers.get(playerIndex) instanceof Bot;
    }

    public void removeBot()
    {
        this.activeBotCount--;
    }

    public boolean isEnded()
    {
        for (Player p : players)
            if (p.getScore() >= 500)
            {
                players.sort(Comparator.comparing(Player::getScore).reversed());
                String str = "";
                for(int i = 0; i < players.size(); ++i)
                {
                    str+="\n" + (i+1) + ". " + players.get(i).getName() + " " + players.get(i).getScore() + "pts";
                }
                JOptionPane.showMessageDialog(null, "Congratulations " + p.getName() + "!\n You won the game with " + p.getScore() + " points !"
                + str);

                    
                
                return true;
            }
        return false;
    }

    public void setRound(JPanel[] panels)
    {
        setDecks(panels);
        for (int i = 0; i < playerCount; ++i)
        {
            activePlayers.add(players.get(i));
            int panelId = (playerCount == 2 && i == 1 ? 2 : i);
            activePlayers.get(i).setPanel(panels[panelId]);
            activePlayers.get(i).setPanelId(panelId);
        }

        this.activeBotCount = botCount;
    }

    public ArrayList<Player> getActivePlayers()
    {
        return activePlayers;
    }
}
