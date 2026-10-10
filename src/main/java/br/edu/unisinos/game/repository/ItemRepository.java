package br.edu.unisinos.game.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.unisinos.game.model.Item;

public interface ItemRepository extends JpaRepository<Item, UUID> {
	List<Item> findByInventoryIsNull();
}
