package br.edu.unisinos.game.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.unisinos.game.dto.PurchaseRequestDTO;
import br.edu.unisinos.game.model.Item;
import br.edu.unisinos.game.service.StoreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/store")
@Tag(name = "Loja", description = "Operações de catálogo e compras")
public class StoreController {
	@Autowired
	private StoreService service;
	
	@PostMapping("/buy")
	@Operation(summary = "Compra um item")
	public ResponseEntity<String> buy(@RequestBody PurchaseRequestDTO dto) {
		try {
			String message = service.buyItem(dto.playerId(), dto.itemId());
			return ResponseEntity.ok(message);
			
		} catch(RuntimeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@GetMapping("/catalog")
	@Operation(summary = "Consulta o catálogo da loja")
	public ResponseEntity<List<Item>> getCatalog() {
		List<Item> catalog = service.getCatalog();
		
		if (catalog.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		
		return ResponseEntity.ok(catalog);
	}
}
