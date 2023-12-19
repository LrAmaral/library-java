package ifsp.edu.source.Model;

import java.util.ArrayList;
import java.util.List;

public abstract class Transacao {
	private int id;
    private int id_cliente;
    private List<Livro> listaLivro = new ArrayList<>();
    private String data;

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Livro> getListaLivro() {
        return listaLivro;
    }

    public void setListaLivro(List<Livro> listaLivro) {
        this.listaLivro = listaLivro;
    }

    public void adicionarLivro(Livro livro) {
        this.listaLivro.add(livro);
    }
}


