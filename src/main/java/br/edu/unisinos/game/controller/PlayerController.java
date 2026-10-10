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
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import br.edu.unisinos.game.model.Player;
import br.edu.unisinos.game.model.Item;
import br.edu.unisinos.game.service.ItemService;
import br.edu.unisinos.game.service.PlayerService;


@RestController
@RequestMapping("/player")
public class PlayerController {
	@Autowired
	private PlayerService service;
	@Autowired
	private ItemService itemService;
	
	@PostMapping
	public Player post(@RequestBody Player player) {
		return service.save(player);
	}

	@PostMapping("/{playerId}/items")
	public Item addItem(@PathVariable UUID playerId, @RequestBody Item item) {
		Player player = service.getOne(playerId);
		if (player == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Player not found");
		}
		return itemService.addToPlayerInventory(player, item);
	}
	
	@GetMapping
	public List<Player> getAll() {
		return service.getAll();
	}
	
	@GetMapping("/{id}")
	public Player getOne(@PathVariable UUID id) {
		return service.getOne(id);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable UUID id) {
		service.delete(id);
	}	
}
