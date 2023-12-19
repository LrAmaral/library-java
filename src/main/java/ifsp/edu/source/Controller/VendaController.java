package ifsp.edu.source.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ifsp.edu.source.DAL.DaoCompra;
import ifsp.edu.source.DAL.DaoItemCompra;
import ifsp.edu.source.DAL.DaoItemVenda;
import ifsp.edu.source.DAL.DaoVenda;
import ifsp.edu.source.DAL.DataBaseCom;
import ifsp.edu.source.Model.Venda;
import ifsp.edu.source.Model.ItemVenda;
import ifsp.edu.source.Model.Livro;
import ifsp.edu.source.Model.Venda;

@RestController
public class VendaController {
	
	DataBaseCom database = new DataBaseCom();
	DaoVenda cadVendas = new DaoVenda();
	DaoItemVenda cadItemVenda = new DaoItemVenda();

	@GetMapping(value = "/venda")
	public List<Venda> listar() {
		return cadVendas.listar();
	}

	@GetMapping("/venda/{id}")
	public ResponseEntity<Venda> GetById(@PathVariable(value = "id") long id) {
		Venda venda = cadVendas.findById(id);
		if (venda != null)
			return new ResponseEntity<Venda>(venda, HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@PostMapping("/venda")
	public String Post(@Validated @RequestBody Venda venda) {
	    cadVendas.incluir(venda);

	    int id_venda = cadVendas.obterUltimoIdInserido();

	    for (Livro livro : venda.getListaLivro()) {
	        if (cadItemVenda.verificarExistenciaLivro(livro.getId())) {
	            cadItemVenda.incluir(livro, id_venda, livro.getQuantidade());
	            
	            
	        } else {
	            System.out.println("Livro não encontrado na tabela produto: " + livro.getNome());
	        }
	        
	    }

	    for (Livro livro : venda.getListaLivro()) {
	        cadItemVenda.atualizarQuantidadeProduto(livro.getId(), livro.getQuantidade());
	    }
	    
	    return "Venda Cadastrada";
	}

	@PutMapping("/venda") // update
	public String Atualizar(@Validated @RequestBody Venda newVenda) {
		cadVendas.alterar(newVenda);
		
		for (Livro livro : newVenda.getListaLivro()) {
	        cadItemVenda.atualizarItemDaVenda(livro.getId(), livro.getQuantidade());
	  }
		
		return "Venda atualizado";
	}

	@DeleteMapping("/venda/{id}") // delete
	public String Delete(@PathVariable(value = "id") long id) {
		cadVendas.excluir(id);
		return "Exclusão realizada";

	}
}
