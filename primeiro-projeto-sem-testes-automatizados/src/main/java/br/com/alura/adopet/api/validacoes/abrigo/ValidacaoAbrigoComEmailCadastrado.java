package br.com.alura.adopet.api.validacoes.abrigo;

import br.com.alura.adopet.api.dto.abrigo.CadastroAbrigoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidacaoAbrigoComEmailCadastrado implements ValidacaoCadastroAbrigo {

    @Autowired
    private AbrigoRepository abrigoRepository;

    @Override
    public void validar(CadastroAbrigoDto dto) {
        if (abrigoRepository.existsByEmail(dto.email())) {
            throw new ValidacaoException("Abrigo com email ja cadastrado");
        }
    }
}
