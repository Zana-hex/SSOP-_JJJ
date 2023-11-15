package Vista;

import Modelo.Observado;
import Modelo.Proceso;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;

public class TablaProcesos extends JFrame implements Observado {

    public static Queue<Proceso> vistaProcesos = new LinkedList<>();
    public static Proceso procesoEjecucion;
    private JTable tablaProcesos;
    private DefaultTableModel modeloTabla;

    public TablaProcesos() {

        // Crear las columnas de la tabla
        String[] columnas = {"ID", "Estatus", "Tiempo de Llegada", "Prioridad Inicial", "Prioridad Actual",
                "Tiempo Requerido", "Tiempo Restante", "Memoria Requerida",
                "Ubicación Memoria", "Impresoras Solicitadas", "Impresoras Asignadas",
                "Escáneres Solicitados", "Escáneres Asignados", "CDs Solicitados", "CDs Asignados", "Modems Solicitados",
                "Modems Asignados"};

        // Crear el modelo de la tabla
        modeloTabla = new DefaultTableModel(columnas, 0);

        // Crear la tabla con el modelo
        tablaProcesos = new JTable(modeloTabla);

        // Agregar la tabla a un JScrollPane para permitir el desplazamiento
        JScrollPane scrollPane = new JScrollPane(tablaProcesos);
        JTableHeader tableHeader = tablaProcesos.getTableHeader();
        JScrollPane headerScrollPane = new JScrollPane(tableHeader);
        setBounds(150, 350,1200 , 500);
        headerScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        //setExtendedState(JFrame.MAXIMIZED_BOTH);
        // Configuración del contenedor principal
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        // Configuración del contenedor principal
      //  add(headerScrollPane, BorderLayout.NORTH);


        // Configuración de la ventana
        //  setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setVisible(true);
    }


    public void agregarProceso(Proceso proceso) {
        StringBuilder bloques = new StringBuilder();
        StringBuilder impresoras = new StringBuilder();
        StringBuilder escaners = new StringBuilder();
        StringBuilder cds = new StringBuilder();
        StringBuilder modems = new StringBuilder();
        for (int bloque : proceso.bloquesAsignados) {
            bloques.append(" ").append(bloque);
        }
        ;
        for (int impresora : proceso.impresorasAsignadas) {
            impresoras.append(" ").append(impresora);
        }
        ;
        for (int escaner : proceso.escanersAsignados) {
            escaners.append(" ").append(escaner);
        }
        ;
        for (int cd : proceso.cdAsignados) {
            cds.append(" ").append(cd);
        }
        ;
        for (int modem : proceso.modemsAsignados) {
            modems.append(" ").append(modem);
        }
        ;

        Object[] fila = {proceso.getTiempoLlegada(), proceso.getEstado(), proceso.getTiempoLlegada(),
        proceso.getPrioridadInicial(), proceso.getPrioridad(), proceso.getTiempoProcesador(),
        proceso.getTiempoRestante(), proceso.getMegas(), bloques, proceso.getNumImpresoras(),
        impresoras, proceso.getNumEscaners(), escaners, proceso.getNumCDs(), cds, proceso.getNumModems(),
        modems};

        modeloTabla.addRow(fila);
        revalidate();
        repaint();


    }


    @Override
    public synchronized void actualizar() {
        modeloTabla.setRowCount(0);
        if (!vistaProcesos.isEmpty()){
            for (Proceso p : vistaProcesos) {
                if (!(p.getEstado() == null)) {
                    if (!p.getEstado().equals("Finalizado")) {
                        agregarProceso(p);
                    }
                }
            }
        }
    }

    public static Proceso getProcesoEjecucion() {
        return procesoEjecucion;
    }

    public static void setProcesoEjecucion(Proceso procesoEjecucion) {
        TablaProcesos.procesoEjecucion = procesoEjecucion;
    }
}
