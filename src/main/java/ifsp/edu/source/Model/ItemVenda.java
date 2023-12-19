package ifsp.edu.source.Model;

import java.util.ArrayList;
import java.util.List;

public class ItemVenda {
	private int id;
	private int id_venda;
	private int id_produto;
	private int qtde_item;
	//private List <Livro> listaLivro = new ArrayList();
	
	/*public List<Livro> getListaLivro() {
		return listaLivro;
	}
	public void setListaLivro(List<Livro> listaLivro) {
		this.listaLivro = listaLivro;
	}*/
	public int getQtde_item() {
		return qtde_item;
	}
	public void setQtde_item(int qtde_item) {
		this.qtde_item = qtde_item;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId_venda() {
		return id_venda;
	}
	public void setId_venda(int id_venda) {
		this.id_venda = id_venda;
	}
	public int getId_produto() {
		return id_produto;
	}
	public void setId_produto(int id_produto) {
		this.id_produto = id_produto;
	}
}
