package br.edu.unisinos.game.strategy;

import java.math.BigDecimal;
import br.edu.unisinos.game.model.ItemType;

public interface DiscountStrategy {

    ItemType supportedType();

    BigDecimal discountRate();

    default BigDecimal calculateDiscount(
            BigDecimal price) {

        return price.multiply(discountRate());
    }

}
