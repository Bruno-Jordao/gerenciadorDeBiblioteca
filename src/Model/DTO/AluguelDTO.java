package Model.DTO;

import java.time.LocalDate;

public class AluguelDTO {
    private long id;
    private long livroId;
    private long bibliotecarioId;
    private LocalDate dataAluguel;
    private LocalDate dataDevolucaoPrevista;

    // Getters e Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getLivroId() {
        return livroId;
    }

    public void setLivroId(long livroId) {
        this.livroId = livroId;
    }

    public long getBibliotecarioId() {
        return bibliotecarioId;
    }

    public void setBibliotecarioId(long bibliotecarioId) {
        this.bibliotecarioId = bibliotecarioId;
    }

    public LocalDate getDataAluguel() {
        return dataAluguel;
    }

    public void setDataAluguel(LocalDate dataAluguel) {
        this.dataAluguel = dataAluguel;
    }

    public LocalDate getDataDevolucaoPrevista() {
        return dataDevolucaoPrevista;
    }

    public void setDataDevolucaoPrevista(LocalDate dataDevolucaoPrevista) {
        this.dataDevolucaoPrevista = dataDevolucaoPrevista;
    }
}
