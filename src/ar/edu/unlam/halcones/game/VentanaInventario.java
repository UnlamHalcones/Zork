package ar.edu.unlam.halcones.game;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.text.StyledEditorKit.ForegroundAction;

import ar.edu.unlam.halcones.entities.Item;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
public class VentanaInventario extends JFrame {

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
    
	private Icon errorIcon = (Icon) UIManager.getIcon("OptionPane.errorIcon");
    private Icon infoIcon = (Icon) UIManager.getIcon("OptionPane.informationIcon");
    private Icon warnIcon = (Icon) UIManager.getIcon("OptionPane.warningIcon");
	
    private String[] columnNames = {"Item", "Nombre"};
    
    // -------------------  ----------- -------------

    private static class LabelIcon {

        ImageIcon icon;
        //String label;

        public LabelIcon(ImageIcon icon/*,String label*/) {
            this.icon = icon;
            //this.label = label;
        }
    }

    private static class LabelIconRenderer extends DefaultTableCellRenderer {

        public LabelIconRenderer() {
            setHorizontalTextPosition(JLabel.CENTER);
            setVerticalTextPosition(JLabel.BOTTOM);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object
            value, boolean isSelected, boolean hasFocus, int row, int col) {
            JLabel r = (JLabel) super.getTableCellRendererComponent(
                table, value, isSelected, hasFocus, row, col);
            setIcon(((LabelIcon) value).icon);
            //setText(((LabelIcon) value).label);
            return r;
        }
    }
    
    
    // -------------------  ----------- -------------

    public VentanaInventario(Object[][] items) { 	
    	
    	TableModel model = new DefaultTableModel(items, columnNames) {
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
            @Override
            public int getRowHeight() {
                return super.getRowHeight() + infoIcon.getIconHeight();
            }

            @Override
            public Dimension getPreferredScrollableViewportSize() {
                return new Dimension(
                    (5 * super.getPreferredSize().width) / 4,
                    4 * this.getRowHeight());
            }
        };
        
        table.setAutoCreateRowSorter(true);
        table.setDefaultRenderer(LabelIcon.class, new LabelIconRenderer());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new JScrollPane(table));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
