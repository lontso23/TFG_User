package views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import source.bd.SGBD;
import source.modelo.HiloActiva;
import source.modelo.SistemaDeVotaciones;

public class Waiting extends JFrame {


	private Fondo panel;
	private Fondo contentPane;
	private static int codV=0;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		Waiting frame = new Waiting();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame.setVisible(true);
					SGBD.getConexion().conectar();
					HiloActiva.getHilo().start();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		boolean a=false;
		while(!a){
			a=HiloActiva.getHilo().isFin();
		}
		esperarActiva();
		frame.setVisible(false);
	}

	/**
	 * Create the frame.
	 */
	public Waiting() {
		
		setTitle("Esperando a iniciar una Votación ");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 600);
		contentPane = new Fondo();
		contentPane.setBackgroundImage(contentPane.createImage("/resources/azulLetra.jpg").getImage());
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		contentPane.add(getPanel(), BorderLayout.CENTER);
		this.setResizable(false);
		

	}
	
	private JPanel getPanel() {
		if (panel == null) {
			panel = new Fondo();
		}
		return panel;
	}
	
	private static void esperarActiva(){
		if(HiloActiva.getHilo().getCodV()!=0){
		Identificarse ident = new Identificarse(HiloActiva.getHilo().getCodV());
		Dimension pantalla= Toolkit.getDefaultToolkit().getScreenSize();
		Dimension ventana = ident.getSize();
		ident.setLocation((pantalla.width-ventana.width)/2, (pantalla.height-ventana.height)/2);
		ident.setVisible(true);
		}
	}
	

}
