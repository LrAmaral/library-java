package ifsp.edu.source.DAL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ifsp.edu.source.Model.Livro;
import ifsp.edu.source.Model.Pessoa;

public class PessoaDao {
	
	
	public boolean incluir(Pessoa v) {
		DataBaseCom.conectar();

		String sqlString = "insert into pessoa values(?,?)";
		try {
			PreparedStatement ps=DataBaseCom.getConnection().prepareStatement(sqlString);
			ps.setLong(1, v.getId());
			ps.setString(2, v.getNome());

			
			//System.out.println("======================"+v.getQuantidade());
            boolean ri=ps.execute(); 
            return ri;
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return false;
	}
	
	public boolean alterar(Pessoa v) {
		DataBaseCom.conectar();
		if (findById(v.getId()) == null) {
			return false;
		}
		try {
			String sqlString = "update pessoa set nome=? where id=?";
			PreparedStatement ps = DataBaseCom.getConnection().prepareStatement(sqlString);

			ps.setString(1, v.getNome());
			ps.setLong(2, v.getId());
			
			ps.execute();

			return true;
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return false;
	}
	
	public Pessoa findById(long id) {
		DataBaseCom.conectar();
		Pessoa p = null;
		try {
			ResultSet rs = DataBaseCom.getStatement().executeQuery("select * from pessoa where id=" + id);
			while (rs.next()) {
				p = new Pessoa();
				p.setId(rs.getLong("id"));
				p.setNome(rs.getString("nome"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return p;
	}
	
	public boolean excluir(long id) {
		DataBaseCom.conectar();
		String sqlString = "delete from pessoa where id=" + id;
		try {
			DataBaseCom.getStatement().executeUpdate(sqlString);
			return true;
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return false;
	}
	
	public List<Pessoa> listar() {
		List<Pessoa> lista = new ArrayList<>();
		try {
			ResultSet rs = DataBaseCom.getStatement().executeQuery("select * from pessoa");
			while (rs.next()) {
				Pessoa p = new Pessoa();
				p.setId(rs.getInt("id"));
				p.setNome(rs.getString("nome"));
				lista.add(p);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}
	

	
	public boolean excluir(Pessoa v) {
		DataBaseCom.conectar();
		String sqlString = "delete from pessoa where id=" + v.getId();
		try {
			DataBaseCom.getStatement().executeUpdate(sqlString);
			return true;
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return false;
	}
}
