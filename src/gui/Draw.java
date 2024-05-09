package gui;

import actions.Mouse;
import game.Board;
import game.Koenig;

import javax.swing.*;
import java.awt.*;

public class Draw extends JLabel {
    Point p;

    Font f = new Font("Arial", Font.PLAIN, 20);

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Schachbrett zeichnen
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if ((i + j) % 2 == 0) {
                    g.setColor(new Color(255, 230, 179)); // Weiße Kacheln
                } else {
                    g.setColor(new Color(134, 89, 45)); // Schwarze Kacheln
                }
                g.fillRect(j * 80, i * 80, 80, 80);
            }
        }

        // Beschriftung der Felder
        g.setColor(Color.BLACK);
        g.setFont(f);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                String label = String.valueOf((char) (65 + j)) + (8 - i);
                int labelWidth = g.getFontMetrics().stringWidth(label);
                int labelHeight = g.getFontMetrics().getHeight();
                int x = j * 80 + (80 - labelWidth) / 2;
                int y = i * 80 + (80 + labelHeight) / 2;
                g.drawString(label, x, y);
            }
        }

        // Figuren zeichnen
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (Board.board[i][j] != null) {
                    Point pos = Mouse.ptc(new Point(i, j));
                    g.drawImage(Board.board[i][j].getImg(), pos.x, pos.y, null);

                    if (Board.board[i][j].isActive()) {
                        g.setColor(Color.BLUE);
                        g.drawRect(pos.x + 1, pos.y + 1, 78, 78);
                    }
                }
            }
        }

        // Markierte Felder und mögliche Züge zeichnen
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (Board.board[i][j] != null && Board.board[i][j].isActive()) {
                    for (Point moves : Board.board[i][j].getMoves()) {
                        Point cmoves = Mouse.ptc(moves);

                        if (Board.board[moves.x][moves.y] != null) {
                            if (Board.board[moves.x][moves.y] instanceof Koenig) {
                                g.setColor(new Color(204, 51, 0));
                            } else {
                                g.setColor(new Color(255, 153, 0));
                            }
                        } else {
                            g.setColor(new Color(0, 179, 0));
                        }
                        g.fillOval(cmoves.x + 27, cmoves.y + 27, 25, 25);
                    }
                }
            }
        }

        // Letzte Züge markieren
        if (Board.lastFrom.x != -1 && Board.lastFrom.y != -1) {
            p = Mouse.ptc(Board.lastFrom);
            g.setColor(new Color(0, 179, 0));
            g.drawRect(p.x + 1, p.y + 1, 78, 78);
        }

        if (Board.lastTo.x != -1 && Board.lastTo.y != -1) {
            p = Mouse.ptc(Board.lastTo);
            g.setColor(new Color(0, 179, 0));
            g.drawRect(p.x + 1, p.y + 1, 78, 78);
        }

        // Gitterlinien zeichnen
        g.setColor(Color.GRAY);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                g.drawRect(j * 80, i * 80, 80, 80);
            }
        }

        // Spielende Nachricht zeichnen
        g.setFont(new Font("Arial", Font.BOLD, 50));
        if (Board.gameOver) {
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(0, Gui.height / 2 - 120, Gui.width, 160);
            g.setColor(new Color(255, 55, 0));
            String winnerMessage = Board.gewinnerWhite ? "Gewinner: Weiß" : "Gewinner: Schwarz";
            int width = g.getFontMetrics().stringWidth(winnerMessage);
            g.drawString(winnerMessage, Gui.width / 2 - width / 2, Gui.height / 2 - 20);
        }

        repaint();
    }
}
