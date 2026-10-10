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

import br.edu.unisinos.game.model.Inventory;
import br.edu.unisinos.game.service.InventoryService;


@RestController
@RequestMapping("/inventory")
public class InventoryController {
	@Autowired
	private InventoryService service;
	
	@PostMapping
	public ResponseEntity<Inventory> post(@RequestBody Inventory inventory) {
		Inventory newInventory = service.save(inventory);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(newInventory);
	}
	
	@GetMapping
	public ResponseEntity<List<Inventory>> getAll() {
		return ResponseEntity.ok(service.getAll());
	}
	
	@GetMapping("/{id}") 
	public ResponseEntity<Inventory> getOne(@PathVariable UUID id) {
		Inventory inventory = service.getOne(id);
		
		if (inventory == null) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(inventory);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable UUID id) {
		Inventory inventory = service.getOne(id);
		
		if (inventory == null) {
			return ResponseEntity.notFound().build();
		}
		service.delete(id);
		
		return ResponseEntity.noContent().build();
	}
}
