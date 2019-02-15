package bd;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.*;


public class DataBase {

    private Connection _con=null;

    public DataBase () {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch(Exception e) {
            System.out.println("Erreur : Class.forName");
            e.printStackTrace();
        }
    }

    public void connect()
    {
        try {
            _con = DriverManager.getConnection("jdbc:mysql://localhost:3306/TP_XML_music", "root", "root");
            System.out.println("OK");
        } catch (Exception e) {
            System.out.println("Unable to connect to MySQL");
            e.printStackTrace();
        }
    }

    public void close()
    {
        try{
            _con.close();
        }catch (Exception e) {
            System.out.println("Unalble to close MySQL connection");
            e.printStackTrace();
        }
    }

    public ResultSet execQuerry(String quer) throws Exception
    {
        Statement stmt = _con.createStatement();
        ResultSet rs = stmt.executeQuery(quer);

        return rs;
    }

    public ResultSet execPrepareQuerry(String quer) throws Exception
    {
        ResultSet rs = null;
        int nbMaj=0;
        PreparedStatement state = _con.prepareStatement(quer,
                Statement.RETURN_GENERATED_KEYS);
        nbMaj = state.executeUpdate();

        if(nbMaj==1)
            rs=state.getGeneratedKeys();

        return rs;
    }

    public Connection getCon()
    {
        return _con;
    }

}
