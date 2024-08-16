package Model.DTO;

public class RelatorioDTO {
    private long livroId;
    private int totalVendas;
    private int totalAlugueis;

    // Getters e Setters
    public long getLivroId() {
        return livroId;
    }

    public void setLivroId(long livroId) {
        this.livroId = livroId;
    }

    public int getTotalVendas() {
        return totalVendas;
    }

    public void setTotalVendas(int totalVendas) {
        this.totalVendas = totalVendas;
    }

    public int getTotalAlugueis() {
        return totalAlugueis;
    }

    public void setTotalAlugueis(int totalAlugueis) {
        this.totalAlugueis = totalAlugueis;
    }
}