package views;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import source.modelo.SistemaDeVotaciones;

import javax.swing.JPasswordField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class IntroducirPin extends JFrame {

	private Fondo panel;
	private Fondo contentPane;
	private JPasswordField pwdPin;
	private JButton btnSiguiente;
	private JLabel lblIntroduceElPin;
	private String dni;
	private String nombre;
	private JButton btnVolver;

	

	/**
	 * Create the frame.
	 */
	public IntroducirPin(String dni, String nombre) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new Fondo();
		contentPane.setBackgroundImage(contentPane.createImage("/resources/azul.jpg").getImage());
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setDni(dni);
		setNombre(nombre);
		contentPane.setLayout(null);
		contentPane.add(getPwdPin());
		contentPane.add(getBtnSiguiente());
		contentPane.add(getLblIntroduceElPin());
		contentPane.add(getBtnVolver());
		this.setResizable(false);
	}
	
	private JPanel getPanel() {
		if (panel == null) {
			panel = new Fondo();
			
		}
		return panel;
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

	private JPasswordField getPwdPin() {
		if (pwdPin == null) {
			pwdPin = new JPasswordField();
			pwdPin.setBounds(119, 99, 216, 29);
		}
		return pwdPin;
	}
	private JButton getBtnSiguiente() {
		if (btnSiguiente == null) {
			btnSiguiente = new JButton("Siguiente");
			btnSiguiente.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					char[] arrayC = getPwdPin().getPassword(); 
					String pass = new String(arrayC); 
					System.out.println(pass+" : PIN");
					boolean c = SistemaDeVotaciones.getSistema().comprobarPin(getDni(), pass);
					if(c){
						Votar vota = new Votar(getDni(), getNombre(), SistemaDeVotaciones.getSistema().getCodVotacion());
						vota.crearOpciones();
						vota.setVisible(true);
						dispose();
					}else{
						Object msj = "El Pin introducido es incorrecto " ;
						JOptionPane.showMessageDialog(null,msj, "Mensaje de Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			});
			btnSiguiente.setBounds(247, 189, 117, 29);
		}
		return btnSiguiente;
	}
	private JLabel getLblIntroduceElPin() {
		if (lblIntroduceElPin == null) {
			lblIntroduceElPin = new JLabel("Introduce el Pin de tu DNIe");
			lblIntroduceElPin.setForeground(Color.WHITE);
			lblIntroduceElPin.setBounds(119, 41, 245, 16);
		}
		return lblIntroduceElPin;
	}
	private JButton getBtnVolver() {
		if (btnVolver == null) {
			btnVolver = new JButton("Volver");
			btnVolver.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int cod = SistemaDeVotaciones.getSistema().getCodVotacion();
					Identificarse.getIndentificarse().setCodV(cod);
					Dimension pantalla= Toolkit.getDefaultToolkit().getScreenSize();
					Dimension ventana = Identificarse.getIndentificarse().getSize();
					Identificarse.getIndentificarse().setLocation((pantalla.width-ventana.width)/2, (pantalla.height-ventana.height)/2);
					Identificarse.getIndentificarse().setVisible(true);
					dispose();
				}
			});
			btnVolver.setBounds(39, 189, 117, 29);
		}
		return btnVolver;
	}
}
