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
import javax.swing.JTextPane;
import javax.swing.JButton;
import javax.swing.DropMode;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import java.awt.event.ActionEvent;

public class Waiting extends JFrame implements Observer {


	private Fondo panel;
	private Fondo contentPane;
	private static int codV=0;
	private JButton btnEmpezar;
	private JPanel panelTexto;
	private JTextPane txtpnInfo;
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
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}

	/**
	 * Create the frame.
	 */
	public Waiting() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Waiting.class.getResource("/resources/icono.ico")));
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
			panel.setLayout(null);
			panel.add(getBtnEmpezar());
			panel.add(getPanelTexto());
		}
		return panel;
	}
	
	private JButton getBtnEmpezar() {
		if (btnEmpezar == null) {
			btnEmpezar = new JButton("Buscar");
			btnEmpezar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int cod=0;
					while(cod==0){
						cod = SistemaDeVotaciones.getSistema().comprobarActiva(); 
						System.out.println(cod);
					}
					getTxtpnInfo().setText("Se ha activado la votación "+HiloActiva.getHilo().getCodV());
					SistemaDeVotaciones.getSistema().setCodVotacion(cod);
					Identificarse.getIndentificarse().setCodV(cod);
					Dimension pantalla= Toolkit.getDefaultToolkit().getScreenSize();
					Dimension ventana = Identificarse.getIndentificarse().getSize();
					Identificarse.getIndentificarse().setLocation((pantalla.width-ventana.width)/2, (pantalla.height-ventana.height)/2);
					Identificarse.getIndentificarse().setVisible(true);
					dispose();
					
				}
			});
			btnEmpezar.setBounds(608, 312, 167, 68);
		}
		return btnEmpezar;
	}
	private JPanel getPanelTexto() {
		if (panelTexto == null) {
			panelTexto = new JPanel();
			panelTexto.setBounds(530, 130, 299, 100);
			panelTexto.setLayout(new BorderLayout(0, 0));
			panelTexto.add(getTxtpnInfo(), BorderLayout.CENTER);
		}
		return panelTexto;
	}
	private JTextPane getTxtpnInfo() {
		if (txtpnInfo == null) {
			txtpnInfo = new JTextPane();
			txtpnInfo.setText("Pulse para buscar una votación activa");
			txtpnInfo.setEditable(false);
		}
		return txtpnInfo;
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
}
