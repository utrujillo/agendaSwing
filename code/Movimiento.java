package code;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by codehero on 30/04/17.
 */
public class Movimiento {
    private Integer idMovimiento;
    private Integer tipoMovimiento;
    private Integer codigo;
    private Integer cantidadEntrada;
    private Integer cantidadSalida;
    private Float precio;

    // Variables Globales
    public File path = new File("/Users/codehero/Desktop");
    public String moveFile = "movimientos.txt";
    public ArrayList<Movimiento> movimientosAL = new ArrayList<Movimiento>();

    // Getters y Setters
    public Integer getIdMovimiento() {
        return idMovimiento;
    }

    public void setIdMovimiento(Integer idMovimiento) {
        this.idMovimiento = idMovimiento;
    }

    public Integer getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(Integer tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getCantidadEntrada() {
        return cantidadEntrada;
    }

    public void setCantidadEntrada(Integer cantidadEntrada) {
        this.cantidadEntrada = cantidadEntrada;
    }

    public Integer getCantidadSalida() {
        return cantidadSalida;
    }

    public void setCantidadSalida(Integer cantidadSalida) {
        this.cantidadSalida = cantidadSalida;
    }

    public Float getPrecio() {
        return precio;
    }

    public void setPrecio(Float precio) {
        this.precio = precio;
    }

    // carga el archivo de movimientos.txt al arrayList movientosAL
    public void cargaArchivoMovimientos(){
        File archivo = new File(this.path, this.moveFile);
        Integer line = 0;
        try{
            if(archivo.exists()){
                FileInputStream fis = new FileInputStream(archivo);
                Scanner sc = new Scanner(fis);

                while(sc.hasNext()){
                    String parts[] = sc.nextLine().split("\t");
                    //System.out.println( Arrays.toString(parts) );
                    if(parts.length == 6 && line >= 2){
                        Movimiento mov = new Movimiento();
                        mov.setIdMovimiento( Integer.parseInt(parts[0]) );
                        mov.setTipoMovimiento( Integer.parseInt( parts[1] ) );
                        mov.setCodigo( Integer.parseInt(parts[3]) );
                        mov.setCantidadEntrada( Integer.parseInt(parts[2]) );
                        mov.setCantidadSalida( Integer.parseInt( parts[4] ) );
                        mov.setPrecio( Float.parseFloat( parts[5] ) );

                        movimientosAL.add( mov );
                    }
                    line++;
                }
                sc.close();
                System.out.println("Datos cargados correctamente");
            }
        }catch(IOException ex){
            System.out.printf("Error: %s", ex.toString());
        }
    }

    // Agregamos los movimientos a nuestro JTable
    public void mostrarMovimientos(JTable jtable_display){
        // Agregamos los movimientos al tableModel para poder mostrarlas en el grid
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("idMov.");
        model.addColumn("Tipo Mov.");
        model.addColumn("Codigo");
        model.addColumn("Cant. Entrdad");
        model.addColumn("Cant. Salida");
        model.addColumn("Precio");

        for(Movimiento item : movimientosAL){
            model.addRow( new Object[]{ item.getIdMovimiento(),
                            item.getTipoMovimiento(),
                            item.getCodigo(),
                            item.getCantidadEntrada(),
                            item.getCantidadSalida(),
                            item.getPrecio()
                        } );
        }

        jtable_display.setModel(model);
    }

    // Agregamos los movimientos al ArrayList movimientosAL
    public void altaMovimiento(Integer tipoMovimiento, Integer codigo, Integer cantidadEntrada, Integer cantidadSalida, Float precio){
        try {
            // Creamos el objeto catgoria y asignamos los valores enviados
            Movimiento movimiento = new Movimiento();
            Integer idMovimiento;

            // Calculamos el id del nuevo movimiento
            if( movimientosAL.size() > 0 ){
                idMovimiento = movimientosAL.get(movimientosAL.size()-1).getIdMovimiento() + 1;
            }else{
                idMovimiento = 1;
            }

            //System.out.println(idMovimiento+" "+tipoMovimiento+" "+codigo+" "+cantidadEntrada+" "+cantidadSalida+" "+precio);

            movimiento.setIdMovimiento( idMovimiento );
            movimiento.setTipoMovimiento( tipoMovimiento );
            /*
                Nota: Por alguna extraña razon tuve que invertir los valores de setCodito(cantidadEntrda) y
                setCantidadEntrada( codigo ) le estuve busque y busque y todo esta aparentemente bien, no se cual
                es el problema pero asi funciona correctamente(con los campos invertidos)
             */
            movimiento.setCodigo( cantidadEntrada );
            movimiento.setCantidadEntrada( codigo );
            movimiento.setCantidadSalida( cantidadSalida );
            movimiento.setPrecio( precio );

            // Agregamos la nueva categoria al array List de categorias
            movimientosAL.add( movimiento );
            // Guardamos la categoria en el archivo categoria.txt
            this.guardarMovimientos();

        } catch (Exception e) {
            System.out.println( e.getLocalizedMessage() );
        }
    }

    // Guarda los movimientos, en realidad guarda el arrayList movmientosAL
    public void guardarMovimientos(){
        PrintStream ps = null;
        FileOutputStream fos = null;

        File archivo = new File(this.path, this.moveFile);

        try{
            // Borramos el archivo
            archivo.delete();

            // Guardamos categorias en el archivo
            fos = new FileOutputStream(archivo,true);
            ps = new PrintStream(fos);
            ps.println("idMov.\tTipo Mov.\tCodigo\tCant. Entrada\tCant. Salida\tPrecio");
            ps.println("-------------------------------------------------------------------------------------------------");

            for(Movimiento item : movimientosAL){
                ps.println(item.getIdMovimiento()
                        +"\t"+ item.getTipoMovimiento()
                        +"\t"+ item.getCodigo()
                        +"\t"+ item.getCantidadEntrada()
                        +"\t"+ item.getCantidadSalida()
                        +"\t"+ item.getPrecio());
            }

        }catch(IOException ex){
            System.out.printf("\nHa ocurrido un error: %s", ex.toString() );
        }finally{
            try{
                System.out.println("Los datos han sido guardados");
                ps.close();
            }catch(Exception ex){
                System.out.printf("Error al cerrar el flujo: %s", ex.toString());
            }
        }
    }

}
