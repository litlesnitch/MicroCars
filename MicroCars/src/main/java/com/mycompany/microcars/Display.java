package com.mycompany.microcars;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Classe che gestisce l'immagine del cruscotto (ovvero un piccolo Display) da riportare a schermo.
 * 
 * <h2>Descrizione</h2>
 * Il display del cruscotto riporta i dati principali della corsa (es. <strong>integrity</strong> e <strong>lap</strong>)<br>
 * Utilizzato dall'<a href="MainGamePanel.html">pannello di gioco</a>.
 * 
 * <br><strong>NB</strong>: viene utilizzata un'immagine separata <strong>(this.dImg)</strong> per il display perché<br>
 * la funzione <strong>paint</strong> non può essere richiamata direttamente dal ciclo di gioco
 * 
 * @author Giulio Frandi
 * @version 1.0
 */
public class Display {

    private BufferedImage dImg;

    /**
     * Costruttore che inizializza l'immagine del display con integrità e lap
     * 
     * @param integrity int integrità iniziale della macchina
     * @param lap int giro iniziale
     */
    public Display(int integrity, int lap) {
        dImg = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
        updateDisplay(integrity, lap);
    }

    /**
     * Metodo per aggiornare il display con nuovi valori di integrità e lap.<br>
     * Viene visualizzata anche una barra dinamica che cambia colore in base all'integrità
     * 
     * @param integrity int integrità corrente della macchina (0-100)
     * @param lap int giro corrente
     */
    public void updateDisplay(int integrity, int lap) {
        Graphics2D g = dImg.createGraphics();

        // Riempie lo sfondo di nero
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 100, 100);

        // Determina il colore della barra in base al livello di integrità
        if (integrity >= 75) {
            g.setColor(Color.GREEN);
        } else if (integrity >= 45) {
            g.setColor(Color.ORANGE);
        } else {
            g.setColor(Color.RED);
        }

        // Calcola e disegna la barra verticale in base al livello di integrità
        int barHeight = (int) (integrity * 0.8);  // Altezza scalata per l'area di visualizzazione
        g.fillRect(80, 100 - barHeight, 10, barHeight);

        // Disegna il testo per l'integrità e il giro
        g.setColor(Color.WHITE);
        g.drawString("Integrity: " + integrity, 10, 20);
        if (lap >= 0) {
            g.drawString("Lap: " + lap, 10, 35);
        }

        // Rilascia il contesto grafico
        g.dispose();
    }

    /**
     * Metodo getter per l'immagine del display
     * 
     * @return BufferedImage con l'ultima immagine del display aggiornata
     */
    public BufferedImage getImage() {
        return dImg;
    }

    /**
     * Metodo per disegnare l'immagine del display (<strong>this.dImg</strong>)<br>
     * 
     * @param g Graphics proveniente dal contesto ove disegnare il display
     */
    public void paint(Graphics g) {
        g.drawImage(dImg, 0, 0, null);
    }
}
