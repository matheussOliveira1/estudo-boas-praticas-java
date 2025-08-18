package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.tutor.AtualizarDadosTutorDto;
import br.com.alura.adopet.api.dto.tutor.CadastroTutorDto;
import br.com.alura.adopet.api.dto.tutor.ValidacaoTutorDto;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.TutorRepository;
import br.com.alura.adopet.api.validacoes.tutor.ValidacaoCadastroTutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TutorService {

    @Autowired
    private TutorRepository tutorRepository;

    @Autowired
    private List<ValidacaoCadastroTutor> validacoesCadastrosTutores;

    public void cadastrar(CadastroTutorDto dto) {
        validacoesCadastrosTutores.forEach(
                v -> v.validar(new ValidacaoTutorDto(dto.email(), dto.telefone()))
        );

        tutorRepository.save(new Tutor(dto));
    }

    public void atualizar(AtualizarDadosTutorDto dto) {
        Tutor tutor = tutorRepository.getReferenceById(dto.idTutor());

        validacoesCadastrosTutores.forEach(
                v -> v.validar(new ValidacaoTutorDto(dto.email(), dto.telefone()))
        );

        tutor.atualizarDadosTutor(dto);
    }
}
