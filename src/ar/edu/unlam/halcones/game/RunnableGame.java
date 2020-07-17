package ar.edu.unlam.halcones.game;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import net.miginfocom.swing.MigLayout;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Dimension;

import ar.edu.unlam.halcones.archivo.GeneradorDeGame;
import ar.edu.unlam.halcones.archivo.LectorDiccionarioCSV;
import ar.edu.unlam.halcones.entities.Game;
import ar.edu.unlam.halcones.entities.INombrable;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class RunnableGame extends JFrame {

	private final static String INVALIDCOMMAND = "No entendi lo que ingresaste. Intenta de nuevo por favor.";
	private final static String ZORKLANDSCAPE = "imagenes/Zork.png";
	
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
	private Interprete interprete;
	private Map<String, String> verbos;
	private GeneradorDeGame generador;
	private Set<String> imagenes;
	private String directorioImagenes;
	private String nombreCharacter = "Guybrush Threepwood";
	
	private JSplitPane splitPane;
	private JPanel landscapePanel;
	private JPanel interactuablePanel;

	private String landscapeImageUrl;
	
	private GuardadorHistoria guardadorHistoria;
	
	public RunnableGame() {
		setResizable(false);
		initialize();
	}

	private void initialize() {
		this.setTitle("Bienvenido a Zork!!!");
		this.setBounds(100, 100, 1200, 800);
		this.getContentPane().setLayout(new MigLayout("", "[grow]", "[91,grow,center][][][][][][][][][][grow][][][]"));
		this.setLocationRelativeTo(null);
		
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener( new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
        		salirDelJuego();
            }
        } );
		
		landscapeImageUrl = ZORKLANDSCAPE;
		
		imagenes = new HashSet<String>();

		splitPane = new JSplitPane();
		splitPane.setEnabled(false);
		splitPane.setBackground(Color.BLACK);
		splitPane.setDividerLocation(788);
		getContentPane().add(splitPane, "cell 0 0,grow");
		
		landscapePanel = new LandscapePanel();
		landscapePanel.setBackground(Color.BLACK);
		splitPane.setLeftComponent(landscapePanel);
		
		interactuablePanel = new DrawPanel();
		interactuablePanel.setBackground(Color.BLACK);
		splitPane.setRightComponent(interactuablePanel);
		

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
		
		mntmGuardar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				guardarPartida();
			}

		
		});
		
		mnPartida.add(mntmGuardar);

		mntmSalir = new JMenuItem("Salir");
		mntmSalir.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				salirDelJuego();
			}
		});
		mnPartida.add(mntmSalir);
	}

	private void salirDelJuego() {
		if (JOptionPane.showConfirmDialog(this, "Realmente quieres irte del juego", "Atención...",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == 0) {
			System.exit(0);
		}
	}

	private void nuevaPartida() {
		nuevaPartida = new NuevaPartida(this, nombreCharacter);
		if (nuevaPartida.iniciaJuego()) {
			generador = new GeneradorDeGame();
			try {
				nombreCharacter = nuevaPartida.getNombreCharacter().trim();
				game = generador.generarEntornoDeJuego(nuevaPartida.getArchivo(), nombreCharacter);
				game.generarInteractuables();
				this.guardadorHistoria = new GuardadorHistoria();
				
				this.guardadorHistoria.agregarSalida(game.getWelcome());
				
				mntmNueva.setEnabled(false);
				mntmGuardar.setEnabled(true);
				interprete = new Interprete();
				verbos = LectorDiccionarioCSV.leerDiccionario();
				directorioImagenes = "imagenes/" + nuevaPartida.getCarpetaImagenes().trim() + "/";
				txtHistoria.setText("");
				txtComando.setEditable(true);
				txtComando.setFocusable(true);
				mostrarSalida("Bienvenido a Zork " + nombreCharacter + ". Espero te diviertas!");
				mostrarSalida(game.getWelcome());
				buscarImagenes("character " + game.getWelcome());
				display();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}


	private void guardarPartida() {
		
	    JFileChooser chooser = new JFileChooser();
	    
	    chooser.setCurrentDirectory(new File("/home/me/Documents"));
	    
	    int retrival = chooser.showSaveDialog(null);
	    
	    if (retrival == JFileChooser.APPROVE_OPTION) {
	    	String fileName = chooser.getSelectedFile().toString();
	    	
	    	if(!fileName.contains(".txt"))
	    		fileName = fileName + ".txt";
	    	
	    	try(FileWriter fw = new FileWriter(fileName)) {
	    	    fw.write(this.guardadorHistoria.getSalida());
	    	}
	    	
	    	catch (Exception ex) {
	            ex.printStackTrace();
	        }
	    }
	}
	
	
	private void enviarComando() {
		String comando = txtComando.getText().toLowerCase().trim();
		String input = "";
		String verbo = "";
		String primerSustantivo = "";
		String segundoSustantivo = "";

		String comandoIngresado = ">>" + this.nombreCharacter + ": " + comando;

		if (comando.isEmpty()) {
		} else if (comando.equals("salir")) {
			limpiarComando();
			if (JOptionPane.showConfirmDialog(this, "Realmente quieres abandonar el juego sin antes guardar la partida",
					"Atención...", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == 0) {
				mostrarSalida("Acabas de abandonar el juego!");
				finalizarGame();
			}
		} else if (!comando.contains(" ")) {
			mostrarSalida(INVALIDCOMMAND);
			limpiarComando();
		} else {

			input = comando;
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

					
					this.guardadorHistoria.agregarEntrada(comandoIngresado);
					
					mostrarSalida(comandoIngresado);
					String salida = interprete.commandRouter(game, verbo, primerSustantivo, segundoSustantivo);
					this.guardadorHistoria.agregarSalida(salida);
					
					game.actualizarInteractuables();

					if (salida.toLowerCase().equals("ver_inventario")) {

						Object[][] items = game.getCharacter().getInventory().getItemsTable();
						String[] columnNames = {"Item", "Nombre (Cantidad)"};

						TableModel model = new DefaultTableModel(items, columnNames) {
							/**
							 * 
							 */
							private static final long serialVersionUID = -3575900618754822545L;

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
							private static final long serialVersionUID = 3553454529151078694L;

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


						splitPane.setRightComponent(new JScrollPane(table));
						display();
						limpiarComando();

						splitPane.setDividerLocation(800);
						getContentPane().add(splitPane, "cell 0 0,grow");

					} else {

						splitPane.setRightComponent(interactuablePanel);

						splitPane.setDividerLocation(800);
						getContentPane().add(splitPane, "cell 0 0,grow");

						mostrarSalida(salida);
						buscarImagenes(salida);
						display();
						limpiarComando();

						if (!interprete.isKeepPlaying()) {
							mostrarSalida("Finalizaste el juego!");
							finalizarGame();
						}

					}
				}
			}
		}
	}

	private void buscarImagenes(String salida) {
		File imagen;
		String[] str = salida.toLowerCase().replace(".", "").replace(",", "").replace("�", "").replace("!", "")
				.split(" ");
		boolean actualizarListaDeImagenes = false;
		Set<String> imagenesABuscar = new HashSet<String>();

		for (int i = 0; i < str.length; i++) {
			if (!str[i].isEmpty()) {
				imagen = new File(directorioImagenes + str[i].trim() + ".jpg");
				if (imagen.exists()) {
					imagenesABuscar.add(str[i]);
					actualizarListaDeImagenes = true;
				}
			}
		}

		if (actualizarListaDeImagenes) {
			imagenes.clear();
			for (String string : imagenesABuscar) {
				imagenes.add(string);
			}
		}
	}

	private void finalizarGame() {
		generador = null;
		game = null;
		interprete = null;
		verbos = null;
		txtComando.setEditable(false);
		txtComando.setFocusable(false);
		mntmNueva.setEnabled(true);
	}

	private void mostrarSalida(String mensaje) {
		txtHistoria.append("\n" + mensaje + "\n");
	}

	private void limpiarComando() {
		txtComando.setText("");
		txtComando.setFocusable(true);
	}

	public void display() {
		String locationImageUrl = "";
		
		interactuablePanel.repaint();
		
		locationImageUrl = directorioImagenes.concat("/landscapes/") + game.getCharacter().getLocation().getName() + ".jpg";
		if(!locationImageUrl.equals(landscapeImageUrl)){
			landscapeImageUrl = locationImageUrl;
			landscapePanel.repaint();
		}
	}

	private class DrawPanel extends JPanel {

		private static final long serialVersionUID = 1L;

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);

			Graphics2D g2 = (Graphics2D) g;
			Dimension currentDimension = getContentPane().getSize();
			g2.scale(currentDimension.getWidth() / 300, currentDimension.getHeight() / 200);
			g2.setColor(Color.black);
			g2.fillRect(0, 0, 300, 200);

			int x = 1, 
				y = 1,
				cantFila = 0,
				cantTotal = 0;

			for (String img : imagenes) {
				ImageIcon imagen = new ImageIcon(directorioImagenes + img + ".jpg");
				g2.drawImage(imagen.getImage(), x, y, 30, 30, null);
				cantTotal++;
				if (cantTotal >= 30) {
					break;
				} else if (cantFila < 2) {
					x += 31;
					cantFila++;
				} else {
					x = 1;
					y += 31;
					cantFila = 0;
				}
			}
			
		}

		@Override
		public Dimension getPreferredSize() {
			return new Dimension(300, 200);
		}
	}
	
	private class LandscapePanel extends JPanel {

		private static final long serialVersionUID = 1L;

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);

			Graphics2D g2 = (Graphics2D) g;
			Dimension currentDimension = getSize();
			g2.setColor(Color.black);

			ImageIcon imagen = new ImageIcon(landscapeImageUrl);
			g2.drawImage(imagen.getImage(), 0, 0, currentDimension.width, currentDimension.height, null);
			
		}
	}

	public static void main(String[] args) {
		new RunnableGame().setVisible(true);
	}
}
