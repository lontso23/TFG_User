package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import source.bd.SGBD;
import source.modelo.Alternativa;
import source.modelo.SistemaDeVotaciones;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;

public class Votar extends JFrame {

	private Fondo panel;
	private Controlador controlador = new Controlador();
	private Fondo contentPane;
	private String dni;
	private String nombre;
	private int codV;
	private JButton btnVotar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Votar frame = new Votar("","",0);
					SGBD.getConexion().conectar();
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
	public Votar(String dni, String nombre, int codV) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Waiting.class.getResource("/resources/icono.ico")));
		setTitle("Bienvenido "+ nombre);
		setNombre(nombre);
		setCodV(codV);
		setDni(dni);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 600);
		contentPane = new Fondo();
		contentPane.setBackgroundImage(contentPane.createImage("/resources/azul.jpg").getImage());
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.add(getPanel(), BorderLayout.SOUTH);
		
		this.setResizable(false);
	}
	
	
	
	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getCodV() {
		return codV;
	}

	public void setCodV(int codV) {
		this.codV = codV;
	}
	
	

	public Fondo getContentPane() {
		return contentPane;
	}

	private JPanel getPanel() {
		if (panel == null) {
			panel = new Fondo();
			panel.setLayout(new GridLayout(1, 0, 0, 0));
			panel.add(getBtnVotar());
			/*GroupLayout gl_panel = new GroupLayout(panel);
			gl_panel.setHorizontalGroup(
				gl_panel.createParallelGroup(Alignment.LEADING)
					.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
						.addContainerGap(674, Short.MAX_VALUE)
						.addComponent(getBtnVotar(), GroupLayout.PREFERRED_SIZE, 192, GroupLayout.PREFERRED_SIZE)
						.addGap(124))
			);
			gl_panel.setVerticalGroup(
				gl_panel.createParallelGroup(Alignment.LEADING)
					.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
						.addContainerGap(464, Short.MAX_VALUE)
						.addComponent(getBtnVotar(), GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
						.addGap(39))
			);
			panel.setLayout(gl_panel);*/
			
			
		}
		return panel;
	}
	
	public void crearOpciones(){
		ArrayList<Alternativa> alter= new ArrayList<Alternativa>();
		alter = SistemaDeVotaciones.getSistema().obtAlternativasInscritas(1);
		GridLayout gPanel = new GridLayout(alter.size()/2, 2);
		gPanel.setVgap(0);
		gPanel.setHgap(0);
		JPanel gridPanel = new JPanel(gPanel);
		gridPanel.setBackground(new Color(0,0,0,65));
		for(int i=0; i<alter.size();i++){
			Alternativa act = alter.get(i);
			JLabel lb = new JLabel();
			lb.setName(act.getNombre()+",,,,");
			lb.setIcon(new ImageIcon(act.getLogo()));
			lb.setText(act.getDescrip());
			lb.setForeground(UIManager.getColor("Button.highlight"));
			lb.addMouseListener(controlador);
			lb.setBounds(0, 0, 100, 50);
			gridPanel.add(lb);
		}
		getContentPane().add(gridPanel);
	
	}
	
	
	private class Controlador implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent e) {
			int queBotonRaton;
			// para elegir boton del raton e.getButton
			queBotonRaton = e.getButton();
			String c = e.getSource().toString().substring(19, 30);
			String[] linea1 = c.split(",");
			System.out.println(linea1[0]);
			if (queBotonRaton == 1) {
				//TODO Marcar el label del partido clicado y añadir un boton para votar
				//SistemaDeVotaciones.getSistema().votar(dni, linea1[0]);
			} 
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {}

		@Override
		public void mouseExited(MouseEvent arg0) {}

		@Override
		public void mousePressed(MouseEvent arg0) {}

		@Override
		public void mouseReleased(MouseEvent arg0) {}

	}
	private JButton getBtnVotar() {
		if (btnVotar == null) {
			btnVotar = new JButton("VOTAR");
		}
		return btnVotar;
	}
}
