package Vista;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class RAM extends JFrame {
    JPanel panel = new JPanel();
    public RAM(){
        this.setSize(800,500);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        panel.setLayout(new GridLayout(4, 8)); // 4 filas y 8 columnas
        for (int i = 0; i < 32; i++) {
            JPanel cuadro = new JPanel();
            cuadro.setBackground(Color.WHITE); // Puedes establecer el color que desees
            cuadro.setBorder(new LineBorder(Color.BLACK));
            panel.add(cuadro);
        }
        this.setContentPane(panel);

    }
}
