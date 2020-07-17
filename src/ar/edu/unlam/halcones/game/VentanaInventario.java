package ar.edu.unlam.halcones.game;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;

public class VentanaInventario extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = -3719990284810020688L;
	private JPanel contentPane;
    private JTable table;
    private static Object[][] items;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {

                    VentanaInventario frame = new VentanaInventario(items);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    private JFrame frame = new JFrame("Inventario");

    private String[] columnNames = {"Item", "Nombre (Cantidad)"};

    public VentanaInventario(Object[][] items) {

        TableModel model = new DefaultTableModel(items, columnNames) {
            /**
			 * 
			 */
			private static final long serialVersionUID = -5113261984204818661L;

			@Override
            public Class<?> getColumnClass(int column) {
                switch (column) {
                    case 1:
                        return String.class;
                    case 0:
                        return ImageIcon.class;
                    default:
                        return String.class;
                }
            }
        };
        JTable table = new JTable(model) {
            /**
			 * 
			 */
			private static final long serialVersionUID = 3358256221897447221L;

			@Override
            public int getRowHeight() {
                return super.getRowHeight() * 3;
            }

            @Override
            public Dimension getPreferredScrollableViewportSize() {
                return new Dimension(
                        (10 * super.getPreferredSize().width) / 4,
                        6 * this.getRowHeight());
            }
        };

        table.setAutoCreateRowSorter(true);
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new JScrollPane(table));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
