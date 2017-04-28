package code;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by codehero on 28/04/17.
 */
public class Categoria {
    private String nombreCateogoria;
    private Integer idCategoria;

    // Variables Globales de la clase
    public File path = new File("/Users/codehero/Desktop");
    public String categoryFile = "categorias.txt";
    public ArrayList<Categoria> categoriasAL = new ArrayList<Categoria>();

    // Getters y Setters
    public Integer getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombreCateogoria() {
        return nombreCateogoria;
    }

    public void setNombreCateogoria(String nombreCateogoria) {
        this.nombreCateogoria = nombreCateogoria;
    }

    // carga el archivo de categorias al arrayList categoriasAL
    public void cargaArchivoCategorias(){
        File archivo = new File(this.path, this.categoryFile);
        Integer line = 0;
        try{
            if(archivo.exists()){
                FileInputStream fis = new FileInputStream(archivo);
                Scanner sc = new Scanner(fis);

                while(sc.hasNext()){
                    String parts[] = sc.nextLine().split("\t");
                    //System.out.println( Arrays.toString(parts) );
                    if(parts.length == 2 && line >= 2){
                        Categoria cat = new Categoria();
                        cat.setIdCategoria( Integer.parseInt(parts[0]) );
                        cat.setNombreCateogoria( parts[1] );

                        categoriasAL.add( cat );
                    }
                    line++;
                }
                sc.close();
                System.out.println("Categorias cargadas correctamente");
            }
        }catch(IOException ex){
            System.out.printf("Error: %s", ex.toString());
        }
    }

    // Agregamos las categorias a nuestro JTable
    public void mostrarCategorias(JTable categories_jtable){
        // Ordenamos las categorias
        this.ordenaCategorias();

        // Agregamos las categorias al tableModel para poder mostrarlas en el grid
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("idCategoria");
        model.addColumn("Nombre Categoría");

        for(Categoria item : categoriasAL){
            model.addRow( new Object[]{ item.getIdCategoria(), item.getNombreCateogoria() } );
        }

        categories_jtable.setModel(model);

    }

    // Agregamos las categorias al ArrayList categoriaAL
    public void altaCategoria(Integer idCategoria, String nombreCategoria, JTable categories_jtable){
        try {
            // Creamos el objeto catgoria y asignamos los valores enviados
            Categoria categoria = new Categoria();
            categoria.setIdCategoria(idCategoria);
            categoria.setNombreCateogoria(nombreCategoria);

            // Agregamos la nueva categoria al array List de categorias
            categoriasAL.add(categoria);
            // Guardamos la categoria en el archivo categoria.txt
            this.guardarCategorias();
            // Listamos nuevamente las categorias
            this.mostrarCategorias(categories_jtable);

        } catch (Exception e) {
            System.out.println( e.getLocalizedMessage() );
        }
    }

    // Actualizamos la categoria, dependiendo del ID buscado
    public void actualizaCategoria(Integer idCategoria, String nombreCategoria, JTable categories_jtable){
        try {

            Categoria itemFound = this.buscaCategoria(idCategoria);

            if ( itemFound != null){

                // Actualizamos la categoria si el id fue encontrado
                itemFound.setNombreCateogoria(nombreCategoria);

                // Guardamos la categoria en el archivo categoria.txt
                this.guardarCategorias();
                // Listamos nuevamente las categorias
                this.mostrarCategorias(categories_jtable);
                JOptionPane.showMessageDialog(null,
                        "La categoría ha sido actualizada",
                        "Actualizar categoría",
                        JOptionPane.INFORMATION_MESSAGE);
            }else{
                JOptionPane.showMessageDialog(null,
                        "Por seguridad el ID no puede ser cambiado",
                        "Actualizar categoría",
                        JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            System.out.println( e.getLocalizedMessage() );
        }
    }

    // Eliminación de categoria, dependiendo del ID encontrado en idCategoria_txt
    public void eliminaCategoria(Integer idCategoria, JTable categories_jtable){

        Boolean itemDeleted = false;

        for (int i = 0; i < categoriasAL.size(); i++) {
            if( categoriasAL.get(i).getIdCategoria().equals(idCategoria) ){
                categoriasAL.remove(i);
                itemDeleted = true;
            }
        }

        if (itemDeleted) {

            // Guardamos la categoria en el archivo categoria.txt
            this.guardarCategorias();
            // Listamos nuevamente las categorias
            this.mostrarCategorias(categories_jtable);
            JOptionPane.showMessageDialog(null,
                    "La categoría ha sido eliminada",
                    "Eliminar categoría",
                    JOptionPane.INFORMATION_MESSAGE);
        }else{
            JOptionPane.showMessageDialog(null,
                    "El ID buscado no fue encontrado",
                    "Eliminar categoría",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // Se realiaza un ordenamiento del arrayList categoriasAL
    public void ordenaCategorias(){
        Collections.sort(categoriasAL, new Comparator<Categoria>()
        {
            public int compare(Categoria c1, Categoria c2){
                return Integer.valueOf(c1.getIdCategoria()).compareTo(c2.getIdCategoria());
            }
        });
    }

    // Se busca la categoria dependiendo del ID
    public Categoria buscaCategoria(Integer id){
        for(Categoria item : categoriasAL){
            if( item.getIdCategoria().equals(id) ){
                return item;
            }
        }
        return null;
    }

    // Guarda las categorias, en realidad guarda el arrayList categoriaAL
    public void guardarCategorias(){
        PrintStream ps = null;
        FileOutputStream fos = null;

        File archivo = new File(this.path, this.categoryFile);

        try{
            // Borramos el archivo
            archivo.delete();

            // Ordenamos las categorias
            this.ordenaCategorias();

            // Guardamos categorias en el archivo
            fos = new FileOutputStream(archivo,true);
            ps = new PrintStream(fos);
            ps.println("idCategoria\tCategoría");
            ps.println("--------------------------------------");

            for(Categoria item : categoriasAL){
                ps.println(item.getIdCategoria()
                        +"\t"+ item.getNombreCateogoria());
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
