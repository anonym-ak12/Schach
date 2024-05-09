package application;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import game.Board;
import gui.Gui;
import gui.IL;

public class Main {
    public static boolean multRunning = false, isServer = true;
    public static String serverIp = "127.0.0.4"; // Hardcodierte IP
    public static int port = 5000;
    public static boolean connected = false, waiting = false;

    public static void main(String[] args) {
        IL.load();
        Gui g = new Gui();
        SwingUtilities.invokeLater(() -> showStartupDialog(g));
    }

    private static void showStartupDialog(Gui gui) {
        Object[] options = {"Offline spielen", "Online spielen"};
        int choice = JOptionPane.showOptionDialog(null, "Wählen Sie Ihren Spielmodus:",
                "Startup", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, options, options[0]);

        switch (choice) {
            case 0: // Offline spielen
                startOffline(gui);
                break;
            case 1: // Online spielen
                showNetworkDialog(gui);
                break;
            default:
                System.exit(0); // Beenden, falls Dialog geschlossen wird
        }
    }

    private static void showNetworkDialog(Gui gui) {
        Object[] networkOptions = {"Host ein Spiel", "Trete einem Spiel bei"};
        int networkChoice = JOptionPane.showOptionDialog(null, "Online-Spieloptionen:",
                "Netzwerkmodus", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, networkOptions, networkOptions[0]);

        switch (networkChoice) {
            case 0: // Host ein Spiel
                configureNetworkSettings(true, gui);
                break;
            case 1: // Trete einem Spiel bei
                configureNetworkSettings(false, gui);
                break;
            default:
                System.exit(0); // Beenden, falls Dialog geschlossen wird
        }
    }

    private static void startOffline(Gui gui) {
        Main.multRunning = false;
        gui.create();
        Board.reset();
        Board.refreshMoves();
    }

    private static void configureNetworkSettings(boolean isServer, Gui gui) {
        Main.isServer = isServer;
        JTextField portField = new JTextField(String.valueOf(Main.port));

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Port:"));
        panel.add(portField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Netzwerkeinstellungen",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            try {
                Main.port = Integer.parseInt(portField.getText());
                startNetwork(gui);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Bitte geben Sie eine gültige Portnummer ein.");
            }
        }
    }

    private static void startNetwork(Gui gui) {
        Main.multRunning = true;
        gui.create();
        Board.reset();
        Board.refreshMoves();
        if (Main.isServer) {
            Server server = new Server(Main.port);
        } else {
            Client client = new Client(Main.serverIp, Main.port);
        }
    }
}