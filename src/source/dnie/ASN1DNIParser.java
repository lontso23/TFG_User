package source.dnie;

public class ASN1DNIParser {
	 private String name;
	  private String dni;

	  public ASN1DNIParser() {
	    name = "";
	    dni = "";
	  }

	  public String getName() {
	    return name;
	  }

	  public String getDni() {
	    return dni;
	  }

	  public void parseData(String data) {
	    readID(data);
	    readName(data);
	  }

	  private void readID(String printableObject) {
	    final String OID = "(2.5.4.5)";
	    final int OBJECT_OFFSET = 1;
	    final int PLUS_ONE_OFFSET = 1;

	    int dirtyNameIndex = printableObject.indexOf(OID) + OID.length() + OBJECT_OFFSET + PLUS_ONE_OFFSET;
	    if (dirtyNameIndex < printableObject.length() && dirtyNameIndex > 0) {
	      String dirtyName = printableObject.substring(dirtyNameIndex);
	      int subClearNameIndex = dirtyName.indexOf("(") + PLUS_ONE_OFFSET;

	      if (subClearNameIndex < dirtyName.length() && subClearNameIndex > 0) {
	        String subClearName = dirtyName.substring(subClearNameIndex);
	        int clearNameIndex = subClearName.indexOf(")");

	        if (clearNameIndex < subClearName.length() && clearNameIndex > 0) {
	          dni = subClearName.substring(0, clearNameIndex);
	        }

	      }

	    }

	  }

	  private void readName(String printableObject) {
	    final String OID = "(2.5.4.3)";
	    final int OBJECT_OFFSET = 1;
	    final int PLUS_ONE_OFFSET = 1;

	    int dirtyNameIndex = printableObject.indexOf(OID) + OID.length() + OBJECT_OFFSET + PLUS_ONE_OFFSET;
	    if (dirtyNameIndex < printableObject.length() && dirtyNameIndex > 0) {
	      String dirtyName = printableObject.substring(dirtyNameIndex);
	      int subClearNameIndex = dirtyName.indexOf("(") + PLUS_ONE_OFFSET;

	      if (subClearNameIndex < dirtyName.length() && subClearNameIndex > 0) {
	        String subClearName = dirtyName.substring(subClearNameIndex);
	        int clearNameIndex = subClearName.indexOf("(");

	        if (clearNameIndex < subClearName.length() && clearNameIndex > 0) {
	          name = subClearName.substring(0, clearNameIndex);
	        }

	      }

	    }

	  }
}
