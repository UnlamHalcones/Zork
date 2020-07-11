package ar.edu.unlam.halcones.game;

import javax.swing.JFrame;
import net.miginfocom.swing.MigLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import ar.edu.unlam.halcones.archivo.GeneradorDeGame;
import ar.edu.unlam.halcones.archivo.LectorDiccionarioCSV;
import ar.edu.unlam.halcones.entities.Game;
import ar.edu.unlam.halcones.entities.INombrable;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Map;

public class RunnableGame extends JFrame {

	private final static String INVALIDCOMMAND = "No entendi lo que ingresaste. Intenta de nuevo por favor.";

	private static final long serialVersionUID = 1L;
	private JTextArea txtHistoria;
	private JTextField txtComando;
	private JScrollPane scrollPane;
	private JMenuBar menuBar;
	private JMenu mnPartida;
	private JMenuItem mntmNueva;
	private JMenuItem mntmGuardar;
	private JMenuItem mntmSalir;
	private JLabel lblComando;
	private NuevaPartida nuevaPartida;
	private Game game = null;
	private Interprete_game interprete;
	private Map<String, String> verbos;
	private GeneradorDeGame generador;

	public RunnableGame() {
		initialize();
	}

	private void initialize() {
		this.setTitle("Bienvenido a Zork!!!");
		this.setBounds(100, 100, 800, 450);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(new MigLayout("", "[grow]", "[grow][][][][][][][][][][grow][][][]"));
		this.setLocationRelativeTo(null);

		scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.getContentPane().add(scrollPane, "cell 0 10 1 2,grow");

		txtHistoria = new JTextArea();
		txtHistoria.setText("");
		txtHistoria.setEditable(false);
		txtHistoria.setLineWrap(true);
		txtHistoria.setWrapStyleWord(true);
		txtHistoria.setFont(new Font("Dialog", Font.PLAIN, 12));
		txtHistoria.setForeground(Color.GREEN);
		txtHistoria.setBackground(Color.BLACK);
		scrollPane.setViewportView(txtHistoria);

		lblComando = new JLabel("\u00BFQu\u00E9 quieres hacer? Escribe algo:");
		getContentPane().add(lblComando, "cell 0 12");

		txtComando = new JTextField();
		txtComando.setCaretColor(Color.GREEN);
		txtComando.setEditable(false);
		txtComando.setBackground(Color.BLACK);
		txtComando.setFont(new Font("Dialog", Font.PLAIN, 14));
		txtComando.setForeground(Color.GREEN);
		txtComando.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					enviarComando();
				}
			}
		});
		this.getContentPane().add(txtComando, "cell 0 13,growx");
		txtComando.setColumns(10);

		menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);

		mnPartida = new JMenu("Partida");
		menuBar.add(mnPartida);

		mntmNueva = new JMenuItem("Nueva...");
		mntmNueva.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				nuevaPartida();
			}
		});
		mnPartida.add(mntmNueva);

		mntmGuardar = new JMenuItem("Guardar...");
		mntmGuardar.setEnabled(false);
		mnPartida.add(mntmGuardar);

		mntmSalir = new JMenuItem("Salir");
		mntmSalir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				System.exit(0);
			}
		});
		mnPartida.add(mntmSalir);
	}

	private void nuevaPartida() {
		nuevaPartida = new NuevaPartida(this);
		if (nuevaPartida.iniciaJuego()) {
			generador = new GeneradorDeGame();
			try {
				game = generador.generarEntornoDeJuego(nuevaPartida.getArchivo());
				game.generarInteractuables();
				mntmNueva.setEnabled(false);
				mntmGuardar.setEnabled(true);
				interprete = new Interprete_game();
				verbos = LectorDiccionarioCSV.leerDiccionario();
				mostrarSalida(game.getWelcome());
				txtComando.setEditable(true);
				txtComando.setFocusable(true);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		new RunnableGame().setVisible(true);
	}

	private void enviarComando() {
		String input = "";
		String verbo = "";
		String primerSustantivo = "";
		String segundoSustantivo = "";

		if (txtComando.getText().isEmpty()) {
		} else if (txtComando.getText().equals("salir")) {
			limpiarComando();
			if (JOptionPane.showConfirmDialog(this, "Realmente quieres abandonar el juego sin antes guardar la partida",
					"Atención...", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == 0) {
				finalizarGame();
			}
		} else if (!txtComando.getText().contains(" ")) {
			mostrarSalida(INVALIDCOMMAND);
			limpiarComando();
		} else {

			input = txtComando.getText().toLowerCase();
			verbo = input.substring(0, input.indexOf(" "));
			input = input.replace(verbo, "");

			if (!verbos.containsKey(verbo)) {
				mostrarSalida(INVALIDCOMMAND);
				limpiarComando();
			} else {
				verbo = verbos.get(verbo);

				int indexPrimerEncontrado = 0;
				int indexSegundoEncontrado = 0;

				String primerEncontrado = "";
				String segundoEncontrado = "";
				primerSustantivo = "";
				segundoSustantivo = "";

				for (Map.Entry<String, INombrable> entry : game.interactuables.entrySet()) {
					if (input.contains(entry.getKey())) {
						if (indexPrimerEncontrado == 0) {
							indexPrimerEncontrado = input.indexOf(entry.getKey());
							primerEncontrado = entry.getKey();
						} else if (indexSegundoEncontrado == 0) {
							indexSegundoEncontrado = input.indexOf(entry.getKey());
							segundoEncontrado = entry.getKey();
						}
					}
				}

				if (indexPrimerEncontrado == 0) {
					mostrarSalida(INVALIDCOMMAND);
					limpiarComando();
				} else {

					if (indexPrimerEncontrado > 0 && indexSegundoEncontrado > 0) {
						if (indexSegundoEncontrado > indexPrimerEncontrado) {
							primerSustantivo = primerEncontrado;
							segundoSustantivo = segundoEncontrado;
						} else {
							primerSustantivo = segundoEncontrado;
							segundoSustantivo = primerEncontrado;
						}
					} else {
						primerSustantivo = primerEncontrado;
					}

					String salida = interprete.commandRouter(game, verbo, primerSustantivo, segundoSustantivo);

					if (interprete.isKeepPlaying()) {
						mostrarSalida(salida);
						mostrarSalida("Finalizaste el juego!");
						finalizarGame();
					} else {
						mostrarSalida(salida);
						limpiarComando();
					}
				}
			}
		}
	}

	private void finalizarGame() {
		generador = null;
		game = null;
		interprete = null;
		verbos = null;
		txtComando.setText("");
		txtComando.setEditable(false);
		txtComando.setFocusable(false);
		mntmNueva.setEnabled(true);
		mntmGuardar.setEnabled(false);
	}

	private void mostrarSalida(String mensaje) {
		txtHistoria.append("\n" + mensaje + "\n");
	}

	private void limpiarComando() {
		txtComando.setText("");
		txtComando.setFocusable(true);
	}

}
