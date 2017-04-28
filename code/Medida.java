package code;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

/**
 * Created by codehero on 28/04/17.
 */
public class Medida {
    private String nombreMedida;
    private Integer idMedida;

    // Variables Globales de la clase
    public File path = new File("/Users/codehero/Desktop");
    public String medidaFile = "medidas.txt";
    public ArrayList<Medida> medidasAL = new ArrayList<Medida>();

    // Getters y Setters
    public String getNombreMedida() {
        return nombreMedida;
    }

    public void setNombreMedida(String nombreMedida) {
        this.nombreMedida = nombreMedida;
    }

    public Integer getIdMedida() {
        return idMedida;
    }

    public void setIdMedida(Integer idMedida) {
        this.idMedida = idMedida;
    }

    // Carga el archivo de medidas al arrayList medidasAL
    public void cargaArchivoMedidas(){
        File archivo = new File(this.path, this.medidaFile);
        Integer line = 0;
        try{
            if(archivo.exists()){
                FileInputStream fis = new FileInputStream(archivo);
                Scanner sc = new Scanner(fis);

                while(sc.hasNext()){
                    String parts[] = sc.nextLine().split("\t");
                    //System.out.println( Arrays.toString(parts) );
                    if(parts.length == 2 && line >= 2){
                        Medida med = new Medida();
                        med.setIdMedida( Integer.parseInt(parts[0]) );
                        med.setNombreMedida( parts[1] );

                        medidasAL.add( med );
                    }
                    line++;
                }
                sc.close();
                System.out.println("Medidas cargadas correctamente");
            }
        }catch(IOException ex){
            System.out.printf("Error: %s", ex.toString());
        }
    }

    // Agregamos las medidas a nuestro JTable
    public void mostrarMedidas(JTable medidas_jtable){
        // Ordenamos las categorias
        this.ordenaMedidas();

        // Agregamos las categorias al tableModel para poder mostrarlas en el grid
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("idMedida");
        model.addColumn("Nombre Medida");

        for(Medida item : medidasAL){
            model.addRow( new Object[]{ item.getIdMedida(), item.getNombreMedida() } );
        }

        medidas_jtable.setModel(model);

    }

    // Agregamos las medidas al ArrayList categoriaAL
    public void altaMedida(Integer idMedida, String nombreMedida, JTable medidas_jtable){
        try {
            // Creamos el objeto catgoria y asignamos los valores enviados
            Medida medida = new Medida();
            medida.setIdMedida(idMedida);
            medida.setNombreMedida(nombreMedida);

            // Agregamos la nueva categoria al array List de categorias
            medidasAL.add(medida);
            // Guardamos la categoria en el archivo categoria.txt
            this.guardarMedidas();
            // Listamos nuevamente las categorias
            this.mostrarMedidas(medidas_jtable);

        } catch (Exception e) {
            System.out.println( e.getLocalizedMessage() );
        }
    }

    // Actualizamos la medida, dependiendo del ID buscado
    public void actualizaMedida(Integer idMedida, String nombreMedida, JTable medidas_jtable){
        try {

            Medida itemFound = this.buscaMedida(idMedida);

            if ( itemFound != null){

                // Actualizamos la medida si el id fue encontrado
                itemFound.setNombreMedida(nombreMedida);

                // Guardamos la medida en el archivo medida.txt
                this.guardarMedidas();
                // Listamos nuevamente las categorias
                this.mostrarMedidas(medidas_jtable);
                JOptionPane.showMessageDialog(null,
                        "La medida ha sido actualizada",
                        "Actualizar medida",
                        JOptionPane.INFORMATION_MESSAGE);
            }else{
                JOptionPane.showMessageDialog(null,
                        "Por seguridad el ID no puede ser cambiado",
                        "Actualizar medida",
                        JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            System.out.println( e.getLocalizedMessage() );
        }
    }

    // Eliminación de categoria, dependiendo del ID encontrado en idCategoria_txt
    public void eliminaMedida(Integer idMedida, JTable medidas_jtable){

        Boolean itemDeleted = false;

        for (int i = 0; i < medidasAL.size(); i++) {
            if( medidasAL.get(i).getIdMedida().equals(idMedida) ){
                medidasAL.remove(i);
                itemDeleted = true;
            }
        }

        if (itemDeleted) {

            // Guardamos la categoria en el archivo categoria.txt
            this.guardarMedidas();
            // Listamos nuevamente las categorias
            this.mostrarMedidas(medidas_jtable);
            JOptionPane.showMessageDialog(null,
                    "La medida ha sido eliminada",
                    "Eliminar medida",
                    JOptionPane.INFORMATION_MESSAGE);
        }else{
            JOptionPane.showMessageDialog(null,
                    "El ID buscado no fue encontrado",
                    "Eliminar medida",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // Se realiaza un ordenamiento del arrayList categoriasAL
    public void ordenaMedidas(){
        Collections.sort(medidasAL, new Comparator<Medida>()
        {
            public int compare(Medida m1, Medida m2){
                return Integer.valueOf(m1.getIdMedida()).compareTo(m2.getIdMedida());
            }
        });
    }

    // Se busca la medida dependiendo del ID
    public Medida buscaMedida(Integer id){
        for(Medida item : medidasAL){
            if( item.getIdMedida().equals(id) ){
                return item;
            }
        }
        return null;
    }

    // Guarda las medidas, en realidad guarda el arrayList medidasAL
    public void guardarMedidas(){
        PrintStream ps = null;
        FileOutputStream fos = null;

        File archivo = new File(this.path, this.medidaFile);

        try{
            // Borramos el archivo
            archivo.delete();

            // Ordenamos las categorias
            this.ordenaMedidas();

            // Guardamos categorias en el archivo
            fos = new FileOutputStream(archivo,true);
            ps = new PrintStream(fos);
            ps.println("idMedida\tMedida");
            ps.println("--------------------------------------");

            for(Medida item : medidasAL){
                ps.println(item.getIdMedida()
                        +"\t"+ item.getNombreMedida());
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

