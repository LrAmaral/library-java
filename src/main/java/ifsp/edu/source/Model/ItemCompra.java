package ifsp.edu.source.Model;

public class ItemCompra{
	private int id;
	private int id_compra;
	private int id_produto;
	private int qtde_item;
	
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
	public int getId_compra() {
		return id_compra;
	}
	public void setId_compra(int id_compra) {
		this.id_compra = id_compra;
	}
	public int getId_produto() {
		return id_produto;
	}
	public void setId_produto(int id_produto) {
		this.id_produto = id_produto;
	}
}
