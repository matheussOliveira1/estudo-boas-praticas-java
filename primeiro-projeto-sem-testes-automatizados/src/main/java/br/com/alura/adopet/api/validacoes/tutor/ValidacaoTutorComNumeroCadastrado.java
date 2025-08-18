package br.com.alura.adopet.api.validacoes.tutor;

import br.com.alura.adopet.api.dto.tutor.ValidacaoTutorDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidacaoTutorComNumeroCadastrado implements ValidacaoCadastroTutor{

    @Autowired
    private TutorRepository tutorRepository;

    @Override
    public void validar(ValidacaoTutorDto tutorDto) {
        if (tutorRepository.existsByTelefone(tutorDto.telefone())) {
            throw new ValidacaoException("Tutor com telefone ja cadastrado!");
        }
    }
}
