package gui;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class IL {
    public static BufferedImage[] figurenW = new BufferedImage[6];
    public static BufferedImage[] figurenB = new BufferedImage[6];

    public static void load() {
        try {
            for (int i = 0; i < 6; i++) {
                figurenW[i] = ImageIO.read(new File("rsc/" + (i + 1) + "w.png"));
                figurenB[i] = ImageIO.read(new File("rsc/" + (i + 1) + "b.png"));
                if (figurenW[i] == null || figurenB[i] == null) {
                    System.out.println("Bild konnte nicht geladen werden: " + (i + 1));
                } else {
                    System.out.println("Bild erfolgreich geladen: " + (i + 1));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Fehler beim Laden der Bilder: " + e.getMessage());
        }
    }
    
}
