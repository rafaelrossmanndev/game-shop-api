package br.edu.unisinos.game.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.unisinos.game.model.Item;
import br.edu.unisinos.game.repository.ItemRepository;

@Service
public class ItemService {
	@Autowired
	private ItemRepository repository;
	
	public Item save(Item item) {
		return repository.save(item);
	}
	
	public List<Item> getAll() {
		return repository.findAll();
	}
	
	public Item getOne(UUID id) {
		return repository.findById(id).orElse(null);
	}
	
	public void delete(UUID id) {
		repository.deleteById(id);
	}
}
