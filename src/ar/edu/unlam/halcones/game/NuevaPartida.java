package ar.edu.unlam.halcones.game;

import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class NuevaPartida extends JDialog {

	private static final long serialVersionUID = 1L;
	private JTextField txtNombre;
	private boolean iniciarJuego;
	private String archivo;
	private String nombreCharacter;
	private JComboBox<String> comboHistoria;

	public NuevaPartida(JFrame padre) {
		super(padre, "Nueva partida...", true);
		this.iniciarJuego = false;
		this.archivo = "";
		this.nombreCharacter = "";
		initialize();
	}

	private void initialize() {
		this.setResizable(false);
		this.setBounds(100, 100, 450, 149);
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		this.getContentPane().setLayout(new MigLayout("", "[][][][][][grow][grow]", "[][][]"));

		JLabel lblIngresoNombre = new JLabel("Ingrese su nombre:");
		this.getContentPane().add(lblIngresoNombre, "cell 0 0,alignx trailing");

		txtNombre = new JTextField();
		this.getContentPane().add(txtNombre, "cell 1 0 6 1,growx");
		txtNombre.setColumns(10);

		JLabel lblSeleccionHistoria = new JLabel("Seleccionar una historia:");
		this.getContentPane().add(lblSeleccionHistoria, "cell 0 1,alignx trailing");

		comboHistoria = new JComboBox<String>();
		comboHistoria.addItem("Sobreviviendo en epocas de Pandemia");
		comboHistoria.addItem("Pirata Fantasma");
		this.getContentPane().add(comboHistoria, "cell 1 1 6 1,growx");

		JButton btnIniciar = new JButton("Iniciar");
		btnIniciar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				iniciarPartida();
			}
		});
		this.getContentPane().add(btnIniciar, "cell 1 2");

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cerrarVentana();
			}
		});
		this.getContentPane().add(btnCancelar, "cell 2 2");
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	private void iniciarPartida() {
		if (this.txtNombre.getText().isEmpty()) {
			JOptionPane.showConfirmDialog(this, "Antes de iniciar la partida, primero debe ingresar su nombre.",
					"Atencion...", JOptionPane.CLOSED_OPTION, JOptionPane.WARNING_MESSAGE);
		} else if (JOptionPane.showConfirmDialog(this, "Desea iniciar la partida", "Confirmar...",
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE) == 0) {
			this.iniciarJuego = true;
			this.nombreCharacter = this.txtNombre.getText();
			if (this.comboHistoria.getSelectedIndex() == 1) {
				this.archivo = "piratasfantasmas.json";
			} else {
				this.archivo = "pandemia.json";
			}
			this.setVisible(false);
		}
	}

	private void cerrarVentana() {
		this.setVisible(false);
	}

	public boolean iniciaJuego() {
		return iniciarJuego;
	}

	public String getArchivo() {
		return archivo;
	}

	public String getNombreCharacter() {
		return nombreCharacter;
	}

}
