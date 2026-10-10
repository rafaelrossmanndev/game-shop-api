package br.edu.unisinos.game.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Adicione esta importação

import br.edu.unisinos.game.model.Item;
import br.edu.unisinos.game.model.Player;
import br.edu.unisinos.game.repository.InventoryRepository;
import br.edu.unisinos.game.repository.PlayerRepository;
import br.edu.unisinos.game.repository.ItemRepository;

@Service
public class StoreService {
	@Autowired
	private InventoryRepository inventoryRepository;

	@Autowired
	private PlayerRepository playerRepository;
	
	@Autowired
	private ItemRepository itemRepository;
	
	@Autowired
	private ItemService itemService;


	@Transactional
	public String buyItem(UUID playerId, UUID itemId) {
		Player player = playerRepository.findById(playerId).orElseThrow(() -> new RuntimeException("Player not found"));
		Item storeItem = itemRepository.findById(itemId).orElseThrow(() -> new RuntimeException("Item not found"));
		
		if (storeItem.getQuantity() <= 0) {
			throw new RuntimeException("Item out of stock");
		}
		
		if (player.getWallet() < storeItem.getPrice()) {
			throw new RuntimeException("Insufficient funds");
		}
		
		player.setWallet(player.getWallet() - storeItem.getPrice());
		playerRepository.save(player); 
        
		storeItem.setQuantity(storeItem.getQuantity() - 1);
		itemRepository.save(storeItem);
		
		if (player.getInventory() != null && player.getInventory().getItems() != null) {
            Optional<Item> existingItem = player.getInventory().getItems().stream()
                .filter(i -> i.getName().equals(storeItem.getName()))
                .findFirst();

            if (existingItem.isPresent()) {
                Item itemToUpdate = existingItem.get();
                itemToUpdate.setQuantity(itemToUpdate.getQuantity() + 1);
                itemRepository.save(itemToUpdate);
                
                return ("Purchase successful.");
            }
        }
		
		Item purchasedItem = Item.builder()
								 .name(storeItem.getName())
								 .description(storeItem.getDescription())
								 .price(storeItem.getPrice())
								 .quantity(1)
								 .build();
		
		itemService.addToPlayerInventory(player, purchasedItem);
		return ("Purchase successful.");
	}
	
	public List<Item> getCatalog() {
		return itemRepository.findByInventoryIsNull();
	}
}