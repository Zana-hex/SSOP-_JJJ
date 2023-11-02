package Controlador;

import Modelo.Proceso;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class Controlador {

    int memoriaDisponible = 1024;
    int impresorasDisponibles = 2;
    int escanersDisponibles = 1;
    int modemsDisponibles = 1;
    int cdsDisponibles = 2;
    final int bloqueMemoria = 32;

    int contadorId = 0;
    Queue<Proceso> colaProcesos = new LinkedList<>();
    Queue<Proceso> colaTiempoReal = new LinkedList<>();
    Queue<Proceso> colaUsuario = new LinkedList<>();
    Queue<Proceso> prioridad1 = new LinkedList<>();
    Queue<Proceso> prioridad2 = new LinkedList<>();
    Queue<Proceso> prioridad3 = new LinkedList<>();

    public Controlador() {
        Proceso proceso = colaProcesos.poll();
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
                    Thread.sleep(1000);
                } else {
                    colaProcesos.offer(actual);
                }

            }

            if (actual.getPrioridad() == 1 || actual.getPrioridad() == 2 || actual.getPrioridad() == 3) {
                if (actual.getMegas() <= bloqueMemoria) {
                    if (memoriaDisponible - bloqueMemoria >= 0) {
                        if (verificarCds(actual) && validacionModems(actual) && validacionEscaners(actual)
                                && validacionImpresoras(actual))
                        {
                            actual.setMemoriaAsignada(bloqueMemoria);
                            memoriaDisponible -= bloqueMemoria;
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
                            colaProcesos.offer(actual);
                        }
                    }
                } else {
                    while (bandera) {
                        if (actual.getMegas() <= bloqueMemoria * multiplicador) {
                            if (memoriaDisponible - bloqueMemoria * multiplicador >= 0) {
                                if (verificarCds(actual) && validacionModems(actual) && validacionEscaners(actual)
                                        && validacionImpresoras(actual)) {
                                    actual.setMemoriaAsignada(bloqueMemoria * multiplicador);
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
                                    colaProcesos.offer(actual);
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

    public boolean validacionImpresoras(Proceso actual) {
        if (actual.getNumImpresoras() <= impresorasDisponibles) {
            impresorasDisponibles -= actual.getNumImpresoras();
            return true;
        } else {
            return false;
        }
    }

    public boolean validacionModems(Proceso actual) {
        if (actual.getNumModems() <= modemsDisponibles) {
            modemsDisponibles -= actual.getNumModems();
            return true;
        } else {
            return false;
        }
    }

    public boolean validacionEscaners(Proceso actual) {
        if (actual.getNumEscaners() <= escanersDisponibles) {
            escanersDisponibles -= actual.getNumEscaners();
            return true;
        } else {
            return false;
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
