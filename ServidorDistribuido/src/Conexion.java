import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Nicolas
 */
public class Conexion 
{
    Connection con;
    PreparedStatement ps = null;
    ResultSet rs;
    private String url = "jdbc:mysql://biqezqa592vn7fme3lxr-mysql.services.clever-cloud.com/biqezqa592vn7fme3lxr";
    private String user = "uvrbi4jjscaw4jtz";
    private String password = "d9M8bfdSX8TC6iGyclxm";
    public Conexion()
    {
        try
        {
            con = DriverManager.getConnection(url, user, password);
            con.setAutoCommit(false);
        } catch (Exception ex) {System.out.println("Error conexión; " + ex);}
    }
}
