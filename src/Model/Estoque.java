package Model;

public class Estoque {

  private static Estoque instancia;

	private Map <LivroDTO, Integer> livros;
	
	private ConexaoPostgres conexao;
	
	private Estoque() {
		this.livros = new HashMap<>();
		this.conexao = ConexaoPostgres.getInstance();
	}
	
	public static synchronized Estoque getInstance() {
		if(instancia == null) {
			instancia = new Estoque();
		}
		return instancia;
	}
	
	public void adicionarLivro(LivroDTO livro, int quantidade) {
		if(livros.containsKey(livro)) {
			livros.put(livro, livros.get(livro) + quantidade);
		}else {
			livros.put(livro, quantidade);
		}
	}
	
	public boolean removerLivro(LivroDTO livro, int quantidade) {
		if(livros.containsKey(livro)) {
			int quantidadeAtual = livros.get(livro);
			if(quantidadeAtual >= quantidade) {
				livros.put(livro, quantidadeAtual - quantidade);
				if(livros.get(livro) == 0) {
					livros.remove(livro);
				}
				return true;
			}
		}
		return false;
	}
	
	public boolean verificarDisponibilidade(LivroDTO livro) {
		return livros.containsKey(livro) && livros.get(livro)> 0;
	}
	
	public int obterQuantidade(LivroDTO livro) {
		return livros.getOrDefault(livro, 0);
	}
	
	public void exibirEstoque() {
		for(Map.Entry<LivroDTO, Integer> entrada: livros.entrySet()) {
			System.out.println("Livro: " + entrada.getKey().getTitulo() + " Quantidade: " + entrada.getValue());
		}
	}

}
