package source.modelo;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import source.bd.SGBD;

public class GestorVotacionUser {

	private static GestorVotacionUser miVotacion = new GestorVotacionUser();
	private int codVotacion;

	private GestorVotacionUser(){
		
	}
	
	public static GestorVotacionUser getGVotacion(){
		return miVotacion;
	}
	
	public int getCodVotacion() {
		return codVotacion;
	}


	public void setCodVotacion(int codVotacion) {
		this.codVotacion = codVotacion;
	}
	
	public int comprobarActiva(){
		String sentencia = "SELECT Cod FROM Votacion WHERE Activa = ?";
		PreparedStatement ps;
		int n=0;
		try {
			ps = SGBD.getConexion().getConnection().prepareStatement(sentencia);
			ps.setString(1, "S");
			ResultSet r = SGBD.getConexion().Select(ps);
			while (r.next()){
				n=r.getInt("Cod");
			}
			SGBD.getConexion().cerrarSelect(r);
		} catch (SQLException e) {e.printStackTrace();}
		
		return n;
	}
	
	
	public void votar(String dni,String alter){
		//TODO puede hacer falta la IP del ordenador del votante
		añadirVotosVotante(dni);
		VotosAlter(dni, alter);
	}
	
	public boolean haVotado(String dni){
		PreparedStatement ps;
		String sentencia = "Select Votado From VotosPersona WHERE DniP = ? AND CodV = ?";
		String votado="";
		boolean haVotado=false;
		try {
			ps = SGBD.getConexion().getConnection().prepareStatement(sentencia);
			ps.setString(1, dni);
			ps.setInt(2, getCodVotacion());
			SGBD.getConexion().Select(ps);
			ResultSet r = SGBD.getConexion().Select(ps);
			while (r.next()){
				votado=r.getString("Votado");
			}
			SGBD.getConexion().cerrarSelect(r);
		} catch (SQLException e) {e.printStackTrace();
		}
		if(votado.equals("S")){
			haVotado=true;
		}
		return haVotado;
	}
	
	
	private void añadirVotosVotante(String dni){
		PreparedStatement ps;
		String sentencia = "UPDATE VotosPersona SET Votado = ? WHERE DniP = ? AND CodV = ?";
		try {
			ps = SGBD.getConexion().getConnection().prepareStatement(sentencia);
			ps.setString(1, "S");
			ps.setString(2, dni);
			ps.setInt(3, getCodVotacion());
			SGBD.getConexion().Update(ps);
		} catch (SQLException e) {e.printStackTrace();
		}
	}
	
	private void VotosAlter(String dni, String alter){
		PreparedStatement ps;
		String sentencia = "Select NumMesa, NomColegio From VotosPersona WHERE DniP = ? AND CodV = ?";
		int nMesa=0;
		String cole="";
		boolean haVotado=false;
		try {
			ps = SGBD.getConexion().getConnection().prepareStatement(sentencia);
			ps.setString(1, dni);
			ps.setInt(2, getCodVotacion());
			SGBD.getConexion().Select(ps);
			ResultSet r = SGBD.getConexion().Select(ps);
			while (r.next()){
				nMesa=r.getInt("NumMesa");
				cole=r.getString("NomColegio");
			}
			SGBD.getConexion().cerrarSelect(r);
			añadirVotosAlter(alter, nMesa, cole);
		} catch (SQLException e) {e.printStackTrace();
		}
	}
	
	private void añadirVotosAlter(String alter, int mesa, String cole){
		int numVotos=sumarVotos(alter,mesa,cole);
		PreparedStatement ps;
		String sentencia = "UPDATE VotosMesa SET NumVotos = ? WHERE Alternativa = ? AND CodV = ? AND NumMesa = ? AND NomColegio = ?";
		try {
			ps = SGBD.getConexion().getConnection().prepareStatement(sentencia);
			ps.setInt(1, numVotos);
			ps.setString(2, alter);
			ps.setInt(3, getCodVotacion());
			ps.setInt(4, mesa);
			ps.setString(5, cole);
			SGBD.getConexion().Update(ps);
		} catch (SQLException e) {e.printStackTrace();
		}
	}
	
	private int sumarVotos(String alter, int mesa, String cole){
		PreparedStatement ps;
		String sentencia = "Select NumVotos From VotosMesa WHERE Alternativa = ? AND CodV = ? AND NumMesa = ? AND NomColegio = ?";
		int nVotos=0;
		boolean haVotado=false;
		try {
			ps = SGBD.getConexion().getConnection().prepareStatement(sentencia);
			ps.setString(1, alter);
			ps.setInt(2, getCodVotacion());
			ps.setInt(3, mesa);
			ps.setString(4, cole);
			SGBD.getConexion().Select(ps);
			ResultSet r = SGBD.getConexion().Select(ps);
			while (r.next()){
				nVotos=r.getInt("NumVotos");
			}
			SGBD.getConexion().cerrarSelect(r);
		} catch (SQLException e) {e.printStackTrace();
		}
		nVotos++;
		return nVotos;
	}
	
	public ArrayList<Alternativa> obtAlternativasInscritas(int CodV){
		ArrayList<Alternativa> alter = new ArrayList<Alternativa>();
		ArrayList<String> nomAlter = obtNombreAlternativasInscritas(CodV);
		String sentencia = "SELECT * FROM Alternativa WHERE Nombre = ?";
		PreparedStatement ps;
		for (int i = 0; i < nomAlter.size(); i++) {
			String act = nomAlter.get(i);
			try {
				ps = SGBD.getConexion().getConnection().prepareStatement(sentencia);
				ps.setString(1, act);
				ResultSet r = SGBD.getConexion().Select(ps);
				while (r.next()) {
					Blob blob = r.getBlob("Logo");
					byte[] data = blob.getBytes(1, (int) blob.length());
					BufferedImage img = null;
					try {
						img = ImageIO.read(new ByteArrayInputStream(data));
						Alternativa a = new Alternativa(r.getString("Nombre"), img, r.getString("Descrip"));
						alter.add(a);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				SGBD.getConexion().cerrarSelect(r);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return alter;
	}
	
	public ArrayList<String> obtNombreAlternativasInscritas(int CodV){
		ArrayList<String> alter = new ArrayList<String>();
		String sentencia = "SELECT Alternativa FROM VotosGeneral WHERE CodV = ?";
		PreparedStatement ps;
		try {
			ps = SGBD.getConexion().getConnection().prepareStatement(sentencia);
			ps.setInt(1, CodV);
			ResultSet r = SGBD.getConexion().Select(ps);
			while (r.next()){
				alter.add(r.getString("Alternativa"));
			}
			SGBD.getConexion().cerrarSelect(r);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return alter;
	}
	
}
