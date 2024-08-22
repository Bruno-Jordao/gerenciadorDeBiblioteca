package Model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import Model.DTO.ClienteDTO;


public class Venda {

  private int idVenda;
	private Date dataVenda;
	private ClienteDTO cliente;
	private List<ItemVenda> itens;
	private int totalVenda;
	private FormaPagamento formaPagamento;
	
	public Venda(int idVenda, Date dataVenda, ClienteDTO cliente, FormaPagamento formaPagamento) {
		this.idVenda = idVenda;
		this.dataVenda = dataVenda;
		this.cliente = cliente;
		this.itens = new ArrayList<>();
		this.totalVenda = 0;
		this.formaPagamento = formaPagamento;
	}
	
	public void setIdVenda(int idVenda) {
		this.idVenda = idVenda;
	}
	
	public int getIdVenda() {
		return idVenda;
	}
	
	public void setDataVenda(Date dataVenda) {
		this.dataVenda = dataVenda;
	}
	
	public Date getDataVenda() {
		return dataVenda;
	}
	
	public void setClienteDTO(ClienteDTO cliente) {
		this.cliente = cliente;
	}
	
	public ClienteDTO getClienteDTO() {
		return cliente;
	}
	
	public List<ItemVenda> getItens(){
		return itens;
	}
	
	public int getTotalVendas() {
		return totalVenda;
	}
	
	public void setFormaPagamento(FormaPagamento formaPagamento) {
		this.formaPagamento = formaPagamento;
	}
	
	public FormaPagamento getFormaPagamento() {
		return formaPagamento;
	}
	
	public boolean adicionarItem(ItemVenda item) {
		if(item.getQuantidade() > 0) {
			itens.add(item);
			totalVenda += item.getPreco();
			item.decrementarQuantidade();
			return true;
		}else {
			System.out.println("Estoque insuficiente para o item: " + item.getNome());
			return false;
		}
	}
	
	public boolean removerItem(ItemVenda item) {
		if(itens.remove(item)) {
			totalVenda -= item.getPreco();
			item.incrementarQuantidade();
			return true;
		}
		return false;
	}
	
	public void imprimirNotaFiscal() {
		System.out.println("Nota Fiscal");
		System.out.println("ID Venda " + idVenda);
		System.out.println("Data:" + dataVenda);
		System.out.println("Cliente:" + cliente);
		System.out.println("Forma de Pagamento:" + formaPagamento.getDescricao());
		System.out.println("Itens Vendidos:");
		for(ItemVenda item:itens) {
			System.out.println(item.getNome() + "R$ " + item.getPreco() + " (Qtd: " + item.getQuantidade() + ")");
		}
		System.out.println("Total de Venda R$ " + totalVenda);
	}
	
	public void finalizarVenda() {
		System.out.println("Venda finalizada");
		imprimirNotaFiscal();
	}

}
