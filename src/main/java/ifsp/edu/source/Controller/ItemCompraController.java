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

import ifsp.edu.source.DAL.DaoItemCompra;
import ifsp.edu.source.DAL.DaoLivro;
import ifsp.edu.source.DAL.DataBaseCom;
import ifsp.edu.source.Model.ItemCompra;
import ifsp.edu.source.Model.Livro;

@RestController
public class ItemCompraController {
	
	DataBaseCom database = new DataBaseCom();
	DaoItemCompra cadItemCompra = new DaoItemCompra();

	@GetMapping(value = "/itemcompra")
	public List<ItemCompra> listar() {
		return cadItemCompra.listar();
	}

	@GetMapping("/itemcompra/{id}")
	public ResponseEntity<ItemCompra> GetById(@PathVariable(value = "id") long id) {
		ItemCompra itemCompra = cadItemCompra.findById(id);
		if (itemCompra != null)
			return new ResponseEntity<ItemCompra>(itemCompra, HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@PostMapping("/itemcompra") // incluir
	public String Post(@Validated @RequestBody ItemCompra itemCompra) {
		cadItemCompra.incluir(itemCompra);
		return "Item da Compra Cadastrado";
	}

	@PutMapping("/itemcompra") // update
	public String Atualizar(@Validated @RequestBody ItemCompra newItemCompra) {
		cadItemCompra.alterar(newItemCompra);
		return "Item da Compra atualizado";
	}

	@DeleteMapping("/itemcompra/{id}") // delete
	public String Delete(@PathVariable(value = "id") long id) {
		cadItemCompra.excluir(id);
		return "Exclusão realizada";

	}

}
