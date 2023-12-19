package ifsp.edu.source.DAL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ifsp.edu.source.Model.Venda;
import ifsp.edu.source.Model.Livro;

public class DaoVenda {
	
DaoItemVenda cadItemVenda = new DaoItemVenda();

public int incluir(Venda v) {
    DataBaseCom.conectar();

    String sqlString = "INSERT INTO venda (id_cliente, data) VALUES (?, ?)";
    try {
        PreparedStatement ps = DataBaseCom.getConnection().prepareStatement(sqlString);
        ps.setInt(1, v.getId_cliente());
        ps.setString(2, v.getData());

        int rowsAffected = ps.executeUpdate();

        if (rowsAffected > 0) {
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }

    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return -1;
}	


	public boolean alterar(Venda v) {
		DataBaseCom.conectar();
		if (findById(v.getId()) == null) {
			return false;
		}
		try {
			String sqlString = "update venda set id_cliente=?, data=? where id=?";
			PreparedStatement ps = DataBaseCom.getConnection().prepareStatement(sqlString);

			ps.setLong(1, v.getId_cliente());
			ps.setString(2, v.getData());
			ps.setInt(3, v.getId());
			ps.execute();

			return true;
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return false;
	}

	public Venda findById(long id) {
		DataBaseCom.conectar();
		Venda p = null;
		try {
			ResultSet rs = DataBaseCom.getStatement().executeQuery("select * from venda where id=" + id);
			while (rs.next()) {
				p = new Venda();
				p.setId(rs.getInt("id"));
				p.setId_cliente(rs.getInt("id_cliente"));
				List<Livro> listaLivro = obterListaProdutosPorCompra(p.getId());
		        p.setListaLivro(listaLivro);
				p.setData(rs.getString("data"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return p;
	}

	public boolean excluir(Venda v) {
		DataBaseCom.conectar();
		String sqlString = "delete from venda where id=" + v.getId();
		try {
			DataBaseCom.getStatement().executeUpdate(sqlString);
			return true;
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return false;
	}
	
	public boolean excluir(long id) {
		DataBaseCom.conectar();
		String sqlString = "delete from venda where id=" + id;
		try {
			DataBaseCom.getStatement().executeUpdate(sqlString);
			return true;
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return false;
	}

	
	public int obterUltimoIdInserido() {
	    try {
	        ResultSet rs = DataBaseCom.getStatement().executeQuery("SELECT last_insert_rowid() AS last_id FROM venda");
	        if (rs.next()) {
	            return rs.getInt("last_id");
	        }
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	    return -1;
	}

	
	public List<Venda> listar() {
	    List<Venda> lista = new ArrayList<>();
	    try {
	        ResultSet rs = DataBaseCom.getStatement().executeQuery("SELECT * FROM venda");
	        while (rs.next()) {
	            Venda venda = new Venda();
	            venda.setId(rs.getInt("id"));
	            venda.setId_cliente(rs.getInt("id_cliente"));
	            venda.setData(rs.getString("data"));
	            
	            List<Livro> listaLivro = obterListaProdutosPorCompra(venda.getId());
	            venda.setListaLivro(listaLivro);

	            lista.add(venda);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return lista;
	}
	

	private List<Livro> obterListaProdutosPorCompra(int idVenda) {
	    List<Livro> listaProdutos = new ArrayList<>();
	    try {

	    	
	    	String sql = "SELECT produto.*, item_venda.qtde_item " +
            "FROM produto " +
            "INNER JOIN item_venda ON produto.id = item_venda.id_produto " +
            "WHERE item_venda.id_venda = ?";
	        PreparedStatement ps = DataBaseCom.getConnection().prepareStatement(sql);
	        ps.setInt(1, idVenda);
	        ResultSet rs = ps.executeQuery();

	        while (rs.next()) {
	            Livro livro = new Livro();
	            livro.setId(rs.getInt("id"));
	            livro.setNome(rs.getString("nome"));
	            livro.setQuantidade(rs.getInt("qtde_item"));
	            livro.setPreco(rs.getDouble("preco"));
	            listaProdutos.add(livro);
	        }
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	    return listaProdutos;
	}
}
