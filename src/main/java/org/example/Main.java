package org.example;

import Controlador.Planificador;
import Vista.RAM;
import Vista.Rendimiento;
import Vista.TablaProcesos;

import javax.swing.*;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws InterruptedException {

        Planificador p = new Planificador();



        SwingUtilities.invokeLater(() -> {
            TablaProcesos tabla = new TablaProcesos();
            tabla.setVisible(true);
            p.agregarObservador(tabla);
                });

           RAM r = new RAM();
            r.setVisible(true);
            p.agregarObservador(r);
/*
        Rendimiento rendimiento = new Rendimiento();
        rendimiento.setVisible(true);
        p.agregarObservador(rendimiento);

 */
        Thread thread1 = new Thread(p);
       thread1.start();

    }

}
