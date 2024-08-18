
public class ItemVenda {

	private String nome;
	private double preco;
	private int quantidade;
	
	public ItemVenda(String nome, double preco, int quantidade) {
		this.nome = nome;
		this.preco = preco;
		this.quantidade = quantidade;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setPreco(double preco) {
		this.preco = preco;
	}
	
	public double getPreco() {
		return preco;
	}
	
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	
	public int getQuantidade() {
		return quantidade;
	}
	
	public void decrementarQuantidade() {
		if(quantidade > 0) {
			quantidade--;
		}
	}
	
	public void incrementarQuantidade() {
		quantidade++;
	}
	
}
