package br.edu.unisinos.game.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.unisinos.game.model.Inventory;
import br.edu.unisinos.game.model.Player;
import br.edu.unisinos.game.repository.InventoryRepository;
import br.edu.unisinos.game.repository.PlayerRepository;

@Service
public class PlayerService {
	@Autowired
	private PlayerRepository repository;
	@Autowired
	private InventoryRepository inventoryRepository;
	
	@Transactional
	public Player save(Player player) {
		Player savedPlayer = repository.save(player);
		Inventory inventory = new Inventory();
		inventory.setPlayer(savedPlayer);
		inventoryRepository.save(inventory);
		savedPlayer.setInventory(inventory);
		return savedPlayer;
	}
	
	public List<Player> getAll() {
		return repository.findAll();
	}
	
	public Player getOne(UUID id) {
		return repository.findById(id).orElse(null);
	}
	
	public void delete(UUID id) {
		repository.deleteById(id);
	}
}
