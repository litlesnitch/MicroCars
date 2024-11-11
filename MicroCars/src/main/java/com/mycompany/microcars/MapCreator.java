/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.microcars;

/**
 *
 * @author simon
 */

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.image.BufferedImage;

public class MapCreator extends JFrame {
    private final int[][] tilesMap = new int[20][36]; // Griglia 10x18 inizializzata con 'field' (0)
    private final BufferedImage[] tilesImage = new BufferedImage[16];
    private int selectedTile = 0; // Indice della tile selezionata

    public MapCreator() {
        setTitle("Map Creator");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Inizializza le immagini dei tiles
        try {
            tilesImage[0] = ImageIO.read(new File("data/img/tiles/Field.png"));
            tilesImage[1] = ImageIO.read(new File("data/img/tiles/Track.png"));
            tilesImage[2] = ImageIO.read(new File("data/img/tiles/TrackStart.png"));
            tilesImage[4] = ImageIO.read(new File("data/img/tiles/Corner2-4.png"));
            tilesImage[5] = ImageIO.read(new File("data/img/tiles/Corner1-3.png"));
            tilesImage[6] = ImageIO.read(new File("data/img/tiles/Corner4-2.png"));
            tilesImage[7] = ImageIO.read(new File("data/img/tiles/Corner3-1.png"));
            tilesImage[8] = ImageIO.read(new File("data/img/tiles/BorderUp.png"));
            tilesImage[9] = ImageIO.read(new File("data/img/tiles/BorderDown.png"));
            tilesImage[10] = ImageIO.read(new File("data/img/tiles/BorderLeft.png"));
            tilesImage[11] = ImageIO.read(new File("data/img/tiles/BorderRight.png"));
            tilesImage[12] = ImageIO.read(new File("data/img/tiles/CornerBorder2.png"));
            tilesImage[13] = ImageIO.read(new File("data/img/tiles/CornerBorder1.png"));
            tilesImage[14] = ImageIO.read(new File("data/img/tiles/CornerBorder3.png"));
            tilesImage[15] = ImageIO.read(new File("data/img/tiles/CornerBorder4.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Pannello per la griglia di tiles
        JPanel gridPanel = new JPanel(new GridLayout(10, 18));
        JButton[][] buttons = new JButton[10][18];

        // Inizializza i pulsanti della griglia
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 18; j++) {
                JButton tileButton = new JButton(new ImageIcon(tilesImage[0])); // Inizia con 'Field'
                int x = i;
                int y = j;
                tileButton.addActionListener(e -> {
                    tilesMap[x][y] = selectedTile;
                    tileButton.setIcon(new ImageIcon(tilesImage[selectedTile]));
                });
                buttons[i][j] = tileButton;
                gridPanel.add(tileButton);
            }
        }

        // Pannello di selezione dei tiles
        JPanel selectionPanel = new JPanel();
        for (int i = 0; i < tilesImage.length; i++) {
            if (tilesImage[i] != null) {
                JButton selectButton = new JButton(new ImageIcon(tilesImage[i]));
                int tileIndex = i;
                selectButton.addActionListener(e -> selectedTile = tileIndex);
                selectionPanel.add(selectButton);
            }
        }

        // Pulsante per salvare la mappa
        JButton saveButton = new JButton("Save Map");
        saveButton.addActionListener(e -> saveMapToCSV("data/tracks/customTrack.csv"));

        // Aggiungi pannelli al frame
        add(gridPanel, BorderLayout.CENTER);
        add(selectionPanel, BorderLayout.SOUTH);
        add(saveButton, BorderLayout.NORTH);

        setVisible(true);
    }

    // Metodo per salvare la mappa in un file CSV
private void saveMapToCSV(String filePath) {
    // Mapping tile indices to their corresponding labels
    String[] tileLabels = {
        "F",    // 0 - Field
        "T",    // 1 - Track
        "S",    // 2 - TrackStart
        "",     // 3 - Not used
        "F\\T", // 4
        "F/T",  // 5
        "T\\F", // 6
        "T/F",  // 7
        "B-F",  // 8
        "F-B",  // 9
        "B|F",  // 10
        "F|B",  // 11
        "B/F",  // 12
        "F\\B", // 13
        "B\\F", // 14
        "F/B"   // 15
    };

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
        // Header row with additional parameters
        writer.write(";");
        writer.write("PosX;600;PosY;100;CheckX;179;CheckY;86;");
        writer.newLine();

        // Row with column numbers
        writer.write(";");
        for (int j = 1; j <= 18; j++) {
            writer.write(j + ";");
        }
        writer.newLine();

        // Write the grid data with labels and row numbers
        for (int i = 0; i < 10; i++) {
            writer.write((i + 1) + ";"); // Row number on the left
            for (int j = 0; j < 18; j++) {
                int tileIndex = tilesMap[i][j];
                String tileLabel = tileIndex < tileLabels.length ? tileLabels[tileIndex] : "";
                writer.write(tileLabel + ";"); // Write the tile label
            }
            writer.write((i + 1) + ";"); // Row number on the right
            writer.newLine();
        }

        // Bottom row with column numbers
        writer.write(";");
        for (int j = 1; j <= 18; j++) {
            writer.write(j + ";");
        }
        writer.newLine();

        // Confirmation message
        JOptionPane.showMessageDialog(this, "Mappa salvata con successo!", "Salvataggio Completato", JOptionPane.INFORMATION_MESSAGE);
    } catch (IOException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Errore durante il salvataggio: " + e.getMessage(), "Errore di Salvataggio", JOptionPane.ERROR_MESSAGE);
    }
}



}
