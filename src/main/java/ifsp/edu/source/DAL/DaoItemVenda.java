package ifsp.edu.source.DAL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ifsp.edu.source.Model.ItemVenda;
import ifsp.edu.source.Model.ItemVenda;
import ifsp.edu.source.Model.Livro;

public class DaoItemVenda {
	
	public boolean incluir(Livro livro, int id_venda, int quantidade) {
	    DataBaseCom.conectar();

	    String sqlString = "INSERT INTO item_venda (id_venda, id_produto, qtde_item) VALUES (?, ?, ?)";
	    try {
	        PreparedStatement ps = DataBaseCom.getConnection().prepareStatement(sqlString);
	        ps.setInt(1, id_venda);
	        ps.setInt(2, livro.getId());
	        ps.setInt(3, quantidade);
	        

	        int rowsAffected = ps.executeUpdate();
	        
	        
	        
	        // Verifique se alguma linha foi afetada (inserida)
	        return rowsAffected > 0;
	        
	        

	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	    return false;
	}


	
	public boolean incluir(ItemVenda v, int id_venda) {
		DataBaseCom.conectar();

		String sqlString = "insert into item_venda values(?,?,?)";
		try {
			PreparedStatement ps=DataBaseCom.getConnection().prepareStatement(sqlString);
			ps.setInt(1, v.getId());
			ps.setInt(2, id_venda);
			ps.setInt(3, v.getId_produto());


            boolean ri=ps.execute(); 
            return ri;

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return false;
	}
	
	public boolean incluir(ItemVenda v) {
		DataBaseCom.conectar();

		String sqlString = "insert into item_venda values(?,?,?,?)";
		try {
			PreparedStatement ps=DataBaseCom.getConnection().prepareStatement(sqlString);
			ps.setInt(1, v.getId());
			ps.setInt(2, v.getId_venda());
			ps.setInt(3, v.getId_produto());
			ps.setInt(4, v.getQtde_item());

			
			//System.out.println("======================"+v.getQuantidade());
            boolean ri=ps.execute(); 
            return ri;

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return false;
	}

	public boolean alterar(ItemVenda v) {
		DataBaseCom.conectar();
		if (findById(v.getId()) == null) {
			return false;
		}
		try {
			String sqlString = "update item_venda set id_venda=?, id_produto=? where id=?";
			PreparedStatement ps = DataBaseCom.getConnection().prepareStatement(sqlString);

			ps.setInt(1, v.getId_venda());
			ps.setInt(2, v.getId_produto());
			ps.setInt(3, v.getId());
			
			ps.execute();
			// statement.executeUpdate(sqlString);
			return true;
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return false;
	}

	public ItemVenda findById(long id) {
		DataBaseCom.conectar();
		ItemVenda p = null;
		try {
			ResultSet rs = DataBaseCom.getStatement().executeQuery("select * from item_venda where id=" + id);
			while (rs.next()) {
				p = new ItemVenda();
				p.setId(rs.getInt("id"));
				p.setId_venda(rs.getInt("Id_venda"));
				p.setId_produto(rs.getInt("Id_produto"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return p;
	}

	public boolean excluir(ItemVenda v) {
		DataBaseCom.conectar();
		String sqlString = "delete from item_venda where id=" + v.getId();
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
		String sqlString = "delete from item_venda where id=" + id;
		try {
			DataBaseCom.getStatement().executeUpdate(sqlString);
			return true;
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return false;
	}

	
	public List<ItemVenda> listar() {
		List<ItemVenda> lista = new ArrayList<>();
		try {
			ResultSet rs = DataBaseCom.getStatement().executeQuery("select * from item_venda");
			while (rs.next()) {
				ItemVenda p = new ItemVenda();
				p.setId(rs.getInt("id"));
				p.setId_venda(rs.getInt("Id_venda"));
				p.setId_produto(rs.getInt("Id_produto"));
				p.setQtde_item(rs.getInt("Qtde_item"));
				lista.add(p);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}
	
	public boolean verificarExistenciaLivro(int idLivro) {
        try {
            String sql = "SELECT COUNT(*) FROM produto WHERE id = ?";
            PreparedStatement ps = DataBaseCom.getConnection().prepareStatement(sql);
            ps.setInt(1, idLivro);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0; // Retorna true se o livro existe, false caso contrário
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false; // Em caso de erro ou se não houver correspondência
    }
	
	public void atualizarQuantidadeProduto(int idProduto, int quantidadeVendida) {
	    try {
	        String sql = "UPDATE produto SET qtde = qtde - ? WHERE id = ?";
	        PreparedStatement ps = DataBaseCom.getConnection().prepareStatement(sql);
	        ps.setInt(1, quantidadeVendida);
	        ps.setInt(2, idProduto);
	        ps.executeUpdate();
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	}
	
	public void atualizarItemDaVenda(int idProduto, int quantidadeVendida) {
	    try {
	        String sql = "UPDATE item_venda SET qtde_item = ? WHERE id = ?";
	        PreparedStatement ps = DataBaseCom.getConnection().prepareStatement(sql);
	        ps.setInt(1, quantidadeVendida);
	        ps.setInt(2, idProduto);
	        ps.executeUpdate();
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	}
	
}
