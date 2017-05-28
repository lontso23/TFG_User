package source.bd;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.*;


public class SGBD {
	
	/**********************************************/
	/*  	CONEXION CON LA BASE DE DATOS		  */
	/**********************************************/
	private static SGBD mConexion = new SGBD();
	
	private static Connection conexion; 
	private static Statement statement;
	private String SentenciaSQL;
	private ResultSet Resultado;
	private static String  usuario = "Xmnieto012"; 
	private static String password = "HdrNjBIwm";
	private static String server = "jdbc:mysql://galan.ehu.eus:3306/Xmnieto012_Votaciones";
	
	private SGBD(){
		
	}
	
	public static SGBD getConexion(){
		return mConexion;
	}
	
	/*******************************************************/
	/* METODO PARA CONECTARSE AL DRIVER Y PODER USAR MYSQL */
	/*******************************************************/
	
	public static void conectar(){
		try{
			Class.forName("org.gjt.mm.mysql.Driver");
			//Class.forName("com.mysql.jdbc.Driver");
		}
		catch (ClassNotFoundException e) {
			//e.printStackTrace();
			e.getMessage();
		}
		// Open connection
		try{
			conexion= DriverManager.getConnection(server,usuario,password);
			conexion.setAutoCommit(true);
			statement=conexion.createStatement();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/***********************************************************/
	/* METODO PARA ESTABLECER LA CONEXION CON LA BASE DE DATOS */
	/***********************************************************/

	public static Statement conexion() {
        Statement st = null;
        try {
            st = conexion.createStatement();
        } catch (SQLException e) {
            System.out.println("Error: Conexion incorrecta.");
            e.printStackTrace();
        }
        return st;
    }
	
	
 
	public static Connection getConnection() {
		return conexion;
	}

	/*************************************************************************
     * M�todo para realizar consultas del tipo: SELECT * FROM tabla WHERE..."*
     *************************************************************************/	
	
	public ResultSet Select(PreparedStatement ps){
		
		try{
			this.Resultado = ps.executeQuery();
		}catch(SQLException e){
			e.getMessage();			
		}
		return Resultado;
	}	
     
	
	
	/*****************************************************************************************************************
     * M�todo para realizar consultas de actualizaci�n, creaci�n o eliminaci�n (DROP,INSERT INTO, ALTER...,NO SELECT * 
     *****************************************************************************************************************/
    
	public void Update(PreparedStatement ps){
		try{
			ps.executeUpdate();
		}catch(SQLException e){
			e.getMessage();			
		}
	}
	
    
    /*********************************
     * M�todo para cerrar la consula *
     *********************************/
    public static void cerrarSelect(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (Exception e) {
                System.out.print("Error: No es posible cerrar la consulta.");
            }
        }
    }
    
    /***********************************
     * M�todo para cerrar la conexi�n. *
     ***********************************/
    public static void cerrar(Statement st) {
        if (st != null) {
            try {
                st.close();
            } catch (Exception e) {
                System.out.print("Error: No es posible cerrar la conexi�n.");
            }
        }
    }
    

}
