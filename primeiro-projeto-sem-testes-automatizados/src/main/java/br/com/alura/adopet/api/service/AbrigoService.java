package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.abrigo.CadastroAbrigoDto;
import br.com.alura.adopet.api.dto.abrigo.DadosAbrigoDto;
import br.com.alura.adopet.api.dto.pet.CadastroPetDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import br.com.alura.adopet.api.validacoes.abrigo.ValidacaoCadastroAbrigo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AbrigoService {

    @Autowired
    private AbrigoRepository abrigoRepository;

    @Autowired
    private List<ValidacaoCadastroAbrigo> validacoesCadastrosAbrigos;

    public List<Abrigo> listarAbrigos() {
        return abrigoRepository.findAll();
    }

    public void cadastrar(CadastroAbrigoDto dto) {
        validacoesCadastrosAbrigos.forEach(v -> v.validar(dto));

        abrigoRepository.save(new Abrigo(dto));
    }

    public List<Pet> listarPets(DadosAbrigoDto dto) {
        Abrigo abrigo = null;
        if (dto.idAbrigo() != null) {
            abrigo = abrigoRepository.getReferenceById(dto.idAbrigo());
        } else if (dto.nome() != null && !dto.nome().isEmpty()) {
            abrigo = abrigoRepository.findByNome(dto.nome());
        }

        if (abrigo == null) {
            throw new ValidacaoException("Nao ha abrigo cadastrado com este id ou nome");
        }

        return abrigo.getPets();
    }

    public void cadastrarPet(CadastroPetDto dto) {
        Abrigo abrigo = null;

        if (dto.dadosAbrigoDto().idAbrigo() != null)
        {
            abrigo = abrigoRepository.getReferenceById(dto.dadosAbrigoDto().idAbrigo());
        }else if (dto.dadosAbrigoDto().nome() != null && !dto.dadosAbrigoDto().nome().isEmpty()) {
            abrigo = abrigoRepository.findByNome(dto.dadosAbrigoDto().nome());
        }

        if (abrigo == null) {
            throw new ValidacaoException("Nao ha abrigo cadastrado com este id ou nome");
        }

        Pet pet = new Pet(dto);
        abrigoRepository.save(abrigo);
    }

}
