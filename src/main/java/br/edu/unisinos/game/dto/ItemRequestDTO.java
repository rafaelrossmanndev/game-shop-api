package br.edu.unisinos.game.dto;

import br.edu.unisinos.game.model.ItemType;

public record ItemRequestDTO(
	String name,
	String description,
	double price,
	int quantity,
	ItemType type
) {}
