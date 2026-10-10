package br.edu.unisinos.game.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.edu.unisinos.game.model.Player;
import br.edu.unisinos.game.dto.ItemRequestDTO;
import br.edu.unisinos.game.dto.PlayerRequestDTO;
import br.edu.unisinos.game.model.Item;
import br.edu.unisinos.game.service.ItemService;
import br.edu.unisinos.game.service.PlayerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/player")
@Tag(name = "Jogadores", description = "Operações de gerenciamento de jogadores")
public class PlayerController {
	@Autowired
	private PlayerService service;
	@Autowired
	private ItemService itemService;
	
	@PostMapping
	@Operation(summary = "Cria um jogador")
	public ResponseEntity<Player> post(@RequestBody PlayerRequestDTO dto) {
		Player player = Player.builder()
						.nickname(dto.nickname())
						.wallet(dto.wallet())
						.build();
		
		Player newPlayer = service.save(player);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(newPlayer);
	}

	@PostMapping("/{playerId}/items")
	@Operation(summary = "Adiciona um item ao inventário do jogador")
	public ResponseEntity<Item> addItem(@PathVariable UUID playerId, @RequestBody ItemRequestDTO dto) {
		Player player = service.getOne(playerId);
		if (player == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
		Item item = Item.builder()
						.name(dto.name())
						.description(dto.description())
						.price(dto.price())
						.quantity(dto.quantity())
						.build();
		
		Item newItem = itemService.addToPlayerInventory(player, item);	
		return ResponseEntity.status(HttpStatus.CREATED).body(newItem);
	}
	
	@GetMapping
	@Operation(summary = "Lista todos os jogadores")
	public ResponseEntity<List<Player>> getAll() {
		return ResponseEntity.ok(service.getAll());
	}
	
	@GetMapping("/{id}")
	@Operation(summary = "Busca um jogador por ID")
	public ResponseEntity<Player> getOne(@PathVariable UUID id) {
		Player player = service.getOne(id);
		
		if (player == null) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(player);
	}
	
	@DeleteMapping("/{id}")
	@Operation(summary = "Exclui um jogador")
	public ResponseEntity<Void> delete(@PathVariable UUID id) {
		Player player = service.getOne(id);
		
		if (player == null) {
			return ResponseEntity.notFound().build();
		}
		service.delete(id);
		
		return ResponseEntity.noContent().build();
	}	
}
