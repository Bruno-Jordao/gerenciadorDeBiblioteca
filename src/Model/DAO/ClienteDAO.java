package Model.DAO;


import ConnectionFactory.ConexaoPostgres;
import Model.DTO.ClienteDTO;
import Interfaces.InterfaceCliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ClienteDAO implements InterfaceCliente{
    @Override
    public boolean create(ClienteDTO obj) throws SQLException {
        String sql = "INSERT INTO cliente (nome,cpf,telefone) VALUES (?,?,?)";
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        int id = 0;
        try{
        con = ConexaoPostgres.getInstance().abrirConexao();
        pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        pst.setString(1, obj.getNome());
        pst.setString(2, obj.getCpf());
        pst.setString(3, obj.getTelefone());
        pst.executeUpdate();
        rs = pst.getGeneratedKeys();
        if(rs.next()){
            id = rs.getInt(1);
        }
        this.setaEndereco(obj.getEndereco(), obj.getCep(), obj.getNumeroEndereco(), id);
        }catch(SQLException e){
            throw new SQLException("erro ao salvar cliente. " + e.getMessage());
        }finally{
            ConexaoPostgres.getInstance().fecharConexao(con, pst, rs);
        }
        return true;
    }
    private void setaEndereco(String endereco, String cep, int numero, int idCliente) throws SQLException{
        String sql = "INSERT INTO endereco_cliente (endereco,cep,numero,id_cliente) VALUES (?,?,?,?)";
        Connection con = null;
        PreparedStatement pst = null;
        try{
            con = ConexaoPostgres.getInstance().abrirConexao();
            pst = con.prepareStatement(sql);
            pst.setString(1, endereco);
            pst.setString(2, cep);
            pst.setInt(3, numero);
            pst.setInt(4, idCliente);
            pst.executeUpdate();
        }catch(SQLException e){
            throw new SQLException("erro ao salvar endereço do cliente. "+ e.getMessage());
        }finally{
            ConexaoPostgres.getInstance().fecharConexao(con, pst);
        }
        
    }
    @Override
    public boolean delete(int id) throws SQLException {
          String sql = "DELETE FROM cliente where id = ?";
          Connection con = null;
            PreparedStatement pst = null;
            try{
                con = ConexaoPostgres.getInstance().abrirConexao();
                pst = con.prepareStatement(sql);
                pst.setInt(1, id);
                pst.execute();
                return true;
            }catch(SQLException e){
                throw new SQLException("erro ao excluir cliente. " + e.getMessage());
            }finally{
                ConexaoPostgres.getInstance().fecharConexao(con, pst);
            }
    }

    @Override
    public boolean update(ClienteDTO obj) throws SQLException {
        String sql = "UPDATE cliente set nome = ?,cpf = ?,telefone= ? WHERE id = ?";
        Connection con = null;
        PreparedStatement pst = null;
        try{
            con = ConexaoPostgres.getInstance().abrirConexao();
            pst = con.prepareStatement(sql);
            pst.setString(1, obj.getNome());
            pst.setString(2, obj.getCpf());
            pst.setString(3, obj.getTelefone());
            pst.setInt(4, obj.getIdRegistro());
            pst.executeUpdate();
            return true;
        }catch(SQLException e){
            throw new SQLException("erro ao atualizar dados do cliente. "+ e.getMessage());
        }
    }

    @Override
    public ClienteDTO read() throws SQLException{
        String sql = "SELECT *FROM cliente";
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<ClienteDTO> vetorCliente = new ArrayList<ClienteDTO>();
        ClienteDTO dadosClientes = new ClienteDTO();
        try {
            con = ConexaoPostgres.getInstance().abrirConexao();
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                ClienteDTO c = new ClienteDTO();
                c.setNome(rs.getString("nome"));
                c.setCpf(rs.getString("cpf"));
                c.setTelefone(rs.getString("telefone"));
                c.setIdRegistro(rs.getInt("id"));
                vetorCliente.add(c);
            }
            dadosClientes.setClientesRegistrados(vetorCliente);
        } catch (SQLException e) {
            throw new SQLException("erro ao buscar clientes. "+e.getMessage());
        }finally{
            ConexaoPostgres.getInstance().fecharConexao(con, pst, rs);
        }
        return dadosClientes;
    }

    @Override
    public ClienteDTO search(String nome) throws SQLException {
        String slq = "SELECT *FROM cliente where nome like ? limit 1";
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try{
            con = ConexaoPostgres.getInstance().abrirConexao();
            pst = con.prepareStatement(slq);
            pst.setString(1,nome+"%");
            rs = pst.executeQuery();
            while(rs.next()){
                ClienteDTO cliente = new ClienteDTO();
                cliente.setNome(rs.getString("nome"));
                cliente.setCpf(rs.getString("cpf"));
                cliente.setIdRegistro(rs.getInt("id"));
                return cliente;
            }
        }catch(SQLException e){
            throw new SQLException("erro ao buscar hóspedes. "+e.getMessage());
        }finally{
            ConexaoPostgres.getInstance().fecharConexao(con, pst, rs);
        }
        return null;
    } 
}