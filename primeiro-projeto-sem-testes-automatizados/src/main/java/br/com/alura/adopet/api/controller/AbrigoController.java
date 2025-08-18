package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.dto.abrigo.CadastroAbrigoDto;
import br.com.alura.adopet.api.dto.abrigo.DadosAbrigoDto;
import br.com.alura.adopet.api.dto.pet.CadastroPetDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import br.com.alura.adopet.api.service.AbrigoService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/abrigos")
public class AbrigoController {

    @Autowired
    private AbrigoService abrigoService;

    @GetMapping
    public ResponseEntity<List<Abrigo>> listar() {
        List<Abrigo> abrigos = abrigoService.listarAbrigos();
        if (abrigos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(abrigos);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<String> cadastrar(@RequestBody @Valid CadastroAbrigoDto dto) {
        try {
            abrigoService.cadastrar(dto);
            return ResponseEntity.ok("Abrigo cadastrado");
        } catch (ValidacaoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/pets")
    public ResponseEntity<?> listarPets(@PathVariable DadosAbrigoDto dto) {
        try {
            return ResponseEntity.ok(abrigoService.listarPets(dto));
        } catch (ValidacaoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/pets")
    @Transactional
    public ResponseEntity<String> cadastrarPet(@RequestBody @Valid CadastroPetDto dto) {
        try {
            abrigoService.cadastrarPet(dto);
            return ResponseEntity.ok("Pet cadastrado com sucesso.");
        } catch (ValidacaoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
