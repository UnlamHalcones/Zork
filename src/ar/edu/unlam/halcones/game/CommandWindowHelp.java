package ar.edu.unlam.halcones.game;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

import net.miginfocom.swing.MigLayout;

public class CommandWindowHelp extends JDialog {

	private static final long serialVersionUID = -3405073402411423038L;

	public CommandWindowHelp(JFrame padre) {
		super(padre,"Ayuda...",true);
		getContentPane().setLayout(new MigLayout("", "[][]", "[][][][][][][][][][][][][][][][][][]"));
		
		this.addWindowListener( new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
        		salirDelJuego();
            }
        } );
		
		JLabel lblNewLabel_4 = new JLabel("Todos los comandos siempre empiezan con una [accion] seguido de:");
		getContentPane().add(lblNewLabel_4, "cell 0 0 1 2,grow");
		
		JLabel lblNewLabel_12 = new JLabel("");
		getContentPane().add(lblNewLabel_12, "cell 0 2");
		
		JLabel lblNewLabel_5 = new JLabel("una [location]");
		getContentPane().add(lblNewLabel_5, "cell 0 3");
		
		JLabel lblNewLabel_6 = new JLabel("un [item]");
		getContentPane().add(lblNewLabel_6, "cell 0 4");
		
		JLabel lblNewLabel_7 = new JLabel("un [npc]");
		getContentPane().add(lblNewLabel_7, "cell 0 5");
		
		JLabel lblNewLabel_8 = new JLabel("o un [place].");
		getContentPane().add(lblNewLabel_8, "cell 0 6");
		
		JLabel lblNewLabel_13 = new JLabel("");
		getContentPane().add(lblNewLabel_13, "cell 0 7");
		
		JLabel lblNewLabel_10 = new JLabel("");
		getContentPane().add(lblNewLabel_10, "cell 0 8");
		
		JLabel lblNewLabel_9 = new JLabel("Algunos comandos de ejemplos pueden ser:");
		getContentPane().add(lblNewLabel_9, "cell 0 9");
		
		JLabel lblNewLabel = new JLabel("> ver alrededor (muestra lo que hay en tu entorno).");
		getContentPane().add(lblNewLabel, "cell 0 10 1 2");
		
		JLabel lblNewLabel_3 = new JLabel("> ver vida (muestra la cantidad de puntos de vida que llevas).");
		getContentPane().add(lblNewLabel_3, "cell 0 12");
		
		JLabel lblNewLabel_11 = new JLabel("> ver inventario (para ver los items que posees en tu inventario).");
		getContentPane().add(lblNewLabel_11, "cell 0 13");
		
		JLabel lblIrAl = new JLabel("> ir [location] (puedes moverte de norte/sur/este/oeste).");
		getContentPane().add(lblIrAl, "cell 0 14");
		
		JLabel lblNewLabel_1 = new JLabel("> agarrar [item] (puedes agarrar un item del lugar donde te encuentres).");
		getContentPane().add(lblNewLabel_1, "cell 0 15");
		
		JLabel lblNewLabel_2 = new JLabel("> dar [item] al [npc] (para poder interactuar con personajes no jugables).");
		getContentPane().add(lblNewLabel_2, "cell 0 16");
		initialize();
	}
	
	private void initialize() {
		this.setBounds(100, 100, 493, 336);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setVisible(true);
	}
	
	private void salirDelJuego() {
		this.setVisible(false);
	}

}
