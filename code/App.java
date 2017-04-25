package code;

import javax.swing.*;

/**
 * Created by codehero on 25/04/17.
 */
public class App {
    public JPanel panelMain;
    private JLabel welcomeLabel;

    public JMenuBar menubar;
    public JMenu sistema, movimientos;
    public JMenuItem salir, subcat, submed, subprod, subent, subsal, submov;

    public static void main(String[] args) {
        new App().createWindow();
    }

    public void createWindow(){
        JFrame mainframe = new JFrame("Agenda - App");
        mainframe.setContentPane( new App().panelMain );
        mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainframe.setSize(300, 400);

        // Agregando el menu
        menubar = new JMenuBar();
        mainframe.setJMenuBar(menubar);

        // Agregando las opciones del menu - sistema
        sistema = new JMenu("Sistema");
        menubar.add(sistema);
            salir = new JMenuItem("salir");

            sistema.add(salir);

        // Agregando las opciones del menu - movimientos
        movimientos = new JMenu("Movimientos");
        menubar.add(movimientos);
            subcat = new JMenuItem("Categorias");
            submed = new JMenuItem("Medidas");
            subprod = new JMenuItem("Productos");
            subent = new JMenuItem("Entradas");
            subsal = new JMenuItem("Salidas");
            submov = new JMenuItem("Movimientos");

            movimientos.add(subcat);
            movimientos.add(submed);
            movimientos.add(subprod);
            movimientos.add(subent);
            movimientos.add(subsal);
            movimientos.add(submov);

        mainframe.setVisible(true);
    }
}
