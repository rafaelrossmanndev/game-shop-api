package br.edu.unisinos.game.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.unisinos.game.dto.ItemRequestDTO;
import br.edu.unisinos.game.model.Item;
import br.edu.unisinos.game.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/item")
@Tag(name = "Itens", description = "Operações de gerenciamento de itens")
public class ItemController {
	@Autowired
	private ItemService service;
	
	@PostMapping
	@Operation(summary = "Cria um item")
	public ResponseEntity<Item> post(@RequestBody ItemRequestDTO dto) {

		if (dto.type() == null) {
			return ResponseEntity.badRequest().build();
		}

		Item item = Item.builder()
						.name(dto.name())
						.description(dto.description())
						.price(dto.price())
						.quantity(dto.quantity())
						.type(dto.type())
						.build();
		
		Item newItem = service.save(item);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(newItem);
	}
	
	@GetMapping
	@Operation(summary = "Lista todos os itens")
	public ResponseEntity<List<Item>> getAll() {
		return ResponseEntity.ok(service.getAll());
	}
	
	@GetMapping("/{id}") 
	@Operation(summary = "Busca um item por ID")
	public ResponseEntity<Item> getOne(@PathVariable UUID id) {
		Item item = service.getOne(id);
		
		if (item == null) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(item);
	}
	
	@DeleteMapping("/{id}")
	@Operation(summary = "Exclui um item")
	public ResponseEntity<Void> delete(@PathVariable UUID id) {
		Item item = service.getOne(id);
		
		if (item == null) {
			return ResponseEntity.notFound().build();
		}
		service.delete(id);
		
		return ResponseEntity.noContent().build();
	}
}
