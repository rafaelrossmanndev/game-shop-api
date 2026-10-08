package br.edu.unisinos.game.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.unisinos.game.model.Player;
import br.edu.unisinos.game.repository.PlayerRepository;

@Service
public class PlayerService {
	@Autowired
	private PlayerRepository repository;
	
	public Player save(Player player) {
		return repository.save(player);
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
