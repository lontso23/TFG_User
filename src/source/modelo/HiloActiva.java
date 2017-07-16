package source.modelo;

import java.util.Observable;

public class HiloActiva   extends Observable{
	private Thread hilo;
	private static HiloActiva miHilo=new HiloActiva();
	private int codV=0;
	private boolean fin=false;
	
	private HiloActiva(){
		
	}
	
	public static HiloActiva getHilo(){
		return miHilo;
	}
	
	
	
	public boolean isFin() {
		return fin;
	}

	public void setFin(boolean fin) {
		this.fin = fin;
	}

	public int getCodV() {
		return codV;
	}

	public void setCodV(int codV) {
		this.codV = codV;
	}

	public void run() {
		
		while(codV==0){
			codV = SistemaDeVotaciones.getSistema().comprobarActiva(); 
			
		}
		setChanged();
		notifyObservers(codV);
		
		
		setCodV(codV);
		
	  }

	  public void start () {
	    if (hilo == null) {
	      hilo = new Thread ();
	      hilo.start();
	    }
	  }
}
