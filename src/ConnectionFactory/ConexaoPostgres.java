package ConnectionFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConexaoPostgres {

    private  final String USER = "postgres";
    private  final String PASS = "2112";
    private  final String DRIVER = "org.postgresql.Driver";
    private  final String URL = "jdbc:postgresql://localhost:5432/livraria";

    public Connection abrirConexao() {
        System.setProperty("org.postgresql.Drives", DRIVER);
        try {
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException ex) {
            throw new RuntimeException("ERRO AO CONECTAR AO BANCO. " + ex.getMessage());
        }
    }

    public void fecharConexao(Connection con){
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException("ERRO AO CONECTAR AO BANCO. " + e.getMessage());

        }
    }

    public void fecharConexao(Connection con, PreparedStatement ps){
        fecharConexao(con);
        try {
            if (ps != null) {
                ps.close();
            }
        } catch (SQLException e) {
           throw new RuntimeException("ERRO AO CONECTAR AO BANCO. " + e.getMessage());

        }
    }

    public void fecharConexao(Connection con, PreparedStatement ps, ResultSet rs){
        fecharConexao(con, ps);
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException("ERRO AO CONECTAR AO BANCO. " + e.getMessage());
        }
    }
    
    private ConexaoPostgres(){
        
    }
    private static volatile ConexaoPostgres con;
    public static ConexaoPostgres getInstance(){
        if(con == null){
            synchronized (ConexaoPostgres.class){
                if(con == null){
                    con = new ConexaoPostgres() {};
                }
            }
        }
        return con;
    }
}