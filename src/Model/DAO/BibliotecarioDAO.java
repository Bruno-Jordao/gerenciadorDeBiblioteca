package Model.DAO;

import Interfaces.Bibliotecario;
import Model.DTO.BibliotecarioDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Model.DTO.LivroDTO;
import Model.DTO.AluguelDTO;
import Model.DTO.RelatorioDTO;

public class BibliotecarioDAO implements Bibliotecario {

    private Connection connection;

    public BibliotecarioDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void addBibliotecario(BibliotecarioDTO bibliotecario) {
        String sql = "INSERT INTO bibliotecarios (nome, ID, email, dataDeNascimento, endereco, login, senha) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, bibliotecario.getNome());
            stmt.setLong(2, bibliotecario.getID());
            stmt.setString(3, bibliotecario.getEmail());
            stmt.setDate(4, Date.valueOf(bibliotecario.getDataDeNascimento())); // Ajuste para o formato correto
            stmt.setString(5, bibliotecario.getEndereco());
            stmt.setString(6, bibliotecario.getLogin());
            stmt.setString(7, bibliotecario.getSenha());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public BibliotecarioDTO getBibliotecarioById(long id) {
        String sql = "SELECT * FROM bibliotecarios WHERE ID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                BibliotecarioDTO bibliotecario = new BibliotecarioDTO();
                bibliotecario.setNome(rs.getString("nome"));
                bibliotecario.setID(rs.getLong("ID"));
                bibliotecario.setEmail(rs.getString("email"));
                bibliotecario.setDataDeNascimento(rs.getDate("dataDeNascimento").toLocalDate());
                bibliotecario.setEndereco(rs.getString("endereco"));
                bibliotecario.setLogin(rs.getString("login"));
                bibliotecario.setSenha(rs.getString("senha"));
                return bibliotecario;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<BibliotecarioDTO> getAllBibliotecarios() {
        List<BibliotecarioDTO> bibliotecarios = new ArrayList<>();
        String sql = "SELECT * FROM bibliotecarios";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                BibliotecarioDTO bibliotecario = new BibliotecarioDTO();
                bibliotecario.setNome(rs.getString("nome"));
                bibliotecario.setID(rs.getLong("ID"));
                bibliotecario.setEmail(rs.getString("email"));
                bibliotecario.setDataDeNascimento(rs.getDate("dataDeNascimento").toLocalDate());
                bibliotecario.setEndereco(rs.getString("endereco"));
                bibliotecario.setLogin(rs.getString("login"));
                bibliotecario.setSenha(rs.getString("senha"));
                bibliotecarios.add(bibliotecario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bibliotecarios;
    }

    @Override
    public void updateBibliotecario(BibliotecarioDTO bibliotecario) {
        String sql = "UPDATE bibliotecarios SET nome = ?, email = ?, dataDeNascimento = ?, endereco = ?, login = ?, senha = ? WHERE ID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, bibliotecario.getNome());
            stmt.setString(2, bibliotecario.getEmail());
            stmt.setDate(3, Date.valueOf(bibliotecario.getDataDeNascimento())); // Ajuste para o formato correto
            stmt.setString(4, bibliotecario.getEndereco());
            stmt.setString(5, bibliotecario.getLogin());
            stmt.setString(6, bibliotecario.getSenha());
            stmt.setLong(7, bibliotecario.getID());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteBibliotecario(long id) {
        String sql = "DELETE FROM bibliotecarios WHERE ID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void registrarAluguel(AluguelDTO aluguel) {
        String sql = "INSERT INTO alugueis (livro_id, bibliotecario_id, data_aluguel, data_devolucao_prevista) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, aluguel.getLivroId());
            stmt.setLong(2, aluguel.getBibliotecarioId());
            stmt.setDate(3, Date.valueOf(aluguel.getDataAluguel()));
            stmt.setDate(4, Date.valueOf(aluguel.getDataDevolucaoPrevista()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void devolverLivroAlugado(long aluguelId) {
        String sqlDevolucao = "UPDATE alugueis SET data_devolucao_real = ? WHERE id = ?";
        String sqlAtualizarEstoque = "UPDATE livros SET quantidade = quantidade + 1 WHERE id = (SELECT livro_id FROM alugueis WHERE id = ?)";

        try (PreparedStatement stmtDevolucao = connection.prepareStatement(sqlDevolucao);
             PreparedStatement stmtAtualizarEstoque = connection.prepareStatement(sqlAtualizarEstoque)) {

            // Atualizar a data de devolução real
            stmtDevolucao.setDate(1, Date.valueOf(java.time.LocalDate.now()));
            stmtDevolucao.setLong(2, aluguelId);
            stmtDevolucao.executeUpdate();

            // Atualizar o estoque do livro
            stmtAtualizarEstoque.setLong(1, aluguelId);
            stmtAtualizarEstoque.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void adicionarEstoque(LivroDTO livro, int quantidade) {
        String sql = "UPDATE livros SET quantidade = quantidade + ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, quantidade);
            stmt.setLong(2, livro.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removerEstoque(LivroDTO livro, int quantidade) {
        String sql = "UPDATE livros SET quantidade = quantidade - ? WHERE id = ? AND quantidade >= ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, quantidade);
            stmt.setLong(2, livro.getId());
            stmt.setInt(3, quantidade);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected == 0) {
                System.out.println("Erro: Estoque insuficiente para remover a quantidade solicitada.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public int consultarEstoque(LivroDTO livro) {
        String sql = "SELECT quantidade FROM livros WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, livro.getId());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("quantidade");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public void registrarVenda(LivroDTO livro, int quantidade) {
        String sql = "INSERT INTO vendas (livro_id, quantidade, data_venda) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, livro.getId());
            stmt.setInt(2, quantidade);
            stmt.setDate(3, Date.valueOf(java.time.LocalDate.now()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<AluguelDTO> consultarAlugueisPendentes() {
        List<AluguelDTO> alugueisPendentes = new ArrayList<>();
        String sql = "SELECT * FROM alugueis WHERE data_devolucao_real IS NULL";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                AluguelDTO aluguel = new AluguelDTO();
                aluguel.setId(rs.getLong("id"));
                aluguel.setLivroId(rs.getLong("livro_id"));
                aluguel.setBibliotecarioId(rs.getLong("bibliotecario_id"));
                aluguel.setDataAluguel(rs.getDate("data_aluguel").toLocalDate());
                aluguel.setDataDevolucaoPrevista(rs.getDate("data_devolucao_prevista").toLocalDate());
                alugueisPendentes.add(aluguel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alugueisPendentes;
    }

    @Override
    public List<RelatorioDTO> gerarRelatoriosVendas() {
        List<RelatorioDTO> relatorios = new ArrayList<>();
        String sql = "SELECT livro_id, SUM(quantidade) as total_vendas FROM vendas GROUP BY livro_id";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                RelatorioDTO relatorio = new RelatorioDTO();
                relatorio.setLivroId(rs.getLong("livro_id"));
                relatorio.setTotalVendas(rs.getInt("total_vendas"));
                relatorios.add(relatorio);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return relatorios;
    }

    @Override
    public List<RelatorioDTO> gerarRelatoriosAluguel() {
        List<RelatorioDTO> relatorios = new ArrayList<>();
        String sql = "SELECT livro_id, COUNT(*) as total_alugueis FROM alugueis GROUP BY livro_id";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                RelatorioDTO relatorio = new RelatorioDTO();
                relatorio.setLivroId(rs.getLong("livro_id"));
                relatorio.setTotalAlugueis(rs.getInt("total_alugueis"));
                relatorios.add(relatorio);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return relatorios;
    }
}