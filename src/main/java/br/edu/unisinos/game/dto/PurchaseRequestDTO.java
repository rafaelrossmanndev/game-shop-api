package br.edu.unisinos.game.dto;

import java.util.UUID;

public record PurchaseRequestDTO(
		UUID playerId,
		UUID itemId
) {}
