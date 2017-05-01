package code;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

/**
 * Created by codehero on 29/04/17.
 */
public class Producto {
    private Integer codigo;
    private String descripcion;
    private Integer idCategoria;
    private Integer idMedida;
    private Integer cantidad;
    private Float pCompra;
    private Float pVenta;
    private Float utilidad;

    // Variables Globales de la clase
    public File path = new File("/Users/codehero/Desktop");
    public String productFile = "productos.txt";
    public ArrayList<Producto> productosAL = new ArrayList<Producto>();

    // Getters y Setters
    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }

    public Integer getIdMedida() {
        return idMedida;
    }

    public void setIdMedida(Integer idMedida) {
        this.idMedida = idMedida;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Float getpCompra() {
        return pCompra;
    }

    public void setpCompra(Float pCompra) {
        this.pCompra = pCompra;
    }

    public Float getpVenta() {
        return pVenta;
    }

    public void setpVenta(Float pVenta) {
        this.pVenta = pVenta;
    }

    public Float getUtilidad() {
        return utilidad;
    }

    public void setUtilidad(Float utilidad) {
        this.utilidad = utilidad;
    }

    // carga el archivo de productos.txt al arrayList productosAL
    public void cargaArchivoProductos(){
        File archivo = new File(this.path, this.productFile);
        Integer line = 0;
        try{
            if(archivo.exists()){
                FileInputStream fis = new FileInputStream(archivo);
                Scanner sc = new Scanner(fis);

                while(sc.hasNext()){
                    String parts[] = sc.nextLine().split("\t");
                    //System.out.println( Arrays.toString(parts) );
                    if(parts.length == 8 && line >= 2){
                        Producto prod = new Producto();
                        prod.setCodigo( Integer.parseInt(parts[0]) );
                        prod.setDescripcion( parts[1] );
                        prod.setIdCategoria( Integer.parseInt(parts[3]) );
                        prod.setIdMedida( Integer.parseInt(parts[2]) );
                        prod.setCantidad( Integer.parseInt( parts[4] ) );
                        prod.setpCompra( Float.parseFloat( parts[5] ) );
                        prod.setpVenta( Float.parseFloat( parts[6] ) );
                        prod.setUtilidad( Float.parseFloat( parts[7]) );

                        productosAL.add( prod );
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

    // Agregamos los productos a nuestro JTable
    // Si paso type = 1 muestro solo los productos basicos codigo, descripcion, idCategoria, idMedida
    // Si paso type = 2 muestro toda la informacion del archivo productos.txt
    public void mostrarProductos(JTable jtable_display, Integer type){
        // Ordenamos los productos
        this.ordenaProductos();

        // Agregamos los productos al tableModel para poder mostrarlas en el grid
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Codigo");
        model.addColumn("Descripción");
        model.addColumn("idCategoria");
        model.addColumn("idMedida");

        if (type == 2){
            model.addColumn("Cantidad");
            model.addColumn("P. Compra");
            model.addColumn("P. Venta");
            model.addColumn("Utilidad");
        }

        if (type == 3){
            model.addColumn("Cantidad");
            model.addColumn("P. Venta");
        }

        for(Producto item : productosAL){
            if( type == 1 ){
                model.addRow( new Object[]{
                        item.getCodigo(),
                        item.getDescripcion(),
                        item.getIdCategoria(),
                        item.getIdMedida()
                } );
            }else if (type == 2){
                model.addRow( new Object[]{
                        item.getCodigo(),
                        item.getDescripcion(),
                        item.getIdCategoria(),
                        item.getIdMedida(),
                        item.getCantidad(),
                        item.getpCompra(),
                        item.getpVenta(),
                        item.getUtilidad()
                } );
            }else if (type == 3){
                model.addRow( new Object[]{
                        item.getCodigo(),
                        item.getDescripcion(),
                        item.getIdCategoria(),
                        item.getIdMedida(),
                        item.getCantidad(),
                        item.getpVenta(),
                } );
            }

        }

        jtable_display.setModel(model);

    }

    // Agregamos los productos al ArrayList productosAL
    public void altaProducto(Integer codigo, String descripcion, Integer idCategoria, Integer idMedida , JTable jtable_display ){

        try {
            Producto prod = new Producto();

            prod.setCodigo(codigo);
            prod.setDescripcion(descripcion);
            prod.setIdCategoria(idCategoria);
            prod.setIdMedida(idMedida);
            prod.setCantidad(0);
            prod.setpCompra(0f);
            prod.setpVenta(0f);
            prod.setUtilidad(0f);

            // Agregamos el nuevo producto al array List de productosAL
            productosAL.add(prod);
            // Guardamos el producto en el archivo productos.txt
            this.guardarProductos();
            // Listamos nuevamente los productos
            this.mostrarProductos( jtable_display, 1 );


        } catch (Exception e) {
            System.out.println( e.getLocalizedMessage() );
        }
    }

    // Actualizamos el producto, dependiendo del ID buscado
    public void actualizaProducto(Integer codigo, String descripcion, Integer idCategoria, Integer idMedida, Integer cantidad, Float pCompra, Float pVenta, Float utilidad, JTable jtable_display, Integer type){
        try {

            Producto itemFound = this.buscaProducto( codigo );

            if ( itemFound != null){

                // Actualizamos el producto si el id fue encontrado
                itemFound.setDescripcion(descripcion);
                itemFound.setIdCategoria(idCategoria);
                itemFound.setIdMedida(idMedida);
                itemFound.setCantidad(cantidad);
                itemFound.setpCompra(pCompra);
                itemFound.setpVenta(pVenta);
                itemFound.setUtilidad(utilidad);

                // Guardamos el producto en el archivo productos.txt
                this.guardarProductos();
                // Listamos nuevamente los productos
                this.mostrarProductos(jtable_display, type);
                JOptionPane.showMessageDialog(null,
                        "El producto ha sido actualizado",
                        "Actualizar producto",
                        JOptionPane.INFORMATION_MESSAGE);
            }else{
                JOptionPane.showMessageDialog(null,
                        "Por seguridad el ID no puede ser cambiado",
                        "Actualizar producto",
                        JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            System.out.println( e.getLocalizedMessage() );
        }
    }

    // Actualizamos el producto, dependiendo del ID buscado
    // Utilizando la tecnica de polimormisfo en el metodo actualizaProducto, solo actualiza la cantidad y la utilidad
    public void actualizaProducto(Integer codigo, Integer cantidad, JTable jtable_display, Integer type){
        try {

            Producto itemFound = this.buscaProducto( codigo );

            if ( itemFound != null){

                Integer cantidadActual = itemFound.getCantidad();

                // Verificamos si hay suficiente cantidad en el almacen para atender el pedido
                if( ( cantidadActual - cantidad ) >= 0 ){
                    Integer nuevaCantidad = cantidadActual - cantidad;
                    itemFound.setCantidad( nuevaCantidad );
                    Float utilidad = ( itemFound.getpVenta() - itemFound.getpCompra() ) * nuevaCantidad;
                    itemFound.setUtilidad(utilidad);

                    // Guardamos el producto en el archivo productos.txt
                    this.guardarProductos();
                    // Listamos nuevamente los productos
                    this.mostrarProductos(jtable_display, type);
                    JOptionPane.showMessageDialog(null,
                            "El producto ha sido actualizado",
                            "Actualizar producto",
                            JOptionPane.INFORMATION_MESSAGE);
                }else{
                    JOptionPane.showMessageDialog(null,
                            "Lo siento, no hay suficiente producto en stock para poder atender tu pedido",
                            "Salida de producto",
                            JOptionPane.ERROR_MESSAGE);
                }

            }else{
                JOptionPane.showMessageDialog(null,
                        "Por seguridad el ID no puede ser cambiado",
                        "Actualizar producto",
                        JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            System.out.println( e.getLocalizedMessage() );
        }
    }

    // Eliminación de producto, dependiendo del ID encontrado en codigo_txt
    public void eliminaProducto(Integer codigo, JTable jtable_display, Integer type){

        Boolean itemDeleted = false;

        for (int i = 0; i < productosAL.size(); i++) {
            if( productosAL.get(i).getCodigo().equals(codigo) ){
                productosAL.remove(i);
                itemDeleted = true;
            }
        }

        if (itemDeleted) {

            // Guardamos el producto en el archivo productos.txt
            this.guardarProductos();
            // Listamos nuevamente los productos
            this.mostrarProductos(jtable_display, type);
            JOptionPane.showMessageDialog(null,
                    "El producto ha sido eliminado",
                    "Eliminar producto",
                    JOptionPane.INFORMATION_MESSAGE);
        }else{
            JOptionPane.showMessageDialog(null,
                    "El ID buscado no fue encontrado",
                    "Eliminar producto",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // Se realiza un ordenamiento del arrayList productosAL
    public void ordenaProductos(){
        Collections.sort(productosAL, new Comparator<Producto>()
        {
            public int compare(Producto p1, Producto p2){
                return Integer.valueOf(p1.getCodigo()).compareTo(p2.getCodigo());
            }
        });
    }

    // Se busca el producto dependiendo del ID
    public Producto buscaProducto(Integer codigo){
        for(Producto item : productosAL){
            if( item.getCodigo().equals(codigo) ){
                return item;
            }
        }
        return null;
    }

    // Guarda las productos, en realidad guarda el arrayList productosAL
    public void guardarProductos(){
        PrintStream ps = null;
        FileOutputStream fos = null;

        File archivo = new File(this.path, this.productFile);

        try{
            // Borramos el archivo
            archivo.delete();

            // Ordenamos los productos
            this.ordenaProductos();

            // Guardamos productos en el archivo
            fos = new FileOutputStream(archivo,true);
            ps = new PrintStream(fos);
            ps.println("Codigo\tDescripción\tidCategoria\tidMedida\tCantidad\tP. Compra\tP. Venta\tUtilidad");
            ps.println("----------------------------------------------------------------------------------------------------------");

            for(Producto item : productosAL){
                ps.println(item.getCodigo()
                        +"\t"+ item.getDescripcion()
                        +"\t"+ item.getIdCategoria()
                        +"\t"+ item.getIdMedida()
                        +"\t"+ item.getCantidad()
                        +"\t"+ item.getpCompra()
                        +"\t"+ item.getpVenta()
                        +"\t"+ item.getUtilidad());
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
