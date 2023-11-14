package Vista;

import javax.swing.*;
import java.awt.*;

public class VentanaConPaneles extends JFrame {
    public VentanaConPaneles() {
        // Configuración del JFrame
        setTitle("Ventana con Paneles");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        // Crear paneles
        JPanel panelIzquierdo = new JPanel();
        JPanel panelDerecho = new JPanel();

        // Establecer un color de fondo para los paneles (solo para visualización)
        panelIzquierdo.setBackground(Color.LIGHT_GRAY);
        panelDerecho.setBackground(Color.GRAY);

        // Configurar LayoutManager para el contenedor principal
        setLayout(new GridBagLayout());

        // Configurar GridBagConstraints para el panel izquierdo
        GridBagConstraints gbcIzquierdo = new GridBagConstraints();
        gbcIzquierdo.gridx = 0;
        gbcIzquierdo.gridy = 0;
        gbcIzquierdo.weightx = 0.7;
        gbcIzquierdo.gridheight = 100;// Proporción horizontal (ajusta según tus necesidades)
        gbcIzquierdo.fill = GridBagConstraints.BOTH;  // Ocupar espacio horizontal y vertical
        add(panelIzquierdo, gbcIzquierdo);

        // Configurar GridBagConstraints para el panel derecho
        GridBagConstraints gbcDerecho = new GridBagConstraints();
        gbcDerecho.gridx = 1;
        gbcDerecho.gridy = 0;
        gbcDerecho.weightx = 0.3;  // Proporción horizontal (ajusta según tus necesidades)
        gbcDerecho.fill = GridBagConstraints.BOTH;  // Ocupar espacio horizontal y vertical
        add(panelDerecho, gbcDerecho);

        // Agregar componentes a los paneles (tus componentes existentes)
        JButton botonPanelIzquierdo = new JButton("Botón en Panel Izquierdo");
        panelIzquierdo.add(botonPanelIzquierdo);

        JButton botonPanelDerecho = new JButton("Botón en Panel Derecho");
        panelDerecho.add(botonPanelDerecho);

        // Hacer visible el JFrame
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentanaConPaneles());
    }
}
