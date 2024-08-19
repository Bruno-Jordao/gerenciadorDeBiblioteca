package Model;

public class Aluguel {

  private int id;
	private ClienteDTO cliente;
	private LivroDTO livro;
	private LocalDate dataEmprestimo;
	private LocalDate dataDevolucaoPrevista;
	private LocalDate dataDevolucaoReal;
	private boolean devolvido;
	
	public Aluguel(int id, ClienteDTO cliente, LivroDTO livro, LocalDate dataEmprestimo, LocalDate dataDevolucaoPrevista) {
		this.id = id;
		this.cliente = cliente;
		this.livro = livro;
		this.dataEmprestimo = dataEmprestimo;
		this.dataDevolucaoPrevista = dataDevolucaoPrevista;
		this.devolvido = false;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public void setCliente(ClienteDTO cliente) {
		this.cliente = cliente;
	}
	
	public ClienteDTO getCliente() {
		return cliente;
	}
	
	public void setLivro(LivroDTO livro) {
		this.livro = livro;
	}
	
	public LivroDTO getLivro() {
		return livro;
	}
	
	public void setDataEmprestimo(LocalDate dataEmprestimo) {
		this.dataEmprestimo = dataEmprestimo;
	}
	
	public LocalDate getDataEmprstimo() {
		return dataEmprestimo;
	}
	
	public void setDataDevolucaoPrevista(LocalDate dataDevolucaoPrevista) {
		this.dataDevolucaoPrevista = dataDevolucaoPrevista;
	}
	
	public LocalDate getDataDevolucaoPrevista() {
		return dataDevolucaoPrevista;
	}
	
	public void setDataDevolucaoReal(LocalDate dataDevolucaoReal) {
		this.dataDevolucaoReal = dataDevolucaoReal;
		this.devolvido = true;
	}
	
	public LocalDate getDataDevolucaoReal() {
		return dataDevolucaoReal;
	}
	
	public boolean isDevolvido() {
		return devolvido;
	}
	
	public void setDevolvido(boolean devolvido) {
		this.devolvido = devolvido;
	}
	
	public boolean isAtrasado() {
		if(!devolvido && LocalDate.now().isAfter(dataDevolucaoPrevista)) {
			return true;
		}
		return false;
	}
	
	public long calcularDiasAtraso() {
		if(isAtrasado()) {
			return LocalDate .now().toEpochDay() - dataDevolucaoPrevista.toEpochDay();
		}
		return 0;
	}

}
