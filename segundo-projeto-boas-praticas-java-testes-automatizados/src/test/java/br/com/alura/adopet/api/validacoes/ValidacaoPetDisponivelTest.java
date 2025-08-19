package br.com.alura.adopet.api.validacoes;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.PetRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ValidacaoPetDisponivelTest {

    @InjectMocks
    private ValidacaoPetDisponivel validacao;

    @Mock
    private PetRepository petRepository;

    @Mock
    private Pet pet;

    @Mock
    private SolicitacaoAdocaoDto solicitacaoAdocaoDto;

    @Test
    void deveriaPermitirSolicitacaoDeAdocaoPet() {
        // Teste unitario para validar se o metodo nao lancou excecao

        // ARRANGE
        BDDMockito.given(petRepository.getReferenceById(solicitacaoAdocaoDto.idPet())).willReturn(pet);
        BDDMockito.given(pet.getAdotado()).willReturn(false);

        // ASSERT + ACT
        Assertions.assertDoesNotThrow(() -> validacao.validar(solicitacaoAdocaoDto));

    }

    @Test
    void naoDeveriaPermitirSolicitacaoDeAdocaoPet() {
        // Teste unitario para validar se o metodo lancou excecao quando o pet ja foi adotado

        // ARRANGE
        BDDMockito.given(petRepository.getReferenceById(solicitacaoAdocaoDto.idPet())).willReturn(pet);
        BDDMockito.given(pet.getAdotado()).willReturn(true);

        // ASSERT + ACT
        Assertions.assertThrows(ValidacaoException.class, () -> validacao.validar(solicitacaoAdocaoDto));

    }

}