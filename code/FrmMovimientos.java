package code;

import javax.swing.*;

/**
 * Created by codehero on 30/04/17.
 */
public class FrmMovimientos {
    private JTable movimientos_jtable;
    public JPanel mainContainer;

    public FrmMovimientos() {
        Movimiento movimiento = new Movimiento();
        movimiento.cargaArchivoMovimientos();

        movimiento.mostrarMovimientos(movimientos_jtable);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Movimiento de productos");
        frame.setContentPane(new FrmMovimientos().mainContainer);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setSize(450,400);
        frame.setVisible(true);
    }
}
