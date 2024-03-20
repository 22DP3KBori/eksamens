package lv.rvt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {
    private static final long serialVersionUID = 1L;

    static final int width = 500;
    static final int height = 500;
    static final int unit_size = 20;
    static final int number_of_units = (width * height) / (unit_size * unit_size);

    final int x[] = new int[number_of_units];
    final int y[] = new int[number_of_units];

    int length = 5;
    int foodEaten;
    int foodX;
    int foodY;
    char direction = 'D';
    boolean running = false;
    Random random;
    Timer timer;

    GamePanel() {
        random = new Random();
        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(Color.white);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        play();
    }

    public void play() {
        addFood();
        running = true;
        timer = new Timer(80, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        draw(graphics);
    }

    public void draw(Graphics g) {
        if (running) {
            g.setColor(Color.red);
            g.fillOval(foodX, foodY, unit_size, unit_size);

            for (int i = 0; i < length; i++) {
                if (i == 0) {
                    g.setColor(Color.green);
                    g.fillRect(x[i], y[i], unit_size, unit_size);
                } else {
                    g.setColor(new Color(45, 180, 0));
                    g.fillRect(x[i], y[i], unit_size, unit_size);
                }
            }
        } else {
            gameOver(g);
        }
    }

    public void move() {
        for (int i = length; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        switch (direction) {
            case 'U':
                y[0] -= unit_size;
                break;
            case 'D':
                y[0] += unit_size;
                break;
            case 'L':
                x[0] -= unit_size;
                break;
            case 'R':
                x[0] += unit_size;
                break;
        }
    }

    public void addFood() {
        foodX = random.nextInt((int) (width / unit_size)) * unit_size;
        foodY = random.nextInt((int) (height / unit_size)) * unit_size;
    }

    public void checkFood() {
        if (x[0] == foodX && y[0] == foodY) {
            length++;
            foodEaten++;
            addFood();
        }
    }

    public void checkCollisions() {
        // Check if snake hits the borders
        if (x[0] < 0 || x[0] >= width || y[0] < 0 || y[0] >= height) {
            running = false;
        }

        // Check if snake hits itself
        for (int i = 1; i < length; i++) {
            if (x[0] == x[i] && y[0] == y[i]) {
                running = false;
                break;
            }
        }

        if (!running) {
            timer.stop();
        }
    }

    public void gameOver(Graphics g) {
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 40));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Game Over", (width - metrics.stringWidth("Game Over")) / 2, height / 2);
        g.drawString("Score: " + foodEaten, (width - metrics.stringWidth("Score: " + foodEaten)) / 2, height / 2 + 50);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            checkFood();
            checkCollisions();
        }
        repaint();
    }

    private class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (direction != 'R')
                        direction = 'L';
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != 'L')
                        direction = 'R';
                    break;
                case KeyEvent.VK_UP:
                    if (direction != 'D')
                        direction = 'U';
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != 'U')
                        direction = 'D';
                    break;
            }
        }
    }
}
