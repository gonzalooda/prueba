
package acrimev;

import java.sql.*;
import javax.swing.*;
public class Conectar {
    Connection conect = null;
    public Connection conexion()
        {
            try {
                //Cargamos el Driver MySQL
                
                Class.forName("com.mysql.jdbc.Driver");
                conect = DriverManager.getConnection("jdbc:mysql://localhost/acrimev?useTimezone=true&serverTimezone=UTC","root","bcgx3");
                System.out.println("(conexión establecida");
                //JOptionPane.showMessageDialog(null,"Conectado");
                                        
            }   catch (ClassNotFoundException | SQLException e){
                //System.out.println("Error de conexión");
                JOptionPane.showMessageDialog(null,"Error de conexión"+e);
            }
            return conect;
        }
    
    
}
