package br.com.alura.adopet.api.dto.pet;

import br.com.alura.adopet.api.dto.abrigo.DadosAbrigoDto;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.TipoPet;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CadastroPetDto(
        DadosAbrigoDto dadosAbrigoDto,
        @Enumerated(EnumType.STRING)
        @NotNull
        TipoPet tipo,
        @NotBlank
        String nome,
        @NotBlank
        String raca,
        @NotNull
        Integer idade,
        @NotBlank
        String cor,
        @NotNull
        Float peso,
        Abrigo abrigo
) {
}
