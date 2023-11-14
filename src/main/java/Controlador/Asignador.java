package Controlador;

import Modelo.*;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import Modelo.ObservadorPlanificador;
import Vista.BloqueMemoria;
import Vista.RAM;
import Vista.TablaProcesos;

public class Asignador implements Runnable, ObservadorPlanificador {

    int memoriaDisponible = 960;

    Impresora[] impresoras;
    CD[] cds;

    Modem[] modems;

    Escaner[] escaners;

    final int bloqueMemoria = 32;

    private ArrayList<Observado> observadores = new ArrayList<>();
    public static Queue<Proceso> colaProcesos = new LinkedList<>();
    public static Queue<Proceso> colaTiempoReal = new LinkedList<>();
    public static Queue<Proceso> colaUsuario = new LinkedList<>();
    public static Queue<Proceso> prioridad1 = new LinkedList<>();
    public static Queue<Proceso> prioridad2 = new LinkedList<>();
    public static Queue<Proceso> prioridad3 = new LinkedList<>();

    public Asignador() {

    }

    public void agregarObservador (Observado observador){
        observadores.add(observador);
    }


    public void asignarRecursos(Proceso proceso) {
        var impresorasAsignadas = 0;
        var cdsAsignados = 0;
        var modemsAsignados = 0;
        var escanersAsignados = 0;

        while (impresorasAsignadas != proceso.getNumImpresoras()) {
            for (Impresora imp : impresoras) {
                if (imp.getEstado().equals("Disponible")) {
                    proceso.impresorasAsignadas.add(imp.getIdImpresora());
                    imp.setEstado("Ocupado");
                    imp.setProcesoPropietario(proceso.getId());
                    impresorasAsignadas++;
                    break;
                }
            }
        }
        while (escanersAsignados != proceso.getNumEscaners()) {
            for (Escaner esc : escaners) {
                if (esc.getEstado().equals("Disponible")) {
                    proceso.escanersAsignados.add(esc.getIdEscaner());
                    esc.setEstado("Ocupado");
                    esc.setProcesoPropietario(proceso.getId());
                    escanersAsignados++;
                    break;
                }
            }
        }
        while (modemsAsignados != proceso.getNumModems()) {
            for (Modem mod : modems) {
                if (mod.getEstado().equals("Disponible")) {
                    proceso.modemsAsignados.add(mod.getIdModem());
                    mod.setEstado("Ocupado");
                    mod.setProcesoPropietario(proceso.getId());
                    modemsAsignados++;
                    break;
                }
            }
        }
        while (cdsAsignados != proceso.getNumCDs()) {
            for (CD cd : cds) {
                if (cd.getEstado().equals("Disponible")) {
                    proceso.cdAsignados.add(cd.getIdEstado());
                    cd.setEstado("Ocupado");
                    cd.setProcesoPropietario(proceso.getId());
                    cdsAsignados++;
                    break;
                }
            }
        }
    }

    public boolean asignacion(Proceso actual) throws InterruptedException {

        boolean bandera = true;
        int multiplicador = 1;
        if (actual.getPrioridad() == 0) {

            actual.setMemoriaAsignada(64);

            BloqueMemoria bloque1 = RAM.bloques.get(0);
            BloqueMemoria bloque2 = RAM.bloques.get(1);

            bloque1.establecerPorcentaje(100);
            bloque2.establecerPorcentaje(100);

            bloque1.establecerColor(Color.red);
            bloque2.establecerColor(Color.red);


            RAM.bloques.set(0, bloque1);
            RAM.bloques.set(1, bloque2);

            actual.bloquesAsignados.add(0);
            actual.bloquesAsignados.add(1);
            colaTiempoReal.offer(actual);
            notificar();

            return true;
        }

        if (actual.getPrioridad() == 1 || actual.getPrioridad() == 2 || actual.getPrioridad() == 3) {
            //Color color = Color.ra
            boolean continuos = false;
            while (bandera) {
                if (actual.getMegas() <= bloqueMemoria * multiplicador) {

                    if (memoriaDisponible - bloqueMemoria * multiplicador >= 0 && verificarRecursos(actual)) {
                        for (int i = 2; i < RAM.bloques.size(); i++) {
                            if (RAM.bloques.get(i).getBackground().equals(Color.WHITE)) {
                                continuos = verificarContinuos(i, multiplicador, actual);
                                if (continuos) {

                                    break;
                                }
                            }
                        }
                        for (int i = 0; i < actual.bloquesAsignados.size(); i++) {
                            int indice = actual.bloquesAsignados.get(i);
                            BloqueMemoria bloque = RAM.bloques.get(indice);
                            bloque.establecerColor(Color.green);
                            RAM.bloques.set(indice, bloque);

                        }

                        asignarRecursos(actual);
                        actual.setMemoriaAsignada(bloqueMemoria * multiplicador);
                        memoriaDisponible -= bloqueMemoria * multiplicador;
                        actual.setEstado("Listo");
                        if (actual.getPrioridad() == 1) {
                            prioridad1.offer(actual);
                           // notificar();
                            return true;
                        }
                        if (actual.getPrioridad() == 2) {
                            prioridad2.offer(actual);
                           // notificar();
                            return true;
                        }
                        if (actual.getPrioridad() == 3) {
                            prioridad3.offer(actual);
                           // notificar();
                            return true;
                        }
                    } else {
                        if (!(memoriaDisponible - bloqueMemoria * multiplicador >= 0) && continuos == false) {
                            actual.setFaltaMemoria(true);
                            System.out.println("FALTA MEMORIA");
                        } else {
                            actual.setFaltaMemoria(false);
                            System.out.println("FALTAN RECURSOS");
                        }
                        actual.setEstado("Bloqueado");
                        colaUsuario.offer(actual);
                        notificar();
                        return false;

                    }
                    bandera = false;
                } else {
                    multiplicador++;
                }
            }
        }

        return true;
    }

    public void liberarRecursos(Proceso actual) {

        if (actual.getNumImpresoras() != 0) {
            for (int imp : actual.impresorasAsignadas) {
                impresoras[imp - 1].setEstado("Disponible");
                impresoras[imp - 1].setProcesoPropietario(0);
            }
        }
        if (actual.getNumCDs() != 0) {
            for (int cd : actual.cdAsignados) {
                cds[cd - 1].setEstado("Disponible");
                cds[cd - 1].setProcesoPropietario(0);
            }
        }
        if (actual.getNumModems() != 0) {
            for (int modem : actual.modemsAsignados) {
                modems[modem - 1].setEstado("Disponible");
                modems[modem - 1].setProcesoPropietario(0);
            }
        }
        if (actual.getNumEscaners() != 0) {
            for (int esc : actual.escanersAsignados) {
                escaners[esc - 1].setEstado("Disponible");
                escaners[esc - 1].setProcesoPropietario(0);
            }
        }
        memoriaDisponible += actual.getMemoriaAsignada();
        actual.setMemoriaAsignada(0);
        if (actual.getPrioridad() == 0) {
            BloqueMemoria bloque1 = RAM.bloques.get(actual.bloquesAsignados.get(0));
            BloqueMemoria bloque2 = RAM.bloques.get(actual.bloquesAsignados.get(1));
            bloque1.eliminar();
            bloque2.eliminar();
            RAM.bloques.set(0, bloque1);
            RAM.bloques.set(1, bloque2);
        }
        if (actual.getPrioridad() == 1 || actual.getPrioridad() == 2 || actual.getPrioridad() == 3){
            ArrayList<Integer> sectores = new ArrayList<>(actual.bloquesAsignados);
            for (int sector : sectores){
                BloqueMemoria bloque = RAM.bloques.get(sector);
                bloque.eliminar();
                RAM.bloques.set(sector, bloque);
            }
        }
    }

    public boolean verificarRecursos(Proceso actual) {
        var impresorasDisponibles = 0;
        var cdsDisponibles = 0;
        var modemsDisponibles = 0;
        var escanersDisponibles = 0;
        for (Impresora imp : impresoras) {
            if (imp.getEstado().equals("Disponible")) {
                impresorasDisponibles++;
            }
        }
        for (CD cd : cds) {
            if (cd.getEstado().equals("Disponible")) {
                cdsDisponibles++;
            }
        }
        for (Modem modem : modems) {
            if (modem.getEstado().equals("Disponible")) {
                modemsDisponibles++;
            }
        }
        for (Escaner scaner : escaners) {
            if (scaner.getEstado().equals("Disponible")) {
                escanersDisponibles++;
            }
        }
        return actual.getNumImpresoras() <= impresorasDisponibles &&
                actual.getNumCDs() <= cdsDisponibles &&
                actual.getNumModems() <= modemsDisponibles &&
                actual.getNumEscaners() <= escanersDisponibles;
    }

    public void crearRecursos() {
        for (var i = 0; i < 2; i++) {
            Impresora impresora = new Impresora((i + 1), "Disponible");
            impresoras[i] = impresora;
            CD cd = new CD((i + 1), "Disponible");
            cds[i] = cd;
        }
        Escaner escaner = new Escaner(1, "Disponible");
        Modem modem = new Modem(1, "Disponible");
        escaners[0] = escaner;
        modems[0] = modem;

    }

    public void lector() {
        int tLlegada, prioridad, tProcesador, mb, numImpresoras, numEsc, numModem, numCds;
        try (BufferedReader in = new BufferedReader(new FileReader("procesos2.txt"))) {
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


                Proceso proceso = new Proceso(tLlegada, tLlegada, prioridad,
                        tProcesador, mb, numImpresoras, numEsc, numModem, numCds, 0);

                colaProcesos.offer(proceso);
            }


        } catch (IOException e) {
            System.out.println("Error a leer el archivo");
        }
    }

    public void ejecucion() throws InterruptedException {
        System.out.println("\nCola Procesos: " + colaProcesos.size() + "\nCola Tiempo Real"
                + colaTiempoReal.size() + "\nPrioridad 1: " + prioridad1.size()
                + "\nPrioridad2: " + prioridad2.size() + "\nPrioridad3: " + prioridad3.size() + "\nCola Usuario: " + colaUsuario.size());

        if (!colaTiempoReal.isEmpty()) {
            for (Proceso p : colaTiempoReal) {
                p.setEstado("Ejecucion");
                while (p.getTiempoRestante() != 0) {

                    System.out.println("\nProceso Tiempo real: " + p.getId() + "\nSegundos restantes: " + p.getTiempoRestante());

                    p.setTiempoRestante(p.getTiempoRestante() - 1);
                    notificar();
                    sleepInSeconds(1);
                }

            }
            Proceso proceso = colaTiempoReal.poll();
            proceso.setEstado("Finalizado");
            liberarRecursos(proceso);
            notificar();
            return;
        }
        if (!prioridad1.isEmpty()) {
            Proceso proceso = prioridad1.peek();
            proceso.setEstado("Ejecucion");
            notificar();
            sleepInSeconds(1);

            proceso.setTiempoRestante(proceso.getTiempoRestante() - 1);
            proceso.setEstado("Listo");
            proceso.setPrioridad(2);
            if (!(proceso.getTiempoRestante() == 0)) {
                prioridad2.offer(proceso);
                prioridad1.poll();
                return;
            } else {
                proceso.setEstado("Finalizado");
                liberarRecursos(proceso);
               // notificar();
            }
        }
        if (!prioridad2.isEmpty()) {
            Proceso proceso = prioridad2.peek();
            proceso.setEstado("Ejecucion");
            notificar();
            sleepInSeconds(1);
            proceso.setTiempoRestante(proceso.getTiempoRestante() - 1);
            proceso.setEstado("Listo");
            proceso.setPrioridad(2);
            if (!(proceso.getTiempoRestante() == 0)) {
                prioridad3.offer(proceso);
                prioridad2.poll();

                return;
            } else {
                proceso.setEstado("Finalizado");
                liberarRecursos(proceso);
            }

        }
        if (!prioridad3.isEmpty()) {
            Proceso proceso = prioridad3.peek();
            proceso.setEstado("Ejecucion");
            TablaProcesos.setProcesoEjecucion(proceso);
            notificar();
            sleepInSeconds(1);

            proceso.setTiempoRestante(proceso.getTiempoRestante() - 1);
            proceso.setEstado("Listo");
            if (!(proceso.getTiempoRestante() == 0)) {

                //prioridad3.offer(proceso);
            } else {
                proceso.setEstado("Finalizado");
                liberarRecursos(proceso);
                prioridad3.poll();
            }
        }

    }

    public void bloqueoMemoria() throws InterruptedException {
        int memoriaMaxima = 0;
        int indice = 0;
        String cola = null;
        for (int i = 0; i < prioridad1.size() && !prioridad1.isEmpty(); i++) {
            Proceso process = prioridad1.poll();
            if (process.getMemoriaAsignada() > memoriaMaxima) {
                memoriaMaxima = process.getMemoriaAsignada();
                indice = i;
                cola = "Prioridad1";
            }
        }
        for (int i = 0; i < prioridad2.size() && !prioridad2.isEmpty(); i++) {
            Proceso process = prioridad2.poll();
            if (process.getMemoriaAsignada() > memoriaMaxima) {
                memoriaMaxima = process.getMemoriaAsignada();
                indice = i;
                cola = "Prioridad2";
            }
        }

        for (int i = 0; i < prioridad2.size() && !prioridad2.isEmpty(); i++) {
            Proceso process = prioridad2.poll();
            if (process.getMemoriaAsignada() > memoriaMaxima) {
                memoriaMaxima = process.getMemoriaAsignada();
                indice = i;
                cola = "Prioridad2";
            }
        }

        for (int i = 0; i < prioridad3.size() && !prioridad3.isEmpty(); i++) {
            Proceso process = prioridad3.poll();
            if (process.getMemoriaAsignada() > memoriaMaxima) {
                memoriaMaxima = process.getMemoriaAsignada();
                indice = i;
                cola = "Prioridad3";
            }
        }

        assert cola != null;
        if (cola.equals("Prioridad1")) {
            for (int i = 0; i < prioridad1.size() && !prioridad1.isEmpty(); i++) {
                Proceso proceso = prioridad1.poll();
                if (i == indice) {
                    while (proceso.getTiempoRestante() != 0) {
                        System.out.println("\nProceso Memoria: " + proceso.getId() + "\nSegundos restantes: " + proceso.getTiempoRestante());
                        sleepInSeconds(1);
                        proceso.setTiempoRestante(proceso.getTiempoRestante() - 1);
                    }
                    proceso.setEstado("Finalizado");
                    liberarRecursos(proceso);
                } else {
                    prioridad1.offer(proceso);
                }
            }
        }
        if (cola.equals("Prioridad2")) {
            for (int i = 0; i < prioridad2.size() && !prioridad2.isEmpty(); i++) {
                Proceso proceso = prioridad2.poll();
                if (i == indice) {
                    while (proceso.getTiempoRestante() != 0) {
                        System.out.println("\nProceso Memoria: " + proceso.getId() + "\nSegundos restantes: " + proceso.getTiempoRestante());
                        sleepInSeconds(1);
                        proceso.setTiempoRestante(proceso.getTiempoRestante() - 1);
                    }
                    proceso.setEstado("Finalizado");
                    liberarRecursos(proceso);
                } else {
                    prioridad2.offer(proceso);
                }
            }
        }
        if (cola.equals("Prioridad3")) {
            for (int i = 0; i < prioridad3.size() && !prioridad3.isEmpty(); i++) {
                Proceso proceso = prioridad3.poll();
                if (i == indice) {
                    while (proceso.getTiempoRestante() != 0) {
                        System.out.println("\nProceso Memoria: " + proceso.getId() + "\nSegundos restantes: " + proceso.getTiempoRestante());
                        sleepInSeconds(1);
                        proceso.setTiempoRestante(proceso.getTiempoRestante() - 1);
                    }
                    proceso.setEstado("Finalizado");
                    liberarRecursos(proceso);
                } else {
                    prioridad3.offer(proceso);
                }
            }
        }
    }

    @Override
    public void run() {
        lector();
        impresoras = new Impresora[2];
        cds = new CD[2];
        modems = new Modem[1];
        escaners = new Escaner[1];
        crearRecursos();
        while (!colaUsuario.isEmpty() || !colaProcesos.isEmpty() ||
                !prioridad1.isEmpty() || !prioridad2.isEmpty() || !prioridad3.isEmpty()) {
            if (!colaProcesos.isEmpty()) {
                Proceso process = colaProcesos.poll();
                TablaProcesos.vistaProcesos.offer(process);
                try {
                    asignacion(process);
                    ejecucion();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }

            for (int i = 1; i <= colaUsuario.size() && !colaUsuario.isEmpty(); i++) {
                Proceso proceso = colaUsuario.poll();
                try {
                    if (asignacion(proceso)) {
                        ejecucion();
                    } else {
                        if (proceso.isFaltaMemoria()) {
                            bloqueoMemoria();
                        }
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            if (!prioridad1.isEmpty() || !prioridad2.isEmpty() || !prioridad3.isEmpty()) {
                try {
                    ejecucion();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

        }
    }

    public boolean verificarContinuos(int inicio, int cantidad, Proceso actual) {
        ArrayList<Integer> indices = new ArrayList<>();
        for (int i = inicio; i < inicio + cantidad; i++) {
            if (RAM.bloques.get(i).getBackground().equals(Color.WHITE)) {
                indices.add(i);
            }
        }
        if (indices.size() == cantidad) {
            actual.bloquesAsignados.addAll(indices);
            return true;
        } else {
            return false;
        }
    }

    public static void sleepInSeconds(int seconds) throws InterruptedException {
        long end = System.currentTimeMillis() + seconds * 1000;
        while (System.currentTimeMillis() < end) {
            Thread.sleep(1); // Puedes ajustar este valor si es necesario
        }
    }

    @Override
    public void notificar() {
        for (Observado observador : observadores) {
            observador.actualizar();
        }
    }
}
