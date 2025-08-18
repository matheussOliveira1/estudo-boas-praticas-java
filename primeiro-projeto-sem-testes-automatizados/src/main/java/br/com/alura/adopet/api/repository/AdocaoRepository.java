package br.com.alura.adopet.api.repository;

import br.com.alura.adopet.api.model.Adocao;
import br.com.alura.adopet.api.model.StatusAdocao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdocaoRepository extends JpaRepository<Adocao, Long> {

    Boolean existsByPetIdAndStatus(Long idPed, StatusAdocao statusAdocao);

    Boolean existsByTutorIdAndStatus(Long idTutor, StatusAdocao statusAdocao);

    Long countByTutorIdAndStatus(Long idTutor, StatusAdocao statusAdocao);

}
