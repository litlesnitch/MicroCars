package com.mycompany.microcars;


import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.util.ArrayList;
import java.util.logging.*;
import javax.swing.*;

/**
 * <h2>Pannello per la selezione delle opzioni di gioco</h2>
 * 
 * Estende JPanel e implementa ActionListener.
 * 
 * @author Giulio Frandi
 * @version 1.0<br>
 */
public class BasicOptionsChoicePanel extends JPanel implements ActionListener {

    private JFrame parentFrame; // Riferimento al frame principale
    private String choosenCar; // Macchina scelta dall'utente
    private String choosenTrack; // Percorso scelto dall'utente
    private AlertMessage m; // Messaggio per trasferire le scelte

    private ArrayList<String> cars = new ArrayList<>(); // Lista di macchine
    private int currentCarIndex = 0; // Indice della macchina corrente
    private ArrayList<String> maps = new ArrayList<>(); // Lista dei circuiti
    private int currentMapIndex = 0; // Indice del circuito corrente

    private JLabel carLabel1, carLabel2, mapLabel; // Etichette per le macchine e il circuito
    private JButton leftArrowButton, rightArrowButton, leftArrowButton2, rightArrowButton2,createTrack;// Pulsanti per cambiare macchina e circuito

    public BasicOptionsChoicePanel(AlertMessage m, JFrame parentFrame) {
        this.m = m;
        this.parentFrame = parentFrame;

        // Aggiungi macchine e circuiti all'ArrayList
        cars.add("yellow");
        cars.add("red");
        cars.add("green");
        cars.add("cyan");
        maps.add("basic");
        maps.add("eight");
        maps.add("nuovo");
        maps.add("custom");

        // Imposta layout a griglia
        setLayout(new GridLayout(3, 4));
        try {
            // Mostra l'indirizzo IP
            JLabel ip = new JLabel("IP number = " + InetAddress.getLocalHost().getHostAddress());
            ip.setHorizontalAlignment(SwingConstants.CENTER);
            add(ip);

            // Etichette per funzionalità future
            JLabel empty2 = new JLabel("Client / Server (In sviluppo)");
            empty2.setHorizontalAlignment(SwingConstants.CENTER);
            add(empty2);

            JLabel empty3 = new JLabel("Connessione (In sviluppo)");
            empty3.setHorizontalAlignment(SwingConstants.CENTER);
            add(empty3);

            // Pulsante per avviare il gioco
            JButton bPlay = new JButton("Play");
            bPlay.addActionListener(this);
            add(bPlay);

            // Pulsanti per cambiare macchina
            leftArrowButton = new JButton("<");
            leftArrowButton.addActionListener(e -> changeCar(-1));
            add(leftArrowButton);

            // Etichetta per la macchina corrente
            carLabel1 = new JLabel(new MicroCarSprite(cars.get(currentCarIndex)).convertToIcon());
            carLabel1.setHorizontalAlignment(SwingConstants.CENTER);
            carLabel1.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    selectCar(currentCarIndex); // Seleziona la macchina corrente
                }
            });
            add(carLabel1);

            // Etichetta per la macchina successiva
            carLabel2 = new JLabel(new MicroCarSprite(cars.get((currentCarIndex + 1) % cars.size())).convertToIcon());
            carLabel2.setHorizontalAlignment(SwingConstants.CENTER);
            carLabel2.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    selectCar((currentCarIndex + 1) % cars.size()); // Seleziona la macchina successiva
                }
            });
            add(carLabel2);

            // Pulsante per cambiare macchina a destra
            rightArrowButton = new JButton(">");
            rightArrowButton.addActionListener(e -> changeCar(1));
            add(rightArrowButton);

            // Pulsante per cambiare circuito a sinistra
            leftArrowButton2 = new JButton("<-");
            leftArrowButton2.addActionListener(e -> changeMap(-1));
            add(leftArrowButton2);

            // Etichetta per il circuito corrente
            mapLabel = new JLabel(new CircuitImage(maps.get(currentMapIndex), (int) (0.8 * Toolkit.getDefaultToolkit().getScreenSize().getWidth()), (int) (0.8 * Toolkit.getDefaultToolkit().getScreenSize().getHeight())).convertToIcon());
            mapLabel.setHorizontalAlignment(SwingConstants.CENTER);
            add(mapLabel);

            // Pulsante per cambiare circuito a destra
            rightArrowButton2 = new JButton("->");
            rightArrowButton2.addActionListener(e -> changeMap(1));
            add(rightArrowButton2);

            // Etichetta per funzionalità future
            createTrack= new JButton("crea nuovo tracciato");
            createTrack.setHorizontalAlignment(SwingConstants.CENTER);
            createTrack.addActionListener(e -> openCreator() );
                  add(createTrack);

            // Valori di default
            choosenCar = "yellow"; // Macchina di default
            choosenTrack = "basic"; // Percorso di default

        } catch (UnknownHostException ex) {
            Logger.getLogger(BasicOptionsChoicePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Cambia la macchina e aggiorna le icone
    private void changeCar(int direction) {
        currentCarIndex = (currentCarIndex + direction + cars.size()) % cars.size();
        updateCarLabels();
        choosenCar = cars.get(currentCarIndex); // Aggiorna la macchina scelta
    }

    // Cambia il circuito e aggiorna l'icona
    private void changeMap(int direction) {
        currentMapIndex = (currentMapIndex + direction + maps.size()) % maps.size();
        updateMapLabel();
        choosenTrack = maps.get(currentMapIndex); // Aggiorna il circuito scelto
    }

    // Seleziona direttamente una macchina
    private void selectCar(int carIndex) {
        choosenCar = cars.get(carIndex); // Imposta la macchina scelta
        updateCarLabels();
    }

    // Aggiorna le etichette delle macchine
    private void updateCarLabels() {
        carLabel1.setIcon(new MicroCarSprite(cars.get(currentCarIndex)).convertToIcon());
        carLabel2.setIcon(new MicroCarSprite(cars.get((currentCarIndex + 1) % cars.size())).convertToIcon());
    }

    // Aggiorna l'etichetta del circuito
    private void updateMapLabel() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        mapLabel.setIcon(new CircuitImage(maps.get(currentMapIndex), (int) (0.8 * screenSize.getWidth()), (int) (0.8 * screenSize.getHeight())).convertToIcon());
    }
    
    private void openCreator() {
    // Crea un'istanza di MapCreator
    MapCreator mapcreator = new MapCreator();
    
    // Imposta il comportamento di chiusura per chiudere solo la finestra
    mapcreator.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    
    // Rende visibile la finestra del MapCreator
    mapcreator.setVisible(true);
    choosenTrack = "custom";
}

    /**
     * Gestore degli eventi per i pulsanti.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String buttonPressed = e.getActionCommand();
        if ("Play".equals(buttonPressed)) {
            m.setMessage(choosenTrack + ";" + choosenCar); // Imposta il messaggio con le scelte
            parentFrame.dispose(); // Chiude il JFrame
        }
    }
}
