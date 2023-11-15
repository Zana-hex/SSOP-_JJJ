package Vista;
import Modelo.*;
import Modelo.Observado;
import Controlador.Planificador;

import javax.swing.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.ArrayList;

public class Rendimiento extends JFrame implements Observado {

    static JPanel panel = new JPanel();
   public static int memoria;
    JLabel tituloMemoria, impresora1, impresora2, escaner, cd1, cd2, modem;

    static JLabel memoriaDisponible;
    public Rendimiento(){

        memoriaDisponible = new JLabel();
        impresora1 = new JLabel();
        impresora2 = new JLabel();
        cd1 = new JLabel();
        cd2 = new JLabel();
        modem = new JLabel();
        escaner = new JLabel();
        panel.setLayout(null);
        this.setBounds(1150, 25, 300, 200);
        this.setContentPane(panel);
        tituloMemoria = new JLabel("Memoria Disponible: ");
        tituloMemoria.setBounds(25, 10, 300, 20);
        panel.add(tituloMemoria);


    }
    @Override
    public synchronized void actualizar() {
        recursos();
        panel.revalidate();
        panel.repaint();

    }

    public static void establecerMemoria(int memoria){
        panel.remove(memoriaDisponible);
        memoriaDisponible = new JLabel(String.valueOf(memoria) + " MBs");
        memoriaDisponible.setBounds(150, 10, 300, 20);
        panel.add(memoriaDisponible);

    }
    public synchronized void recursos(){

        panel.remove(impresora1);
        panel.remove(impresora2);
        panel.remove(escaner);
        panel.remove(cd1);
        panel.remove(cd2);
        panel.remove(modem);

        Recurso imp1 = Planificador.recursos[0];
        Recurso imp2 = Planificador.recursos[1];
        Recurso esc = Planificador.recursos[2];
        Recurso CD1 = Planificador.recursos[3];
        Recurso CD2 = Planificador.recursos[4];
        Recurso mod = Planificador.recursos[5];


        impresora1 = new JLabel("Impresora "+ imp1.getId() +
                " " + imp1.getEstado() + " Proceso: " + imp1.getProcesoPropietario());
        impresora1.setBounds(25, 30, 300, 20);
        impresora2 = new JLabel("Impresora "+ imp2.getId() +
                " " + imp2.getEstado() + " Proceso: " + imp2.getProcesoPropietario());
        impresora2.setBounds(25, 50, 300, 20);

        escaner = new JLabel("Escaner "+ esc.getId() +
                " " + esc.getEstado() + " Proceso: " + esc.getProcesoPropietario());
        escaner.setBounds(25, 70, 300, 20);



        cd1 = new JLabel("CD "+ CD1.getId() +
                " " + CD1.getEstado() + " Proceso: " + CD1.getProcesoPropietario());
        cd1.setBounds(25, 90, 300, 20);

        cd2 = new JLabel("CD "+ CD2.getId() +
                " " + CD2.getEstado() + " Proceso: " + CD2.getProcesoPropietario());
        cd2.setBounds(25, 110, 300, 20);

        modem = new JLabel("Modem "+ mod.getId() +
                " " + mod.getEstado() + " Proceso: " + mod.getProcesoPropietario());
        modem.setBounds(25, 130, 300, 20);



        panel.add(impresora1);
        panel.add(impresora2);
        panel.add(escaner);
        panel.add(cd1);
        panel.add(cd2);
        panel.add(modem);


    }



}
