package ifsp.edu.source.DAL;

import java.beans.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ifsp.edu.source.Controller.PessoaController;
import ifsp.edu.source.Model.Compra;
import ifsp.edu.source.Model.Livro;
import ifsp.edu.source.Model.Pessoa;
import ifsp.edu.source.Model.Venda;

public class DaoCompra {
	
	public boolean incluirC(Compra v) {
		DataBaseCom.conectar();
		
		//INSERT
		//criar um insercao na tabela compras inserindo um id
		//colocar o id do cliente.
		
		//INSERT
		//pegar o id da compra e criar uma insercao na tabela itemproduto
		// você vincula o id da compra com o id dos produtos na tabela itemproduto
		
		//UPDATE		
		//atualizar a quantidade de produtos na tabela produtos
		
		
		//FIM DA VENDA
		
		/* exemplo de JSON a ser passado no POSTMAN
		 * {"id":1,
		 *  "Cliente":
		 *     {
		 *       "id":1,
		 *       "nome":"Joao"
		 *      }
		 *  "Itens":[
		 *      {
		 *        "id":1,
		 *        "nome":"Cem anos",
		 *        "quantidade":1,
		 *        "preco":12.4
		 *      },{
		 *        "id":2,
		 *        "nome":"Duzentos dias",
		 *        "quantidade":3,
		 *        "preco":25.4
		 *      }
		 *   ]
		 * }
		 *      
		 *
		 * 
		 * 
		 * 
		 * 
		 * 
		 */
		return true;

	}
	
	DaoItemCompra cadItemCompra = new DaoItemCompra();
	
	public int incluir(Compra v) {
	    DataBaseCom.conectar();

	    String sqlString = "INSERT INTO compra (data) VALUES (?)";
	    try {
	        PreparedStatement ps = DataBaseCom.getConnection().prepareStatement(sqlString);
	        //ps.setInt(1, v.getId_cliente());
	        ps.setString(1, v.getData());

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
	
	public boolean alterar(Compra v) {
		DataBaseCom.conectar();
		if (findById(v.getId()) == null) {
			return false;
		}
		try {
			String sqlString = "update compra set data=? where id=?";
			PreparedStatement ps = DataBaseCom.getConnection().prepareStatement(sqlString);

			ps.setString(1, v.getData());
			ps.setInt(2, v.getId());
			ps.execute();

			return true;
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return false;
	}

	public Compra findById(long id) {
		DataBaseCom.conectar();
		Compra p = null;
		try {
			ResultSet rs = DataBaseCom.getStatement().executeQuery("select * from compra where id=" + id);
			while (rs.next()) {
				p = new Compra();
				p.setId(rs.getInt("id"));
				List<Livro> listaLivro = obterListaProdutosPorCompra(p.getId());
		        p.setListaLivro(listaLivro);
				p.setData(rs.getString("data"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return p;
	}

	public boolean excluir(Compra v) {
		DataBaseCom.conectar();
		String sqlString = "delete from compra where id=" + v.getId();
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
		String sqlString = "delete from compra where id=" + id;
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
	        ResultSet rs = DataBaseCom.getStatement().executeQuery("SELECT last_insert_rowid() AS last_id FROM compra");
	        if (rs.next()) {
	            return rs.getInt("last_id");
	        }
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	    return -1;
	}

	public List<Compra> listar() {
	    List<Compra> lista = new ArrayList<>();
	    try {
	        ResultSet rs = DataBaseCom.getStatement().executeQuery("SELECT * FROM compra");
	        while (rs.next()) {
	            Compra compra = new Compra();
	            compra.setId(rs.getInt("id"));
	            compra.setData(rs.getString("data"));
	            
	            List<Livro> listaLivro = obterListaProdutosPorCompra(compra.getId());
	            compra.setListaLivro(listaLivro);

	            lista.add(compra);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return lista;
	}

	private List<Livro> obterListaProdutosPorCompra(int idCompra) {
	    List<Livro> listaProdutos = new ArrayList<>();
	    try {
	        String sql = "SELECT produto.*, item_compra.qtde_item " + "FROM produto " +
	                     "INNER JOIN item_compra ON produto.id = item_compra.id_produto " +
	                     "WHERE item_compra.id_compra = ?";
	        PreparedStatement ps = DataBaseCom.getConnection().prepareStatement(sql);
	        ps.setInt(1, idCompra);
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
