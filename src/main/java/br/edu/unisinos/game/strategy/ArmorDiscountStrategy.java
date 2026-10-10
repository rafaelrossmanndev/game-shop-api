package br.edu.unisinos.game.strategy;

import java.math.BigDecimal;
import org.springframework.stereotype.Component;
import br.edu.unisinos.game.model.ItemType;

@Component
public class ArmorDiscountStrategy
        implements DiscountStrategy {

    @Override
    public ItemType supportedType() {
        return ItemType.ARMOR;
    }

    @Override
    public BigDecimal discountRate() {
        return new BigDecimal("0.05");
    }
}
