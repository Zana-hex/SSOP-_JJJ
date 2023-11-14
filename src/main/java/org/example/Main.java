package org.example;

import Controlador.Asignador;
import Vista.RAM;
import Vista.TablaProcesos;

import javax.swing.*;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws InterruptedException {

        RAM r = new RAM();
        r.setVisible(true);
        TablaProcesos tabla = new TablaProcesos();
        tabla.setVisible(true);
        Asignador p = new Asignador();

        Thread thread1 = new Thread(p);
        p.agregarObservador(r);
        p.agregarObservador(tabla);

       thread1.start();

    }
}
