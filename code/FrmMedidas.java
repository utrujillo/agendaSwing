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
public class FrmMedidas {
    public JPanel mainContainer;
    private JTextField idMedida_txt;
    private JTextField medida_txt;
    private JButton agregarMedida_btn;
    private JButton actualizaMedida_btn;
    private JButton eliminaMedida_btn;
    private JTable medidas_jtable;

    public FrmMedidas() {
        Medida medida = new Medida();
        medida.cargaArchivoMedidas();

        medida.mostrarMedidas(medidas_jtable);

        // Listener - cuando se presiona el boton agregar
        agregarMedida_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idMed = idMedida_txt.getText();
                String med = medida_txt.getText();

                if (idMed.length() > 0 && med.length() > 0 ){
                    medida.altaMedida(Integer.parseInt(idMed), med, medidas_jtable);
                    idMedida_txt.setText("");
                    medida_txt.setText("");
                }else{
                    JOptionPane.showMessageDialog(mainContainer,
                            "No debe haber datos vacios",
                            "Alta de Medida",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Listener - cuando se presiona el boton actualizar
        actualizaMedida_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idMed = idMedida_txt.getText();
                String med = medida_txt.getText();

                if (idMed.length() > 0 && med.length() > 0 ){
                    medida.actualizaMedida(Integer.parseInt(idMed), med, medidas_jtable);
                    idMedida_txt.setText("");
                    medida_txt.setText("");
                }else{
                    JOptionPane.showMessageDialog(mainContainer,
                            "No debe haber datos vacios",
                            "Actualizacion de medida",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Listener - cuando se presiona el boton eliminar
        eliminaMedida_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idMed = idMedida_txt.getText();

                if (idMed.length() > 0 ){
                    medida.eliminaMedida(Integer.parseInt(idMed), medidas_jtable);
                    idMedida_txt.setText("");
                    medida_txt.setText("");
                }else{
                    JOptionPane.showMessageDialog(mainContainer,
                            "Especificar el ID de medida existente para eliminar",
                            "Eliminar medida",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Listener - cuando se presiona sobre el jtable
        medidas_jtable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                DefaultTableModel model = (DefaultTableModel) medidas_jtable.getModel();
                idMedida_txt.setText( model.getValueAt(medidas_jtable.getSelectedRow(), 0).toString() );
                medida_txt.setText( model.getValueAt(medidas_jtable.getSelectedRow(), 1).toString() );
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Medidas");
        frame.setContentPane(new FrmMedidas().mainContainer);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(420,400);
        frame.setVisible(true);
    }
}
