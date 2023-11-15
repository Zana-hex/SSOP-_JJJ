package Vista;
import Modelo.*;
import Modelo.Observado;
import Controlador.Planificador;

import javax.swing.*;
import java.util.ArrayList;

public class Rendimiento extends JFrame implements Observado {
   public static ArrayList<Impresora> impresorasCopy = new ArrayList<>(Planificador.impresoras);
   public static ArrayList<Escaner> escanersCopy = new ArrayList<>(Planificador.escaners);
   public static ArrayList<CD> cdsCopy = new ArrayList<>(Planificador.cds);
   public static ArrayList<Modem> modemsCopy = new ArrayList<>(Planificador.modems);
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
        Impresora imp1 = impresorasCopy.get(0);
        Impresora imp2 = impresorasCopy.get(1);
        Escaner esc = escanersCopy.get(0);

        CD CD1 = cdsCopy.get(0);
        CD CD2 = cdsCopy.get(1);
        Modem mod = modemsCopy.get(0);


        impresora1 = new JLabel("Impresora "+ imp1.getIdImpresora() +
                " " + imp1.getEstado() + " Proceso: " + imp1.getProcesoPropietario());
        impresora1.setBounds(25, 30, 300, 20);
        impresora2 = new JLabel("Impresora "+ imp2.getIdImpresora() +
                " " + imp2.getEstado() + " Proceso: " + imp2.getProcesoPropietario());
        impresora2.setBounds(25, 50, 300, 20);

        escaner = new JLabel("Escaner "+ esc.getIdEscaner() +
                " " + esc.getEstado() + " Proceso: " + esc.getProcesoPropietario());
        escaner.setBounds(25, 70, 300, 20);



        cd1 = new JLabel("CD "+ CD1.getIdEstado() +
                " " + CD1.getEstado() + " Proceso: " + CD1.getProcesoPropietario());
        cd1.setBounds(25, 90, 300, 20);

        cd2 = new JLabel("CD "+ CD2.getIdEstado() +
                " " + CD2.getEstado() + " Proceso: " + CD2.getProcesoPropietario());
        cd2.setBounds(25, 110, 300, 20);

        modem = new JLabel("Modem "+ mod.getIdModem() +
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
