package Vista;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class BloqueMemoria extends JPanel {
    int idPanel;
    double porcentaje;
    JLabel porcentajeLabel;

    public BloqueMemoria(int id){
       this.idPanel = id;
       porcentajeLabel = new JLabel();
       this.add(porcentajeLabel);
    }

    public double getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(double porcentaje) {
        this.porcentaje = porcentaje;
    }

    public JLabel getPorcentajeLabel() {
        return porcentajeLabel;
    }
    public void establecerPorcentaje(double percent){
        porcentajeLabel = null;
        JLabel label = new JLabel();
        String per = String.valueOf(percent);
        label.setText(per);
        setPorcentajeLabel(label);
        this.add(porcentajeLabel);
        revalidate();
        repaint();
    }

    public void establecerColor(Color color){
        this.setBackground(color);
        this.revalidate();
        this.repaint();
    }

    public void eliminar(){
        this.remove(porcentajeLabel);
        setBackground(Color.WHITE);
        setBorder(new LineBorder(Color.BLACK));
        this.revalidate();
        this.repaint();
    }
    public void setPorcentajeLabel(JLabel porcentajeLabel) {
        this.porcentajeLabel = porcentajeLabel;
    }
}
