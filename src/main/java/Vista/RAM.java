package Vista;

import Controlador.Asignador;
import Modelo.Observado;
import Modelo.ObservadorPlanificador;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.LinkedList;

public class RAM extends JFrame implements  Observado {
    Asignador planificador;
    public static LinkedList<BloqueMemoria>bloques;
    JPanel panel = new JPanel();
    public RAM() throws InterruptedException {
        bloques = new LinkedList<>();
        this.setSize(800,500);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        panel.setLayout(new GridLayout(4, 8)); // 4 filas y 8 columnas
        for (int i = 0; i < 32; i++) {
            BloqueMemoria bloque = new BloqueMemoria(i);
            bloque.setBackground(Color.WHITE); // Puedes establecer el color que desees
            bloque.setBorder(new LineBorder(Color.BLACK));
            bloques.add(bloque);
        }
        this.setContentPane(panel);


    }



    @Override
    public void actualizar() {
        //  panel.removeAll();
        for (BloqueMemoria bloque : bloques){
            bloque.repaint();
            bloque.revalidate();
            panel.add(bloque);
            panel.revalidate();
            panel.repaint();
        }
    }
}
