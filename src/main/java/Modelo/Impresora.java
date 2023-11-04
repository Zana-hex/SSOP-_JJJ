package Modelo;

public class Impresora {
    int idImpresora;
    String estado;

        public Impresora(int id, String state){
        this.idImpresora = id;
        this.estado = state;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
