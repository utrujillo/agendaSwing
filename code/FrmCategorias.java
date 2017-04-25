package code;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Created by codehero on 25/04/17.
 */
public class FrmCategorias {
    public JPanel mainContainer;
    private JTextField idCategoria_txt;
    private JButton agregarCategoria_btn;
    private JTable categories_jtable;
    private JButton actualizarCategoria_btn;
    private JButton eliminarCategoria_btn;
    private JTextField textField1;
    private JLabel message_label;

    public FrmCategorias(){
        System.out.println("Entro");
        String[] columnNames = {"idCategoria","Categoria"};
        Object[][] datos = {
            {"Juan", new Integer(25) },
            {"Sonia", new Integer(33) },
            {"Pedro", new Integer(42) },
        };

        categories_jtable = new JTable( datos, columnNames );
        categories_jtable.setPreferredScrollableViewportSize( new Dimension(500, 100));
        categories_jtable.setFillsViewportHeight(true);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Categorias");
        frame.setContentPane(new FrmCategorias().mainContainer);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(380,400);
        frame.setVisible(true);
    }
}
