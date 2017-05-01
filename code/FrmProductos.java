package code;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by codehero on 25/04/17.
 */
public class FrmProductos {
    public JPanel mainContainer;
    private JTextField codigo_txt;
    private JTextField descripcion_txt;
    private JComboBox categoria_combo;
    private JComboBox medida_combo;
    private JButton agregarProducto_btn;
    private JTable creaproductos_jtable;
    private JButton actualizaProducto_btn;
    private JButton eliminaProducto_btn;
    private JButton frmLimpiar_btn;

    public FrmProductos() {
        // Cargamos el JComboBox - categorias desde el archivo categorias.txt
        Categoria categoria = new Categoria();
        categoria.cargaArchivoCategorias();
        categoria_combo.addItem(new ComboItem(0, "- Seleccionar "));
        for(Categoria catItem : categoria.categoriasAL){
            categoria_combo.addItem(new ComboItem(catItem.getIdCategoria(), catItem.getNombreCateogoria()));
        }

        // Cargamos el JComboBox - medidas desde el archivo medids.txt
        Medida medida = new Medida();
        medida.cargaArchivoMedidas();
        medida_combo.addItem(new ComboItem(0, "- Seleccionar -"));
        for(Medida medItem : medida.medidasAL){
            medida_combo.addItem(new ComboItem(medItem.getIdMedida(), medItem.getNombreMedida()));
        }

        // Creamos instancia a clase Productos para utilizar sus metodos
        Producto producto = new Producto();
        producto.cargaArchivoProductos();

        producto.mostrarProductos(creaproductos_jtable,1);

        // Evento ejectuado al seleccionar el boton seleccionar
        agregarProducto_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!codigo_txt.getText().equals("")
                        && !descripcion_txt.getText().equals("")
                        && medida_combo.getSelectedIndex() > 0
                        && categoria_combo.getSelectedIndex() > 0)
                {
                    /*Object itemCat = categoria_combo.getSelectedItem();
                    Integer idCategoria = ((ComboItem)itemCat).getId();*/
                    producto.altaProducto(Integer.parseInt(codigo_txt.getText()),
                            descripcion_txt.getText(),
                            categoria_combo.getSelectedIndex(),
                            medida_combo.getSelectedIndex(),
                            creaproductos_jtable);
                    cleanFields();
                }else{
                    JOptionPane.showMessageDialog(mainContainer,
                            "No debe haber datos vacios",
                            "Alta de producto",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Listener para cuando se presiona sobre el grid del Jtable
        creaproductos_jtable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                DefaultTableModel model = (DefaultTableModel) creaproductos_jtable.getModel();
                String idCategoria = model.getValueAt(creaproductos_jtable.getSelectedRow(), 2).toString();
                String idMedida = model.getValueAt(creaproductos_jtable.getSelectedRow(), 3).toString();

                categoria_combo.setSelectedIndex( Integer.parseInt( idCategoria ) );
                descripcion_txt.setText( model.getValueAt(creaproductos_jtable.getSelectedRow(), 1).toString() );
                codigo_txt.setText( model.getValueAt(creaproductos_jtable.getSelectedRow(), 0).toString() );
                medida_combo.setSelectedIndex( Integer.parseInt( idMedida ) );

            }
        });

        // Listener para cuando se presiona sobre el boton actualizar
        actualizaProducto_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String codigo = codigo_txt.getText();
                String descripcion = descripcion_txt.getText();
                Integer idCategoria = categoria_combo.getSelectedIndex();
                Integer idMedida = medida_combo.getSelectedIndex();

                if(!codigo_txt.getText().equals("")
                        && !descripcion_txt.getText().equals("")
                        && medida_combo.getSelectedIndex() > 0
                        && categoria_combo.getSelectedIndex() > 0)
                {

                    producto.actualizaProducto(Integer.parseInt( codigo_txt.getText() ),
                                                descripcion_txt.getText(),
                                                categoria_combo.getSelectedIndex(),
                                                medida_combo.getSelectedIndex(),
                                                0,
                                                0f,
                                                0f,
                                                0f,
                                                creaproductos_jtable,
                                                1);
                    cleanFields();
                }else{
                    JOptionPane.showMessageDialog(mainContainer,
                            "No debe haber datos vacios",
                            "Actualizar producto",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Listener para cuando se presiona sobre el boton eliminar
        eliminaProducto_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String codigo = codigo_txt.getText();

                if (codigo.length() > 0 ){
                    producto.eliminaProducto(Integer.parseInt(codigo), creaproductos_jtable, 1);
                    cleanFields();
                }else{
                    JOptionPane.showMessageDialog(mainContainer,
                            "Especificar el ID de producto existente para eliminar",
                            "Eliminar producto",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Listener para cuando se presiona sobre el boton limpiar
        frmLimpiar_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cleanFields();
            }
        });
    }

    // Restauramos los valores iniciales del formulario
    public void cleanFields(){
        codigo_txt.setText("");
        descripcion_txt.setText("");
        categoria_combo.setSelectedIndex(0);
        medida_combo.setSelectedIndex(0);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Productos");
        frame.setContentPane(new FrmProductos().mainContainer);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(420,400);
        frame.setVisible(true);
    }
}
