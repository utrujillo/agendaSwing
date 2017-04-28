package code;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by codehero on 25/04/17.
 */
public class FrmCategorias {
    public JPanel mainContainer;
    private JTextField idCategoria_txt;
    private JButton agregarCategoria_btn;
    private JButton actualizarCategoria_btn;
    private JButton eliminarCategoria_btn;
    private JTextField categoria_txt;
    private JLabel message_label;
    private JTable categories_jtable;

    public FrmCategorias(){
        Categoria categoria = new Categoria();
        categoria.cargaArchivoCategorias();

        categoria.mostrarCategorias(categories_jtable);

        // Listener para cuando el boton agregar es presionado
        agregarCategoria_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idCat = idCategoria_txt.getText();
                String cat = categoria_txt.getText();

                if (idCat.length() > 0 && cat.length() > 0 ){
                    categoria.altaCategoria(Integer.parseInt(idCat), cat, categories_jtable);
                    idCategoria_txt.setText("");
                    categoria_txt.setText("");
                }else{
                    JOptionPane.showMessageDialog(mainContainer,
                            "No debe haber datos vacios",
                            "Alta de categoria",
                            JOptionPane.ERROR_MESSAGE);
                }

            }
        });

        // Listener para cuando se presiona sobre el grid del Jtable
        categories_jtable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                DefaultTableModel model = (DefaultTableModel) categories_jtable.getModel();
                idCategoria_txt.setText( model.getValueAt(categories_jtable.getSelectedRow(), 0).toString() );
                categoria_txt.setText( model.getValueAt(categories_jtable.getSelectedRow(), 1).toString() );
            }
        });

        // Listener para cuando se presiona el boton actualizar
        actualizarCategoria_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idCat = idCategoria_txt.getText();
                String cat = categoria_txt.getText();

                if (idCat.length() > 0 && cat.length() > 0 ){
                    categoria.actualizaCategoria(Integer.parseInt(idCat), cat, categories_jtable);
                    idCategoria_txt.setText("");
                    categoria_txt.setText("");
                }else{
                    JOptionPane.showMessageDialog(mainContainer,
                            "No debe haber datos vacios",
                            "Actualizacion de categoria",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        eliminarCategoria_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idCat = idCategoria_txt.getText();

                if (idCat.length() > 0 ){
                    categoria.eliminaCategoria(Integer.parseInt(idCat), categories_jtable);
                    idCategoria_txt.setText("");
                    categoria_txt.setText("");
                }else{
                    JOptionPane.showMessageDialog(mainContainer,
                            "Especificar el ID de categoria existente para eliminar",
                            "Eliminar categoria",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Categorias");
        frame.setContentPane(new FrmCategorias().mainContainer);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(420,400);
        frame.setVisible(true);
    }
}
