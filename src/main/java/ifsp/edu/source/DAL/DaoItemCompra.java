package ifsp.edu.source.DAL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ifsp.edu.source.Model.ItemCompra;
import ifsp.edu.source.Model.Livro;

public class DaoItemCompra {

	public boolean incluir(Livro livro, int id_compra, int quantidade) {
	    DataBaseCom.conectar();

	    String sqlString = "INSERT INTO item_compra (id_compra, id_produto, qtde_item) VALUES (?, ?, ?)";
	    try {
	        PreparedStatement ps = DataBaseCom.getConnection().prepareStatement(sqlString);
	        ps.setInt(1, id_compra);
	        ps.setInt(2, livro.getId());
	        ps.setInt(3, quantidade);
	        
	        int rowsAffected = ps.executeUpdate();
	        return rowsAffected > 0;

	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	    return false;
	}
	
	public boolean incluir(ItemCompra v, int id_compra) {
		DataBaseCom.conectar();

		String sqlString = "insert into item_compra values(?,?,?)";
		try {
			PreparedStatement ps=DataBaseCom.getConnection().prepareStatement(sqlString);
			ps.setInt(1, v.getId());
			ps.setInt(2, id_compra);
			ps.setInt(3, v.getId_produto());

			
			//System.out.println("======================"+v.getQuantidade());
      boolean ri=ps.execute(); 
      return ri;

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return false;
	}
	
	public boolean incluir(ItemCompra v) {
		DataBaseCom.conectar();

		String sqlString = "insert into item_compra values(?,?,?)";
		try {
			PreparedStatement ps=DataBaseCom.getConnection().prepareStatement(sqlString);
			ps.setInt(1, v.getId());
			ps.setInt(2, v.getId_compra());
			ps.setInt(3, v.getId_produto());

			
			//System.out.println("======================"+v.getQuantidade());
      boolean ri=ps.execute(); 
      return ri;

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return false;
	}

	public boolean alterar(ItemCompra v) {
		DataBaseCom.conectar();
		if (findById(v.getId()) == null) {
			return false;
		}
		try {
			String sqlString = "update item_compra set id_compra=?, id_produto=? where id=?";
			PreparedStatement ps = DataBaseCom.getConnection().prepareStatement(sqlString);

			ps.setInt(1, v.getId_compra());
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

	public ItemCompra findById(long id) {
		DataBaseCom.conectar();
		ItemCompra p = null;
		try {
			ResultSet rs = DataBaseCom.getStatement().executeQuery("select * from item_compra where id=" + id);
			while (rs.next()) {
				p = new ItemCompra();
				p.setId(rs.getInt("id"));
				p.setId_compra(rs.getInt("Id_compra"));
				p.setId_produto(rs.getInt("Id_produto"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return p;
	}

	public boolean excluir(ItemCompra v) {
		DataBaseCom.conectar();
		String sqlString = "delete from item_compra where id=" + v.getId();
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
		String sqlString = "delete from item_compra where id=" + id;
		try {
			DataBaseCom.getStatement().executeUpdate(sqlString);
			return true;
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return false;
	}
	
	public List<ItemCompra> listar() {
		List<ItemCompra> lista = new ArrayList<>();
		try {
			ResultSet rs = DataBaseCom.getStatement().executeQuery("select * from item_compra");
			while (rs.next()) {
				ItemCompra p = new ItemCompra();
				p.setId(rs.getInt("id"));
				p.setId_compra(rs.getInt("Id_compra"));
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
                return count > 0;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false; 
    }
	
	public void atualizarQuantidadeProduto(int idProduto, int quantidadeVendida) {
	    try {
	        String sql = "UPDATE produto SET qtde = qtde + ? WHERE id = ?";
	        PreparedStatement ps = DataBaseCom.getConnection().prepareStatement(sql);
	        ps.setInt(1, quantidadeVendida);
	        ps.setInt(2, idProduto);
	        ps.executeUpdate();
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	}
	
	public void atualizarItemDaCompra(int idProduto, int quantidadeVendida) {
	    try {
	        String sql = "UPDATE item_compra SET qtde_item = ? WHERE id = ?";
	        PreparedStatement ps = DataBaseCom.getConnection().prepareStatement(sql);
	        ps.setInt(1, quantidadeVendida);
	        ps.setInt(2, idProduto);
	        ps.executeUpdate();
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	}
}

