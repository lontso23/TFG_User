package source.modelo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.smartcardio.CardException;

import source.bd.SGBD;
import source.dnie.DNIPublicData;

public class SistemaDeVotaciones {

	private static SistemaDeVotaciones miSistema = new SistemaDeVotaciones();
	
	
	private SistemaDeVotaciones(){
		
	}
	
	public static SistemaDeVotaciones getSistema(){
		SGBD.getConexion().conectar();
		return miSistema;
	}
	
	public int comprobarActiva(){
		return GestorVotacionUser.getGVotacion().comprobarActiva();
	}
	
	public void setCodVotacion(int cod){
		GestorVotacionUser.getGVotacion().setCodVotacion(cod);
	}
	
	public int getCodVotacion(){
		return GestorVotacionUser.getGVotacion().getCodVotacion();
	}
	
	public List<String> identificar(){
		List<String> datos = new ArrayList<>();
		try {
			datos = DNIPublicData.getDniPublic().getPublicDataFromCard();
		} catch (CardException e) {e.printStackTrace();} catch (IOException e) {e.printStackTrace();}
		return datos;
	}
	
	public boolean haVotado(String dni){
		return GestorVotacionUser.getGVotacion().haVotado(dni);
	}
	
	public boolean esta(String dni){
		return GestorVotacionUser.getGVotacion().esta(dni);
	}
	
	public boolean comprobarPin(String dni, String pin){
		return GestorVotacionUser.getGVotacion().comprobarPin(dni,pin);
	}
	
	public void votar(String dni, String alter){
		//TODO puede hacer falta la IP del ordenador del votante
		GestorVotacionUser.getGVotacion().votar(dni, alter);
	
	}
	
	public ArrayList<Alternativa> obtAlternativasInscritas(int CodV){
		return GestorVotacionUser.getGVotacion().obtAlternativasInscritas(CodV);
	}
	
}
