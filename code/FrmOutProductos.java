package code;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created by codehero on 25/04/17.
 */
public class FrmOutProductos {
    public JPanel mainContainer;
    private JTextField srcProducto_txt;
    private JButton srcCodigo_btn;
    private JLabel descripcion_label;
    private JLabel categoria_label;
    private JLabel medida_label;
    private JLabel pVenta_label;
    private JButton registrar_btn;
    private JTextField cantidad_txt;
    private JLabel costo_label;
    private JButton frmLimpiar_btn;
    private JTable productosout_jtable;
    private JLabel pCompra_label;

    public FrmOutProductos() {

        // Creamos instancia a clase Productos para utilizar sus metodos
        Producto producto = new Producto();
        producto.cargaArchivoProductos();

        producto.mostrarProductos(productosout_jtable, 3);

        // Listener ejecutado cuando se presiona el boton limpiar
        frmLimpiar_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cleanFields();
            }
        });

        // Listener ejecutado cuando se presiona el boton buscar
        srcCodigo_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (srcProducto_txt.getText().length() > 0){
                    Producto itemFound = producto.buscaProducto( Integer.parseInt( srcProducto_txt.getText() ) );

                    if( itemFound != null ){
                        descripcion_label.setText( itemFound.getDescripcion().toString() );
                        categoria_label.setText( itemFound.getIdCategoria().toString() );
                        medida_label.setText( itemFound.getIdMedida().toString() );
                        pVenta_label.setText( itemFound.getpVenta().toString() );
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

        // Listener para cuando el usuario teclea la cantidad, se calcula automaticamente el costo
        cantidad_txt.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);

                if( !pVenta_label.getText().equals("") && !cantidad_txt.getText().equals("") ){
                    Float pVenta = Float.parseFloat( pVenta_label.getText() );
                    Integer cantidad = Integer.parseInt( cantidad_txt.getText() );
                    Float costoCalculado = cantidad * pVenta;
                    costo_label.setText( costoCalculado.toString() );
                }
            }
        });

        // Listener para cuando se presiona el boton registrar(venta de producto)
        registrar_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer codigo = Integer.parseInt( srcProducto_txt.getText() );
                Integer cantidad = Integer.parseInt( cantidad_txt.getText() );

                if( codigo > 0 && cantidad >= 0 ){

                    Producto itemFound = producto.buscaProducto( codigo );

                    if ( itemFound != null ){
                        producto.actualizaProducto( codigo, cantidad, productosout_jtable, 3 );
                        cleanFields();
                        // Agregamos el registro en la tabla de movimientos
                        Movimiento mov = new Movimiento();
                        mov.cargaArchivoMovimientos();

                        Float precio = itemFound.getpVenta() * cantidad;

                        mov.altaMovimiento(2, codigo, 0, cantidad, precio );
                    }else{
                        JOptionPane.showMessageDialog(mainContainer,
                                "El codigo a actualizar no fue encontrado",
                                "Actualizar producto",
                                JOptionPane.ERROR_MESSAGE);
                    }


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
        srcProducto_txt.setText("");
        descripcion_label.setText("");
        categoria_label.setText("");
        medida_label.setText("");
        cantidad_txt.setText("");
        pVenta_label.setText("");
        costo_label.setText("");
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Productos - salida");
        frame.setContentPane(new FrmOutProductos().mainContainer);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(620,400);
        frame.setVisible(true);
    }
}
