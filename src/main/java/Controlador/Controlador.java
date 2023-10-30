package Controlador;

import Modelo.Proceso;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class Controlador {
    Queue<Proceso> colaProcesos = new LinkedList<>();
    public Controlador(){

    }

    public void lector() {
        int tLlegada, prioridad, tProcesador, mb, numImpresoras, numEsc, numModem, numCds;
        try(BufferedReader in = new BufferedReader(new FileReader("procesos.txt"))) {
            String str;
            while ((str = in.readLine()) != null) {
                String[] atributos = str.split(",");

                tLlegada = Integer.parseInt(atributos[0]);
                prioridad = Integer.parseInt(atributos[1]);
                tProcesador = Integer.parseInt(atributos[2]);
                mb = Integer.parseInt(atributos[3]);
                numImpresoras = Integer.parseInt(atributos[4]);
                numEsc = Integer.parseInt(atributos[5]);
                numModem = Integer.parseInt(atributos[6]);
                numCds = Integer.parseInt(atributos[7]);


                Proceso proceso = new Proceso(tLlegada, prioridad,
                        tProcesador, mb, numImpresoras, numEsc, numModem, numCds);
                colaProcesos.add(proceso);
            }

        }
        catch (IOException e) {
            System.out.println("File Read Error");
        }
    }
}
