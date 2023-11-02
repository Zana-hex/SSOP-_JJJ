package Controlador;

import Modelo.Proceso;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class Controlador {

    int memoriaDisponible = 1024;
    final int bloqueMemoria = 32;

    int contadorId = 0;
    Queue<Proceso> colaProcesos = new LinkedList<>();
    Queue<Proceso> colaTiempoReal = new LinkedList<>();
    Queue<Proceso> colaUsuario = new LinkedList<>();
    Queue<Proceso> prioridad1 = new LinkedList<>();
    Queue<Proceso> prioridad2 = new LinkedList<>();
    Queue<Proceso> prioridad3 = new LinkedList<>();

    public Controlador() {

    }

    public void asignacion() throws InterruptedException {
        boolean bandera = true;
        int multiplicador = 2;
        while (!colaProcesos.isEmpty()) {
            Proceso actual = colaProcesos.poll();
            if (actual.getPrioridad() == 0) {
                if (memoriaDisponible - 64 >= 0) {
                    actual.setMemoriaAsignada(64);
                    memoriaDisponible -= 64;
                    colaTiempoReal.offer(actual);
                }
                Thread.sleep(1000);
            }

            if (actual.getPrioridad() == 1) {
                if (actual.getMegas() <= bloqueMemoria) {
                    if (memoriaDisponible - bloqueMemoria >= 0) {
                        actual.setMemoriaAsignada(bloqueMemoria);
                        memoriaDisponible -= bloqueMemoria;
                        prioridad1.offer(actual);
                    } else {

                    }
                } else {
                    while (bandera) {

                        if (actual.getMegas() <= 32*multiplicador){
                            actual.setMemoriaAsignada(32*multiplicador);

                        }
                    }
                }
                Thread.sleep(1000);
            }


        }
    }

    public void lector() {
        int tLlegada, prioridad, tProcesador, mb, numImpresoras, numEsc, numModem, numCds;
        try (BufferedReader in = new BufferedReader(new FileReader("procesos.txt"))) {
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


                Proceso proceso = new Proceso(contadorId, tLlegada, prioridad,
                        tProcesador, mb, numImpresoras, numEsc, numModem, numCds, 0);

                colaProcesos.offer(proceso);
                contadorId++;
            }


        } catch (IOException e) {
            System.out.println("Error a leer el archivo");
        }
    }
}
