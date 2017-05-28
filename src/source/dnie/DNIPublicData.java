package source.dnie;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.smartcardio.CardException;
public class DNIPublicData {
	private static final int DNI_NUMBER_COUNT = 8;
    private static final int DNI_COUNT = 9;
	private static DNIPublicData miDNI = new DNIPublicData();
	
	private DNIPublicData() {

	  }
	  
	public static DNIPublicData getDniPublic(){
		return miDNI;
	}
	  
	 
	  public List<String> getPublicDataFromCard() throws CardException, IOException {
	    List<String> publicData = new ArrayList<>();

	    DNIeCard dnieCard = new DNIeCard();
	    
	    try {
	      dnieCard.connect();
	      
	      String ASN1printablePublicData = dnieCard.readPublicData();
	      ASN1DNIParser asn1Dec = new ASN1DNIParser();
	      asn1Dec.parseData(ASN1printablePublicData);
	      
	      publicData.add(asn1Dec.getDni());
	     
	      if ((publicData.get(0).length() <= DNIeCard.DNI_LENGTH)
	              && (!publicData.get(0).isEmpty())) {
	    	  boolean length = check(asn1Dec.getDni());
		      if(!length){
		    	  CardException ex = new CardException("Error al leer el DNI: No es un DNI correcto!");
			        ex.printStackTrace();
		      }
	      } else {
	        CardException ex = new CardException("Error al leer el DNI: Se ha intentado"
	                + " obtener demasiado pronto. \n�Reinsertar el DNI y esperar"
	                + " a que el led del lector termine de parpadear!");
	        ex.printStackTrace();
	      }
	      publicData.add(asn1Dec.getName());
	      
	    } catch (CardException ex) {
	      Logger.getLogger("").log(Level.SEVERE, "No es posible interactuar "
	              + "con la tarjeta {0}", ex.getMessage());
	      throw new CardException("No ha sido posible conectar con la tarjeta");
	    } catch (IOException ex){
	      Logger.getLogger("").log(Level.SEVERE, "Error en la tarjeta -> {0}", ex.getMessage());
	      throw ex;
	    } finally {
	      dnieCard.disconnect();
	    }
	    if (publicData.size() != 2) {
	            throw new CardException("No hay suficientes datos");
	    }
	    return publicData;
	  }
	  
	  private static boolean check(String dni) {
	        boolean isValid = false;
	        if (null != dni && dni.length() == DNI_COUNT) {
	                    
	            int dniValue = Integer.valueOf(dni.substring(0, DNI_NUMBER_COUNT));            
	            String expectedLetter = "";
	            expectedLetter += "TRWAGMYFPDXBNJZSQVHLCKE".charAt(dniValue % 23);
	            
	            String dniLetter =  dni.substring(DNI_NUMBER_COUNT, dni.length());  
	            
	            isValid = expectedLetter.equals(dniLetter);
	        }
	        return isValid;
	    }
	  
}
