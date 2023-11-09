package Modelo;

import java.util.ArrayList;

public class Proceso {
    int tiempoLlegada, prioridad, tiempoProcesador, megas, numImpresoras,
            numEscaners, numModems, numCDs, id, memoriaAsignada;
    public ArrayList<Integer> impresorasAsignadas, escanersAsignados, modemsAsignados, cdAsignados;

    public Proceso(int ID, int tLlegada, int priority, int tProce, int mb,
                   int numImp, int numEsc, int numMod, int numCD, int memAsig){
        this.tiempoLlegada = tLlegada;
        this.prioridad = priority;
        this.tiempoProcesador = tProce;
        this.megas = mb;
        this.numImpresoras = numImp;
        this.numEscaners = numEsc;
        this.numModems = numMod;
        this.numCDs = numCD;
        this.id = ID;
        this.memoriaAsignada = memAsig;
        cdAsignados = new ArrayList<>();
        impresorasAsignadas = new ArrayList<>();
        escanersAsignados = new ArrayList<>();
        modemsAsignados = new ArrayList<>();

    }

    public int getTiempoLlegada() {
        return tiempoLlegada;
    }

    public void setTiempoLlegada(int tiempoLlegada) {
        this.tiempoLlegada = tiempoLlegada;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }

    public int getTiempoProcesador() {
        return tiempoProcesador;
    }

    public void setTiempoProcesador(int tiempoProcesador) {
        this.tiempoProcesador = tiempoProcesador;
    }

    public int getMegas() {
        return megas;
    }

    public void setMegas(int megas) {
        this.megas = megas;
    }

    public int getNumImpresoras() {
        return numImpresoras;
    }

    public void setNumImpresoras(int numImpresoras) {
        this.numImpresoras = numImpresoras;
    }

    public int getNumEscaners() {
        return numEscaners;
    }

    public void setNumEscaners(int numEscaners) {
        this.numEscaners = numEscaners;
    }

    public int getNumModems() {
        return numModems;
    }

    public void setNumModems(int numModems) {
        this.numModems = numModems;
    }

    public int getNumCDs() {
        return numCDs;
    }

    public void setNumCDs(int numCDs) {
        this.numCDs = numCDs;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMemoriaAsignada() {
        return memoriaAsignada;
    }

    public void setMemoriaAsignada(int memoriaAsignada) {
        this.memoriaAsignada = memoriaAsignada;
    }

    public String toString(){
        return "\nID: " + getId() + "\n Tiempo Llegada: " + getTiempoLlegada()+"___________________";
    }
}
