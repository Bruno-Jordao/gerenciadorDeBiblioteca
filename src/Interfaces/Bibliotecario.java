package Interfaces;

import Model.DTO.AluguelDTO;
import Model.DTO.BibliotecarioDTO;
import Model.DTO.LivroDTO;
import Model.DTO.RelatorioDTO;

import java.util.List;

public interface Bibliotecario {

    // Método para adicionar um novo bibliotecário
    void addBibliotecario(BibliotecarioDTO bibliotecario);

    // Método para recuperar um bibliotecário pelo ID
    BibliotecarioDTO getBibliotecarioById(long id);

    // Método para recuperar todos os bibliotecários
    List<BibliotecarioDTO> getAllBibliotecarios();

    // Método para atualizar as informações de um bibliotecário existente
    void updateBibliotecario(BibliotecarioDTO bibliotecario);

    // Método para remover um bibliotecário pelo ID
    void deleteBibliotecario(long id);

    void registrarAluguel(AluguelDTO aluguel);
    void adicionarEstoque(LivroDTO livro, int quantidade);
    void removerEstoque(LivroDTO livro, int quantidade);
    int consultarEstoque(LivroDTO livro);
    void registrarVenda(LivroDTO livro, int quantidade);
    void devolverLivroAlugado(long aluguelId);

    List<AluguelDTO> consultarAlugueisPendentes();
    List<RelatorioDTO> gerarRelatoriosVendas(); // Supondo que você tenha um DTO para relatórios de vendas
    List<RelatorioDTO> gerarRelatoriosAluguel(); // Supondo que você tenha um DTO para relatórios de aluguel
}

