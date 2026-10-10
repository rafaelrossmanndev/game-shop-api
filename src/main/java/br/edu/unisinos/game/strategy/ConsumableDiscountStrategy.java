package br.edu.unisinos.game.strategy;

import java.math.BigDecimal;
import org.springframework.stereotype.Component;
import br.edu.unisinos.game.model.ItemType;

@Component
public class ConsumableDiscountStrategy
        implements DiscountStrategy {

    @Override
    public ItemType supportedType() {
        return ItemType.CONSUMABLE;
    }

    @Override
    public BigDecimal discountRate() {
        return BigDecimal.ZERO;
    }
}
