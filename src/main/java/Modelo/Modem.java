package Modelo;

public class Modem {
    int idModem, procesoPropietario;
    String estado;

    public Modem(int id, String state){

        this.idModem = id;
        this.estado = state;

    }

    public int getProcesoPropietario() {
        return procesoPropietario;
    }

    public void setProcesoPropietario(int procesoPropietario) {
        this.procesoPropietario = procesoPropietario;
    }
}
