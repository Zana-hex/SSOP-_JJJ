package Vista;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class BloqueMemoria extends JPanel {
    int idPanel;
    private Color color;
    private double porcentaje;

    JLabel porcentajeLabel, idProcesoLabel;

    public BloqueMemoria(int id) {
        porcentaje = 0.0;
        this.idPanel = id;
        porcentajeLabel = new JLabel();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // Establecer el diseño vertical

    }





    public JLabel getPorcentajeLabel() {
        return porcentajeLabel;
    }

    public void establecerPorcentaje(int percent) {
        JLabel label = new JLabel();
        String per = String.valueOf(percent);
        label.setText("Uso: " + per + "%");
        setPorcentajeLabel(label);
        this.add(porcentajeLabel);
    }

    public void establecerProceso(int id){
        JLabel label = new JLabel();
        String idString = String.valueOf(id);
        label.setText("ID: " + idString);
        setIdProcesoLabel(label);
        this.add(idProcesoLabel);
    }


    public void establecerColor(Color color) {
        this.setBackground(color);
    }

    public void eliminar() {
        this.removeAll();
        setBorder(new LineBorder(Color.BLACK));
        this.setBackground(Color.white);
    }

    public JLabel getIdProcesoLabel() {
        return idProcesoLabel;
    }

    public void setIdProcesoLabel(JLabel idProcesoLabel) {
        this.idProcesoLabel = idProcesoLabel;
    }

    public void setPorcentajeLabel(JLabel porcentajeLabel) {

        this.porcentajeLabel = porcentajeLabel;
    }

}


