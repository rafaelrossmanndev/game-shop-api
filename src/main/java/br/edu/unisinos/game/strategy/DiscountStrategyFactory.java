package br.edu.unisinos.game.strategy;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import br.edu.unisinos.game.model.ItemType;

@Component
public class DiscountStrategyFactory {

    private final Map<ItemType, DiscountStrategy> strategies;

    public DiscountStrategyFactory(
            List<DiscountStrategy> discountStrategies) {

        strategies = new EnumMap<>(ItemType.class);

        for (DiscountStrategy strategy : discountStrategies) {
            strategies.put(strategy.supportedType(), strategy);
        }
    }

    public DiscountStrategy forType(ItemType type) {

        DiscountStrategy strategy = strategies.get(type);

        if (strategy == null) {
            throw new IllegalArgumentException(
                    "Unsupported item type: " + type
            );
        }

        return strategy;
    }
}
