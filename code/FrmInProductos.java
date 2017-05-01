package code;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by codehero on 25/04/17.
 */
public class FrmInProductos {
    public JPanel mainContainer;
    private JTextField srcCodigo_txt;
    private JButton srcProducto_btn;
    private JLabel descripcion_label;
    private JLabel categoria_label;
    private JLabel medida_label;
    private JTextField cantidad_txt;
    private JTextField pCompra_txt;
    private JTextField pVenta_txt;
    private JButton guardar_btn;
    private JTable productosin_jtable;
    private JButton frmLimpiar_btn;

    public FrmInProductos() {

        // Creamos instancia a clase Productos para utilizar sus metodos
        Producto producto = new Producto();
        producto.cargaArchivoProductos();

        producto.mostrarProductos(productosin_jtable,2);

        // Listener para cuando se presiona el boton buscar
        srcProducto_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (srcCodigo_txt.getText().length() > 0){
                    Producto itemFound = producto.buscaProducto( Integer.parseInt( srcCodigo_txt.getText() ) );

                    if( itemFound != null ){
                        descripcion_label.setText( itemFound.getDescripcion().toString() );
                        categoria_label.setText( itemFound.getIdCategoria().toString() );
                        medida_label.setText( itemFound.getIdMedida().toString() );
                        cantidad_txt.setText( itemFound.getCantidad().toString() );
                        pCompra_txt.setText( itemFound.getpCompra().toString() );
                        pVenta_txt.setText( itemFound.getpVenta().toString() );
                    }else{
                        JOptionPane.showMessageDialog(mainContainer,
                                "Producto no encontrado, intentar con otro Codigo",
                                "Buscar Producto",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }else{
                    JOptionPane.showMessageDialog(mainContainer,
                            "Especificar el ID a buscar",
                            "Buscar Producto",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Listener para cuando se presiona el boton limpiar
        frmLimpiar_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cleanFields();
            }
        });

        // Listener para cuando se presiona el boton guardar
        guardar_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Integer codigo = Integer.parseInt( srcCodigo_txt.getText() );
                String descripcion = descripcion_label.getText();
                Integer idCategoria = Integer.parseInt( categoria_label.getText() );
                Integer idMedida = Integer.parseInt( medida_label.getText() );
                Integer cantidad = Integer.parseInt( cantidad_txt.getText() );
                Float pCompra = Float.parseFloat( pCompra_txt.getText() );
                Float pVenta = Float.parseFloat( pVenta_txt.getText() );
                Float utilidad = ( pVenta - pCompra ) * cantidad;

                if( codigo > 0
                        && !descripcion.equals("")
                        && idCategoria > 0
                        && idMedida > 0
                        && cantidad >= 0
                        && pCompra >= 0
                        && pVenta >= 0)
                {

                    producto.actualizaProducto( codigo ,
                            descripcion,
                            idCategoria,
                            idMedida,
                            cantidad,
                            pCompra,
                            pVenta,
                            utilidad,
                            productosin_jtable,
                            2);
                    cleanFields();
                }else{
                    JOptionPane.showMessageDialog(mainContainer,
                            "No debe haber datos vacios",
                            "Actualizar producto",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

    }

    // Restauramos los valores iniciales del formulario
    public void cleanFields(){
        srcCodigo_txt.setText("");
        descripcion_label.setText("");
        categoria_label.setText("");
        medida_label.setText("");
        cantidad_txt.setText("");
        pCompra_txt.setText("");
        pVenta_txt.setText("");
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Entrada de Productos");
        frame.setContentPane(new FrmInProductos().mainContainer);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(620,400);
        frame.setVisible(true);
    }
}
