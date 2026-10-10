package br.edu.unisinos.game.dto;

public record ItemRequestDTO(
	String name,
	String description,
	double price,
	int quantity
) {}
