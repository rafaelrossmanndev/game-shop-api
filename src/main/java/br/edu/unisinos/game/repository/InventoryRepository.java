package br.edu.unisinos.game.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.unisinos.game.model.Inventory;
import br.edu.unisinos.game.model.Player;

public interface InventoryRepository extends JpaRepository<Inventory, UUID> {
	Inventory findByPlayer(Player player);

}
