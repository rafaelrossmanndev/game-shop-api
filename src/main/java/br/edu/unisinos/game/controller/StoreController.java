package br.edu.unisinos.game.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.unisinos.game.dto.PurchaseRequestDTO;
import br.edu.unisinos.game.service.StoreService;

@RestController
@RequestMapping("/store")
public class StoreController {
	@Autowired
	private StoreService service;
	
	@PostMapping("/buy")
	public ResponseEntity<String> buy(@RequestBody PurchaseRequestDTO dto) {
		try {
			String message = service.buyItem(dto.playerId(), dto.itemId());
			return ResponseEntity.ok(message);
			
		} catch(RuntimeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
