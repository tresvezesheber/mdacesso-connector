package br.dev.hebio.mdacessoconnector.schedules;

import br.dev.hebio.mdacessoconnector.model.colaborador.Colaborador;
import br.dev.hebio.mdacessoconnector.model.colaborador.ColaboradorDadosView;
import br.dev.hebio.mdacessoconnector.model.colaborador.SyncStatus;
import br.dev.hebio.mdacessoconnector.model.credencial.Credencial;
import br.dev.hebio.mdacessoconnector.model.pessoa.Pessoa;
import br.dev.hebio.mdacessoconnector.repository.ColaboradorRepository;
import br.dev.hebio.mdacessoconnector.service.ColaboradorService;
import br.dev.hebio.mdacessoconnector.service.SyncService;
import br.dev.hebio.mdacessoconnector.service.ViewService;
import br.dev.hebio.mdacessoconnector.util.DatabaseConnection;
import br.dev.hebio.mdacessoconnector.util.HashUtil;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ScheduledTasks {

    @Autowired
    private ViewService viewService;

    @Autowired
    private DatabaseConnection databaseConnection;

    @Autowired
    private ColaboradorService colaboradorService;

    @Autowired
    private ColaboradorRepository colaboradorRepository;

    @PostConstruct
    public void importarColaboradoresNaPrimeiraExecucao() {
        long tempoInicio = System.nanoTime();
        if (colaboradorRepository.count() > 0) {
            return;
        }

        List<ColaboradorDadosView> colaboradoresView = viewService.listarColaboradoresAdmitidos();
        for (ColaboradorDadosView dadosView : colaboradoresView) {
            Colaborador colaborador = new Colaborador(dadosView);
            colaborador.setSyncStatus(SyncService.defineRegistrosASeremSincronizados(dadosView.situacao()));
            colaborador.setHash(HashUtil.calculateHash(dadosView));
            colaboradorRepository.save(colaborador);
        }
        enviarNovosColaboradoresParaMDAcesso();
        long tempoFim = System.nanoTime();
        long tempoTotal = (tempoFim - tempoInicio) / 1000000;
        System.out.println(LocalDateTime.now() + " - importarColaboradoresNaPrimeiraExecucao() Tempo total de execução: " + tempoTotal + "ms");
    }

    @Scheduled(fixedRate = 300000) // 300000ms = 5 minutos
    public void verificarEAtualizarDados() {
        long tempoInicio = System.nanoTime();
        List<ColaboradorDadosView> colaboradoresView = viewService.listarColaboradoresAdmitidos();
        for (ColaboradorDadosView dadosView : colaboradoresView) {
            colaboradorService.verificarEAtualizarDados(dadosView);
        }
        enviarNovosColaboradoresParaMDAcesso();
//        enviarCartoesAtualizadosParaMDAcesso();
        long tempoFim = System.nanoTime();
        long tempoTotal = (tempoFim - tempoInicio) / 1000000;
        System.out.println(LocalDateTime.now() + " - verificarEAtualizarDados() Tempo total de execução: " + tempoTotal + "ms");
    }

    public void enviarNovosColaboradoresParaMDAcesso() {
        colaboradorRepository.findAllBySyncStatusIs(SyncStatus.CRIAR).forEach(colaborador -> {
            Pessoa pessoa = new Pessoa(colaborador);
            Credencial credencial = new Credencial(colaborador);

            databaseConnection.insertPessoa(pessoa);
            databaseConnection.insertCredencial(credencial);
            databaseConnection.insertPessoaCredencialRelation(pessoa.nu_matricula());

            colaborador.setSyncStatus(SyncStatus.SINCRONIZADO);
            colaboradorRepository.save(colaborador);
        });
    }

    public void enviarCartoesAtualizadosParaMDAcesso() {
        colaboradorRepository.findAllBySyncStatusIs(SyncStatus.ATUALIZAR).forEach(colaborador -> {
            databaseConnection.updatePessoa(new Pessoa(colaborador));
            colaborador.setSyncStatus(SyncStatus.SINCRONIZADO);
            colaboradorRepository.save(colaborador);
        });
    }
}