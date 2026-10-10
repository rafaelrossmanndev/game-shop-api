package br.edu.unisinos.game.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.unisinos.game.model.Inventory;
import br.edu.unisinos.game.model.Item;
import br.edu.unisinos.game.model.Player;
import br.edu.unisinos.game.repository.InventoryRepository;
import br.edu.unisinos.game.repository.ItemRepository;

@Service
public class ItemService {
	@Autowired
	private ItemRepository repository;
	@Autowired
	private InventoryRepository inventoryRepository;
	
	public Item save(Item item) {
		return repository.save(item);
	}

	@Transactional
	public Item addToPlayerInventory(Player player, Item item) {
		Inventory inventory = inventoryRepository.findByPlayer(player);
		if (inventory == null) {
			inventory = new Inventory();
			inventory.setPlayer(player);
			inventory = inventoryRepository.save(inventory);
		}

		inventory.addItem(item);
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
