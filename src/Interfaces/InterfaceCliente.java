package Interfaces;


import DTO.ClienteDTO;
import java.sql.SQLException;

public interface InterfaceCliente {
    public boolean create(ClienteDTO obj) throws SQLException;
    public boolean delete (int id) throws SQLException;
    public boolean update (ClienteDTO obj) throws SQLException;
    public ClienteDTO read() throws SQLException;
    public ClienteDTO search(String nome) throws SQLException;
}