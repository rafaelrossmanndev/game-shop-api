package br.edu.unisinos.game.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.unisinos.game.model.Inventory;
import br.edu.unisinos.game.repository.InventoryRepository;

@Service
public class InventoryService {
	@Autowired
	private InventoryRepository repository;
	
	public Inventory save(Inventory inventory) {
		return repository.save(inventory);
	}
	
	public List<Inventory> getAll() {
		return repository.findAll();
	}
	
	public Inventory getOne(UUID id) {
		return repository.findById(id).orElse(null);
	}
	
	public void delete(UUID id) {
		repository.deleteById(id);
	}
}
