package views;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import source.bd.SGBD;
import source.modelo.HiloActiva;
import source.modelo.SistemaDeVotaciones;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class Identificarse extends JFrame {

	private Fondo panel;
	private Fondo contentPane;
	private JPanel panelLogoDni;
	private JLabel lblPoweredBy;
	private JLabel lblPoweredBy_1;
	private int codV;
	private JLabel lblTextoExpli;
	private JLabel lblLogoplay;
	private static Identificarse miId=new Identificarse();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Identificarse frame = new Identificarse();
					frame.setVisible(true);
					SGBD.getConexion().conectar();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	private Identificarse() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Waiting.class.getResource("/resources/icono.ico")));
		setTitle("Bienvenido, introduzca su DNIe ");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 600);
		contentPane = new Fondo();
		contentPane.setBackgroundImage(contentPane.createImage("/resources/azul.jpg").getImage());
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.add(getPanel(), BorderLayout.CENTER);
		this.setResizable(false);

	}
	
	public static Identificarse getIndentificarse(){
		return miId;
	}
	
	public int getCodV() {
		return codV;
	}

	public void setCodV(int codV) {
		this.codV = codV;
	}

	/**
	 * @return
	 */
	private JPanel getPanel() {
		if (panel == null) {
			panel = new Fondo();
			panel.setLayout(new BorderLayout(0, 0));
			panel.add(getPanelLogoDni(), BorderLayout.NORTH);
			panel.add(getLabel_1(), BorderLayout.WEST);
			panel.add(getLblLogoplay(), BorderLayout.CENTER);
			
		}
		return panel;
	}
	private JPanel getPanelLogoDni() {
		if (panelLogoDni == null) {
			panelLogoDni = new Fondo();
			GroupLayout gl_panelLogoDni = new GroupLayout(panelLogoDni);
			gl_panelLogoDni.setHorizontalGroup(
				gl_panelLogoDni.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_panelLogoDni.createSequentialGroup()
						.addGap(30)
						.addComponent(getLblPoweredBy_1(), GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(getLblPoweredBy(), GroupLayout.PREFERRED_SIZE, 124, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(546, Short.MAX_VALUE))
			);
			gl_panelLogoDni.setVerticalGroup(
				gl_panelLogoDni.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_panelLogoDni.createSequentialGroup()
						.addGroup(gl_panelLogoDni.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_panelLogoDni.createSequentialGroup()
								.addContainerGap()
								.addComponent(getLblPoweredBy(), GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
							.addGroup(gl_panelLogoDni.createSequentialGroup()
								.addGap(35)
								.addComponent(getLblPoweredBy_1(), GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)))
						.addContainerGap())
			);
			panelLogoDni.setLayout(gl_panelLogoDni);
			
		}
		return panelLogoDni;
	}
	private JLabel getLblPoweredBy() {
		if (lblPoweredBy == null) {
			lblPoweredBy = new JLabel("");
			lblPoweredBy.setIcon(new ImageIcon(Identificarse.class.getResource("/resources/dniELogo.jpg")));
		}
		return lblPoweredBy;
	}
	private JLabel getLblPoweredBy_1() {
		if (lblPoweredBy_1 == null) {
			lblPoweredBy_1 = new JLabel("Powered by");
			lblPoweredBy_1.setLabelFor(lblPoweredBy_1);
			lblPoweredBy_1.setForeground(UIManager.getColor("Button.highlight"));
		}
		return lblPoweredBy_1;
	}
	private JLabel getLabel_1() {
		if (lblTextoExpli == null) {
			lblTextoExpli = new JLabel("");
			lblTextoExpli.setIcon(new ImageIcon(Identificarse.class.getResource("/resources/manus.png")));
			lblTextoExpli.setText("                                                ");
		}
		return lblTextoExpli;
	}
	private JLabel getLblLogoplay() {
		if (lblLogoplay == null) {
			lblLogoplay = new JLabel("");
			lblLogoplay.setIcon(new ImageIcon(Identificarse.class.getResource("/resources/next.png")));
			lblLogoplay.addMouseListener(new MouseListener() {
				
				@Override
				public void mouseClicked(MouseEvent e) {
					//QUITAR DESPUES!!!!!!!
					//SistemaDeVotaciones.getSistema().setCodVotacion(1);
					List<String> datos = new ArrayList<>();
					datos = SistemaDeVotaciones.getSistema().identificar();
					if(datos.equals(null)){
						Object msj = "Tu Dni no se ha podido leer correctamente" ;
						lblLogoplay.setIcon(new ImageIcon(Identificarse.class.getResource("/resources/next.png")));
						JOptionPane.showMessageDialog(null,msj, "Mensaje de Error", JOptionPane.ERROR_MESSAGE); 
					}else if(!SistemaDeVotaciones.getSistema().esta(datos.get(0))){
						System.out.println("ESTA: "+datos.get(0)+" "+SistemaDeVotaciones.getSistema().esta(datos.get(0)) );
						Object msj = "No estás registrado para esta votación" ;
						lblLogoplay.setIcon(new ImageIcon(Identificarse.class.getResource("/resources/next.png")));
						JOptionPane.showMessageDialog(null,msj, "Mensaje de Error", JOptionPane.ERROR_MESSAGE);
					}else if(SistemaDeVotaciones.getSistema().haVotado(datos.get(0))){
						Object msj = "Ya has registrado tu voto" ;
						lblLogoplay.setIcon(new ImageIcon(Identificarse.class.getResource("/resources/next.png")));
						JOptionPane.showMessageDialog(null,msj, "Mensaje de Error", JOptionPane.ERROR_MESSAGE);
					}
					else{
						//Votar vota = new Votar(datos.get(0), datos.get(1), getCodV());
						Votar vota = new Votar(datos.get(0), datos.get(1), 1);
						vota.crearOpciones();
						vota.setVisible(true);
						dispose();
					}
					/*Votar vota = new Votar("79046076K", "MIKEL", 1);
					vota.crearOpciones();
					vota.setVisible(true);
					dispose();*/
				}
				
				@Override
				public void mouseReleased(MouseEvent e) {}
				
				@Override
				public void mousePressed(MouseEvent e) {
					lblLogoplay.setIcon(new ImageIcon(Identificarse.class.getResource("/resources/nextPulsado.png")));
				}
				
				@Override
				public void mouseExited(MouseEvent e) {}
				
				@Override
				public void mouseEntered(MouseEvent e) {}
				
				
			});
		}
		return lblLogoplay;
	}
}
