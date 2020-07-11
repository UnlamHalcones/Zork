package ar.edu.unlam.halcones.game;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import net.miginfocom.swing.MigLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Dimension;

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
import javax.swing.JPanel;

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
	private JPanel panel;

	public RunnableGame() {
		initialize();
	}

	private void initialize() {
		this.setTitle("Bienvenido a Zork!!!");
		this.setBounds(100, 100, 800, 450);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(new MigLayout("", "[grow]", "[grow][][][][][][][][][][grow][][][]"));
		this.setLocationRelativeTo(null);
		
		panel = new DrawPanel();
		getContentPane().add(panel, "cell 0 0 1 10,grow");

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
				display();
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
	
	public void display() {
		panel.repaint();
	}
	
	private class DrawPanel extends JPanel {
		
		private static final long serialVersionUID = 1L;

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			ImageIcon imagen1, imagen2, imagen3, imagen4, imagen5, imagen6;
			imagen1 = new ImageIcon("imagenes/a.jpg");
			imagen2 = new ImageIcon("imagenes/b.jpg");
			imagen3 = new ImageIcon("imagenes/c.jpg");
			imagen4 = new ImageIcon("imagenes/d.jpg");
			imagen5 = new ImageIcon("imagenes/e.jpg");
			imagen6 = new ImageIcon("imagenes/f.jpg");
			Graphics2D g2 = (Graphics2D) g;
			Dimension currentDimension = getContentPane().getSize();
			g2.scale(currentDimension.getWidth() / 300, currentDimension.getHeight() / 200);
			g2.setColor(Color.black);
			g2.fillRect(0, 0, 300, 200);
			
			g2.drawImage(imagen1.getImage(), 0, 50, 50, 50, null);
			g2.drawImage(imagen2.getImage(), 50, 50, 50, 50, null);
			g2.drawImage(imagen3.getImage(), 100, 50, 50, 50, null);
			g2.drawImage(imagen4.getImage(), 150, 50, 50, 50, null);
			g2.drawImage(imagen5.getImage(), 200, 50, 50, 50, null);
			
			g2.drawImage(imagen6.getImage(), 0, 50, 50, 50, null);
		}

		@Override
		public Dimension getPreferredSize() {
			return new Dimension(300, 200);
		}
	}

}
