package gui;

import actions.MouseHandler;
import actions.MouseMotionHandler;
import application.Client;
import application.Main;
import application.Server;
import game.*;

import javax.swing.*;
import java.awt.*;

public class Gui {
    JFrame jf;
    Draw draw;
    public static JTextField ipInput, portInput;
    public static JButton changeColor;
    public static int width = 1024, height = 768;
    public static int fieldx = 0, fieldy = 0, fieldsize = Math.min(1024, 768);

    public void create() {
        jf = new JFrame("Schach");
        jf.setSize(width, height);
        jf.setLocationRelativeTo(null);
        jf.setLayout(null);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setResizable(false);

        ipInput = new JTextField("127.0.0.1");
        ipInput.setBounds(10, 415, 100, 20);
        ipInput.setVisible(false);

        portInput = new JTextField("5000");
        portInput.setBounds(10, 473, 100, 20);
        portInput.setVisible(false);

        changeColor = new JButton("<=>");
        changeColor.setBounds(100,560,35,20);
        changeColor.setFocusPainted(false);
        changeColor.setBackground(new Color(233, 203, 168));
        changeColor.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        changeColor.addActionListener(e -> {
            Board.serverWhite = !Board.serverWhite;
            Board.refreshMoves();  // Refresh the board to reflect the change in color
        });
        changeColor.setVisible(false);

        draw = new Draw();
        draw.setBounds(0, 0, width, height);
        draw.setVisible(true);
        draw.addMouseListener(new MouseHandler());
        draw.addMouseMotionListener(new MouseMotionHandler());
        draw.add(ipInput);
        draw.add(portInput);
        draw.add(changeColor);
        jf.add(draw);

        jf.setVisible(true);
    }
}
