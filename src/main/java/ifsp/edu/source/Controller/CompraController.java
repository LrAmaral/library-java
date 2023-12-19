package ifsp.edu.source.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ifsp.edu.source.DAL.DaoCompra;
import ifsp.edu.source.DAL.DaoItemCompra;
import ifsp.edu.source.DAL.DataBaseCom;
import ifsp.edu.source.DAL.PessoaDao;
import ifsp.edu.source.Model.Compra;
import ifsp.edu.source.Model.ItemCompra;
import ifsp.edu.source.Model.Livro;
import ifsp.edu.source.Model.Pessoa;
import ifsp.edu.source.Model.Venda;

@RestController
public class CompraController {

  DataBaseCom database = new DataBaseCom();
	DaoCompra cadCompras = new DaoCompra();
	DaoItemCompra cadItemCompra = new DaoItemCompra();

	@GetMapping(value = "/compra")
	public List<Compra> listar() {
		return cadCompras.listar();
	}

	@GetMapping("/compra/{id}")
	public ResponseEntity<Compra> GetById(@PathVariable(value = "id") long id) {
		Compra compra = cadCompras.findById(id);
		if (compra != null)
			return new ResponseEntity<Compra>(compra, HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@PostMapping("/compra")
	public String Post(@Validated @RequestBody Compra compra) {
	    cadCompras.incluir(compra);

	    int id_compra = cadCompras.obterUltimoIdInserido();

	    for (Livro livro : compra.getListaLivro()) {
	        if (cadItemCompra.verificarExistenciaLivro(livro.getId())) {
	            cadItemCompra.incluir(livro, id_compra, livro.getQuantidade());
	            
	            
	        } else {
	            System.out.println("Livro não encontrado na tabela produto: " + livro.getNome());
	        }
	        
	    }

	    for (Livro livro : compra.getListaLivro()) {
	        cadItemCompra.atualizarQuantidadeProduto(livro.getId(), livro.getQuantidade());
	    }
	    
	    //cadItemVenda.atualizarQuantidadeProduto(livro.getId(), livro.getQuantidade());
	    return "Compra Cadastrada";
	}
	
	@PutMapping("/compra") // update
	public String Atualizar(@Validated @RequestBody Compra newCompra) {
		cadCompras.alterar(newCompra);
		
		for (Livro livro : newCompra.getListaLivro()) {
	        cadItemCompra.atualizarItemDaCompra(livro.getId(), livro.getQuantidade());
	    }
		
		return "Compra atualizada";
	}

	@DeleteMapping("/compra/{id}") // delete
	public String Delete(@PathVariable(value = "id") long id) {
		cadCompras.excluir(id);
		return "Exclusão realizada";

	}
}