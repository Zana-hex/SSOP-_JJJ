package Vista;

import Controlador.Planificador;
import Modelo.Observado;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.LinkedList;

public class RAM extends JFrame implements Observado {
    Planificador planificador;
    public static LinkedList<BloqueMemoria> bloques;
    JPanel panel = new JPanel();

    public RAM() {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception ex) {

        }
        bloques = new LinkedList<>();
        this.setBounds(350, 25, 800, 300);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        panel.setLayout(new GridLayout(4, 8)); // 4 filas y 8 columnas
        for (int i = 0; i < 32; i++) {
            BloqueMemoria bloque = new BloqueMemoria(i);
            bloque.setBackground(Color.WHITE); // Puedes establecer el color que desees
            bloque.setBorder(new LineBorder(Color.BLACK));
            bloques.add(bloque);
            panel.add(bloque);
        }
        this.setContentPane(panel);


    }

    @Override
    public synchronized void actualizar() {
        //  panel.removeAll();
        if (!bloques.isEmpty()) {
            for (BloqueMemoria bloque : bloques) {
                bloque.invalidate();
                bloque.revalidate();
                bloque.repaint();

                panel.revalidate();
                panel.repaint();
            }
        }
    }
}
