package br.dev.hebio.mdacessoconnector.repository;

import br.dev.hebio.mdacessoconnector.model.colaborador.Colaborador;
import br.dev.hebio.mdacessoconnector.model.colaborador.SyncStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ColaboradorRepository extends JpaRepository<Colaborador, Long> {
    Colaborador findByMatricula(String matricula);

    List<Colaborador> findAllBySyncStatusIs(SyncStatus syncStatus);
}
