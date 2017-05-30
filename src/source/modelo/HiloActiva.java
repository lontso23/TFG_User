package source.modelo;

public class HiloActiva extends Thread {
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
			codV=SistemaDeVotaciones.getSistema().comprobarActiva();
			System.out.println(codV);
		}
		setCodV(codV);
		setFin(true);
	  }

	  public void start () {
	    if (hilo == null) {
	      hilo = new Thread (this);
	      hilo.start();
	    }
	  }
}
