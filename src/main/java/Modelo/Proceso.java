package Modelo;

public class Proceso {
    int tiempoLlegada, prioridad, tiempoProcesador, megas, numImpresoras, numEscaners, numModems, numCDs;

    public Proceso(int tLlegada, int priority, int tProce, int mb, int numImp, int numEsc, int numMod, int numCD){
        this.tiempoLlegada = tLlegada;
        this.prioridad = priority;
        this.tiempoProcesador = tProce;
        this.megas = mb;
        this.numImpresoras = numImp;
        this.numEscaners = numEsc;
        this.numModems = numMod;
        this.numCDs = numCD;
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
}
