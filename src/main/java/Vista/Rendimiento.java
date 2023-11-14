package Vista;
import Modelo.*;
import Modelo.Observado;

import javax.swing.*;
import java.util.ArrayList;

public class Rendimiento extends JFrame implements Observado {
   public static ArrayList<Impresora> impresoras = new ArrayList<>();
   public static ArrayList<Escaner> escaners = new ArrayList<>();
   public static ArrayList<CD> cds = new ArrayList<>();
   public static ArrayList<Modem> modems = new ArrayList<>();
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
    public void recursos(){


        panel.remove(impresora1);
        panel.remove(impresora2);
        panel.remove(escaner);
        panel.remove(cd1);
        panel.remove(cd2);
        panel.remove(modem);
        Impresora imp1 = impresoras.get(0);
        Impresora imp2 = impresoras.get(1);
        Escaner esc = escaners.get(0);
        CD CD1 = cds.get(0);
        CD CD2 = cds.get(1);
        Modem mod = modems.get(0);
        impresora1 = new JLabel("Impresora "+ imp1.getIdImpresora() +
                " " + imp1.getEstado() + " Proceso: " + imp1.getProcesoPropietario());
        impresora1.setBounds(25, 20, 300, 20);
        impresora2 = new JLabel("Impresora "+ imp2.getIdImpresora() +
                " " + imp2.getEstado() + " Proceso: " + imp2.getProcesoPropietario());
        impresora2.setBounds(25, 30, 300, 20);
        escaner = new JLabel("Escaner "+ esc.getIdEscaner() +
                " " + esc.getEstado() + " Proceso: " + esc.getProcesoPropietario());
        escaner.setBounds(25, 40, 300, 20);
        cd1 = new JLabel("CD "+ CD1.getIdEstado() +
                " " + CD1.getEstado() + " Proceso: " + CD1.getProcesoPropietario());
        cd1.setBounds(25, 50, 300, 20);

        cd2 = new JLabel("CD "+ CD2.getIdEstado() +
                " " + CD2.getEstado() + " Proceso: " + CD2.getProcesoPropietario());
        cd2.setBounds(25, 60, 300, 20);
        modem = new JLabel("CD "+ mod.getIdModem() +
                " " + mod.getEstado() + " Proceso: " + mod.getProcesoPropietario());
        modem.setBounds(25, 70, 300, 20);
        panel.add(impresora1);
        panel.add(impresora2);
        panel.add(escaner);
        panel.add(cd1);
        panel.add(cd2);
        panel.add(modem);
    }


}
