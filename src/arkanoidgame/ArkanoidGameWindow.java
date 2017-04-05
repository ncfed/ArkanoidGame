package arkanoidgame;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class ArkanoidGameWindow extends javax.swing.JFrame {

    public int width, height, rowsOfBricks, columnsOfBricks, tickrate = 40;
    public Platform platform;
    private Ball ball;
    public ArrayList<Brick> bricks;
    private int numOfLives = 3, bricksBroken = 0;
    public boolean run = false, pause = false;
    public long lastUpdate;
    private GameThread gameThread;
    private Font messageFont = new Font("Arial", Font.BOLD, 30);

    public ArkanoidGameWindow(int width, int height) {
        initComponents();
        setSize(width, height);
        setFocusable(true);
        setLocationRelativeTo(null);
        show();
        this.width = jPanel1.getWidth();
        this.height = jPanel1.getHeight();
        rowsOfBricks = 3;
        columnsOfBricks = 5;
        initGame();
        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent e) {
                platform.position.x = e.getX() - getWidth() / 2;
                repaint();
            }
        });
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE & !run) {
                    startGame();
                }
                if (e.getKeyCode() == KeyEvent.VK_SPACE & run) {
                    switchPause();
                }
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    run = false;
                    initGame();
                }
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Arkanoid");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 640, Short.MAX_VALUE)
        );

        jLabel1.setText("Lives:");

        jLabel2.setText("0");

        jLabel3.setText("Bricks:");

        jLabel4.setText("0");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addContainerGap(796, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void initGame() {
        platform = new Platform(this);
        ball = new Ball(this);
        numOfLives = 3;
        bricksBroken = 0;
        jLabel2.setText(String.valueOf(numOfLives));
        jLabel4.setText(bricksBroken + "/" + rowsOfBricks * columnsOfBricks);
        createBricks(rowsOfBricks, columnsOfBricks);
    }

    private void createBricks(int rows, int columns) {
        bricks = new ArrayList<Brick>();
        int interspace = 10;
        float wid = (((float) width - 10) / columns) - 10;
        float hei = 30;
        for (int x = 0; x < columns; x++) {
            for (int y = 0; y < rows; y++) {
                Brick brick = new Brick();
                brick.position.x = (int) (x * (wid + interspace) + interspace) - width / 2;
                brick.position.y = (int) (y * (hei + interspace) + interspace) - height / 2;
                brick.height = (int) hei;
                brick.width = (int) wid;
                bricks.add(brick);
            }
        }
    }
    
    public void startGame() {
        if (gameThread != null) {
            if (gameThread.isAlive()) {
                gameThread.interrupt();
            }
        }
        gameThread = new GameThread(this);
        gameThread.start();
    }

    public void switchPause() {
        pause = !pause;
    }

    public void gameOver(boolean win) {
        run = false;
        String gameOverMessage;
        if (win) {
            gameOverMessage = "Congrats! You win! ";
        } else {
            gameOverMessage = "Sorry, you lose! ";
        }
        gameOverMessage = gameOverMessage + bricksBroken + " out of " + rowsOfBricks * columnsOfBricks + " bricks broken.";
        JOptionPane.showMessageDialog(rootPane, gameOverMessage, "Game Over", JOptionPane.PLAIN_MESSAGE);
        initGame();
    }
    
    public void ballFlewOff() {
        numOfLives--;
        jLabel2.setText(String.valueOf(numOfLives));
        if (numOfLives <= 0) {
            gameOver(false);
        } else {
            ball.position = new Point(0, 0);
        }
    }

    public void brickBroken() {
        bricksBroken++;
        jLabel4.setText(bricksBroken + "/" + rowsOfBricks * columnsOfBricks);
    }  

    public void tick() {
        double deltatime = (System.nanoTime() - lastUpdate) / 1000000.0;
        ball.tick(deltatime);
        if (bricks.isEmpty()) {
            gameOver(true);
        }
        repaint();
    }

    public void paint(Graphics g) {
        Image offScrImg = createImage(getWidth(), getHeight());
        Graphics offScrGraph = offScrImg.getGraphics();
        super.paint(offScrGraph);
        offScrGraph.translate((getWidth() - width) / 2, (getHeight() - height) / 2);
        offScrGraph.translate(width / 2, height / 2);
        platform.draw(offScrGraph);
        ball.draw(offScrGraph);
        if (bricks != null) {
            for (int i = 0; i < bricks.size(); i++) {
                bricks.get(i).draw(offScrGraph);
            }
        }
        offScrGraph.setColor(Color.WHITE);
        offScrGraph.setFont(messageFont);
        String message = "";
        if (!run) {
            message = "HIT SPACE TO START!";
        } else if (pause) {
            message = "GAME PAUSED";
        }
        FontMetrics fm = offScrGraph.getFontMetrics();
        offScrGraph.drawString(message, -fm.stringWidth(message) / 2, fm.getHeight() * 4);
        g.drawImage(offScrImg, 0, 0, null);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
