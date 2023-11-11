package Controlador;

import Modelo.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class Asignador {

    int cdsDisponibles = 0;
    int impresorasDisponibles = 0;
    int modemsDisponibles = 0;
    int escanersDisponibles = 0;
    int memoriaDisponible = 1024;
    Impresora[] impresoras;
    CD[] cds;

    Modem[] modems;

    Escaner[] escaners;

    final int bloqueMemoria = 32;

    Queue<Proceso> colaProcesos = new LinkedList<>();
    Queue<Proceso> colaTiempoReal = new LinkedList<>();
    Queue<Proceso> colaUsuario = new LinkedList<>();
    Queue<Proceso> prioridad1 = new LinkedList<>();
    Queue<Proceso> prioridad2 = new LinkedList<>();
    Queue<Proceso> prioridad3 = new LinkedList<>();

    public Asignador() throws InterruptedException {
        lector();
        Proceso proceso = colaProcesos.poll();
        impresoras = new Impresora[2];
        cds = new CD[2];
        modems = new Modem[1];
        escaners = new Escaner[1];
        crearRecursos();

        asignacion(proceso);


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

    public void asignacion(Proceso actual) throws InterruptedException {
        while (!colaProcesos.isEmpty()) {
            boolean bandera = true;
            int multiplicador = 2;

            if (actual.getPrioridad() == 0) {
                if (memoriaDisponible - 64 >= 0) {
                    actual.setMemoriaAsignada(64);
                    memoriaDisponible -= 64;
                    colaTiempoReal.offer(actual);
                } else {
                    colaProcesos.offer(actual);
                }

            }

            if (actual.getPrioridad() == 1 || actual.getPrioridad() == 2 || actual.getPrioridad() == 3) {
                if (actual.getMegas() <= bloqueMemoria) {
                    if (memoriaDisponible - bloqueMemoria >= 0) {
                        if (verificarRecursos(actual)) {
                            actual.setMemoriaAsignada(bloqueMemoria);
                            memoriaDisponible -= bloqueMemoria;
                            actual.setEstado("Listo");
                            if (actual.getPrioridad() == 1) {
                                prioridad1.offer(actual);
                            }
                            if (actual.getPrioridad() == 2) {
                                prioridad2.offer(actual);
                            }
                            if (actual.getPrioridad() == 3) {
                                prioridad3.offer(actual);
                            }
                        } else {
                            actual.setEstado("Bloqueado");
                            colaUsuario.offer(actual);
                        }
                    }
                } else {
                    while (bandera) {
                        if (actual.getMegas() <= bloqueMemoria * multiplicador) {
                            if (memoriaDisponible - bloqueMemoria * multiplicador >= 0) {
                                if (verificarRecursos(actual)) {
                                    actual.setMemoriaAsignada(bloqueMemoria * multiplicador);
                                    memoriaDisponible -= bloqueMemoria * multiplicador;
                                    actual.setEstado("Listo");
                                    if (actual.getPrioridad() == 1) {
                                        prioridad1.offer(actual);
                                    }
                                    if (actual.getPrioridad() == 2) {
                                        prioridad2.offer(actual);
                                    }
                                    if (actual.getPrioridad() == 3) {
                                        prioridad3.offer(actual);
                                    }
                                } else {
                                    colaUsuario.offer(actual);
                                }
                            }
                            bandera = false;
                        } else {
                            multiplicador++;
                        }
                    }
                }
                Thread.sleep(1000);
            }
        }
    }

    public boolean verificarRecursos(Proceso actual) {
        impresorasDisponibles = 0;
        cdsDisponibles = 0;
        modemsDisponibles = 0;
        escanersDisponibles = 0;
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
        if (actual.getNumImpresoras() <= impresorasDisponibles &&
                actual.getNumCDs() <= cdsDisponibles &&
                actual.getNumModems() <= modemsDisponibles &&
                actual.getNumEscaners() <= escanersDisponibles) {
            return true;
        } else {
            return false;
        }
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


                Proceso proceso = new Proceso(tLlegada, tLlegada, prioridad,
                        tProcesador, mb, numImpresoras, numEsc, numModem, numCds, 0);

                colaProcesos.offer(proceso);
            }


        } catch (IOException e) {
            System.out.println("Error a leer el archivo");
        }
    }

    public void ejecucion() throws InterruptedException {
        if (!colaTiempoReal.isEmpty()) {
            Proceso proceso = colaTiempoReal.poll();
            proceso.setEstado("Ejecucion");
            while (proceso.getTiempoRestante() != 0) {
                proceso.setTiempoRestante(proceso.getTiempoRestante() - 1);
                Thread.sleep(1000);
            }
            proceso.setEstado("Finalizado");
        }
        if (!prioridad1.isEmpty()) {
            Proceso proceso = prioridad1.poll();
            proceso.setEstado("Ejecucion");
            Thread.sleep(1000);
            proceso.setTiempoRestante(proceso.getTiempoRestante() - 1);
            proceso.setEstado("Listo");
            if (!(proceso.getTiempoRestante() == 0))
                prioridad2.offer(proceso);

        }
        if (!prioridad2.isEmpty()) {
            Proceso proceso = prioridad2.poll();
            proceso.setEstado("Ejecucion");
            Thread.sleep(1000);
            proceso.setTiempoRestante(proceso.getTiempoRestante() - 1);
            proceso.setEstado("Listo");
            if (!(proceso.getTiempoRestante() == 0))
                prioridad3.offer(proceso);
        }
        if (!prioridad3.isEmpty()) {
            Proceso proceso = prioridad3.poll();
            proceso.setEstado("Ejecucion");
            Thread.sleep(1000);
            proceso.setTiempoRestante(proceso.getTiempoRestante() - 1);
            proceso.setEstado("Listo");
            if (!(proceso.getTiempoRestante() == 0)) {
                prioridad3.offer(proceso);
            }
        }
    }
}
