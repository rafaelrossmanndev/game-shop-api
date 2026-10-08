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

import br.edu.unisinos.game.model.Item;
import br.edu.unisinos.game.service.ItemService;


@RestController
@RequestMapping("/item")
public class ItemController {
	@Autowired
	private ItemService service;
	
	@PostMapping
	public Item post(@RequestBody Item item) {
		return service.save(item);
	}
	
	@GetMapping
	public List<Item> getAll() {
		return service.getAll();
	}
	
	@GetMapping("/{id}") 
	public Item getOne(@PathVariable UUID id) {
		return service.getOne(id);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable UUID id) {
		service.delete(id);
	}
}
