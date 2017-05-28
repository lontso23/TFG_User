package source.modelo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.smartcardio.CardException;

import source.dnie.DNIPublicData;

public class SistemaDeVotaciones {

	private static SistemaDeVotaciones miSistema = new SistemaDeVotaciones();
	
	
	private SistemaDeVotaciones(){
		
	}
	
	public static SistemaDeVotaciones getSistema(){
		return miSistema;
	}
	
	public int comprobarActiva(){
		return GestorVotacionUser.getGVotacion().comprobarActiva();
	}
	
	public void setCodVotacion(int cod){
		GestorVotacionUser.getGVotacion().setCodVotacion(cod);
	}
	
	public List<String> identificar(){
		List<String> datos = new ArrayList<>();
		try {
			datos = DNIPublicData.getDniPublic().getPublicDataFromCard();
		} catch (CardException e) {e.printStackTrace();} catch (IOException e) {e.printStackTrace();}
		return datos;
	}
	
	public void votar(String dni, String alter){
		//TODO puede hacer falta la IP del ordenador del votante
		//if(!haVotado(dni))
		GestorVotacionUser.getGVotacion().votar(dni, alter);
	
	}
	
}
