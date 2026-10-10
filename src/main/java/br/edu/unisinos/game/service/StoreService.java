package br.edu.unisinos.game.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Adicione esta importação

import br.edu.unisinos.game.model.Item;
import br.edu.unisinos.game.model.Player;
import br.edu.unisinos.game.repository.InventoryRepository;
import br.edu.unisinos.game.repository.PlayerRepository;
import br.edu.unisinos.game.repository.ItemRepository;
import br.edu.unisinos.game.strategy.DiscountStrategyFactory;
import br.edu.unisinos.game.strategy.DiscountStrategy;

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

	@Autowired
	private DiscountStrategyFactory discountStrategyFactory;


	@Transactional
	public String buyItem(UUID playerId, UUID itemId) {
		Player player = playerRepository.findById(playerId).orElseThrow(() -> new RuntimeException("Player not found"));
		Item storeItem = itemRepository.findById(itemId).orElseThrow(() -> new RuntimeException("Item not found"));
		
		if (storeItem.getQuantity() <= 0) {
			throw new RuntimeException("Item out of stock");
		}

		if (storeItem.getType() == null) {
			throw new IllegalArgumentException("Item type is required");
		}

		DiscountStrategy strategy =
				discountStrategyFactory.forType(storeItem.getType());

		BigDecimal originalPrice =
				BigDecimal.valueOf(storeItem.getPrice());

		BigDecimal discount =
				strategy.calculateDiscount(originalPrice);

		BigDecimal finalPrice = originalPrice
				.subtract(discount)
				.setScale(2, RoundingMode.HALF_UP);

		BigDecimal wallet =
				BigDecimal.valueOf(player.getWallet());

		if (wallet.compareTo(finalPrice) < 0) {
			throw new RuntimeException("Insufficient funds");
		}

		BigDecimal remainingWallet = wallet.subtract(finalPrice);

		player.setWallet(remainingWallet.doubleValue());
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
				   				 .type(storeItem.getType())
								 .build();
		
		itemService.addToPlayerInventory(player, purchasedItem);
		return ("Purchase successful.");
	}
	
	public List<Item> getCatalog() {
		return itemRepository.findByInventoryIsNull();
	}
}