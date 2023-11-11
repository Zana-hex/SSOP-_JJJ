package Modelo;

public class Impresora {
    int idImpresora, procesoPropietario;
    String estado;

        public Impresora(int id, String state){
        this.idImpresora = id;
        this.estado = state;
    }

    public int getIdImpresora() {
        return idImpresora;
    }

    public void setIdImpresora(int idImpresora) {
        this.idImpresora = idImpresora;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getProcesoPropietario() {
        return procesoPropietario;
    }

    public void setProcesoPropietario(int procesoPropietario) {
        this.procesoPropietario = procesoPropietario;
    }
}
