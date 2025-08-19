package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Adocao;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import br.com.alura.adopet.api.repository.PetRepository;
import br.com.alura.adopet.api.repository.TutorRepository;
import br.com.alura.adopet.api.validacoes.ValidacaoSolicitacaoAdocao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class AdocaoServiceTest {

    @InjectMocks
    private AdocaoService adocaoService;

    @Mock
    private AdocaoRepository adocaoRepository;

    @Mock
    private PetRepository petRepository;

    @Mock
    private TutorRepository tutorRepository;

    @Mock
    private EmailService emailService;

    @Spy
    private List<ValidacaoSolicitacaoAdocao> validacoes = new ArrayList<>();

    @Mock
    private ValidacaoSolicitacaoAdocao validador1;

    @Mock
    private ValidacaoSolicitacaoAdocao validador2;

    @Mock
    private Pet pet;

    @Mock
    private Tutor tutor;

    @Mock
    private Abrigo abrigo;

    private SolicitacaoAdocaoDto solicitacaoAdocaoDto;

    @Captor
    private ArgumentCaptor<Adocao> adocaoCaptor;

    @Test
    void deveriaSalvarAdocaoAoSolicitar() {

        // ARRANGE
        this.solicitacaoAdocaoDto = new SolicitacaoAdocaoDto(1l, 1l, "teste");
        BDDMockito.given(petRepository.getReferenceById(solicitacaoAdocaoDto.idPet())).willReturn(pet);
        BDDMockito.given(tutorRepository.getReferenceById(solicitacaoAdocaoDto.idTutor())).willReturn(tutor);
        BDDMockito.given(pet.getAbrigo()).willReturn(abrigo);

        // ACT
        adocaoService.solicitar(solicitacaoAdocaoDto);

        // ASSERT
        BDDMockito.then(adocaoRepository).should().save(adocaoCaptor.capture());
        Adocao adocao = adocaoCaptor.getValue();
        Assertions.assertEquals(pet, adocao.getPet());
        Assertions.assertEquals(tutor, adocao.getTutor());
        Assertions.assertEquals(solicitacaoAdocaoDto.motivo(), adocao.getMotivo());

    }

    @Test
    void deveriaChamarValidadoresDeAdocaoAoSolicitar() {

        // ARRANGE
        this.solicitacaoAdocaoDto = new SolicitacaoAdocaoDto(1l, 1l, "teste");

        BDDMockito.given(petRepository.getReferenceById(solicitacaoAdocaoDto.idPet())).willReturn(pet);
        BDDMockito.given(tutorRepository.getReferenceById(solicitacaoAdocaoDto.idTutor())).willReturn(tutor);
        BDDMockito.given(pet.getAbrigo()).willReturn(abrigo);

        validacoes.add(validador1);
        validacoes.add(validador2);

        // ACT
        adocaoService.solicitar(solicitacaoAdocaoDto);

        // ASSERT
        BDDMockito.then(validador1).should().validar(solicitacaoAdocaoDto);
        BDDMockito.then(validador2).should().validar(solicitacaoAdocaoDto);

    }


}