package br.dev.hebio.mdacessoconnector.service;

import br.dev.hebio.mdacessoconnector.model.colaborador.Colaborador;
import br.dev.hebio.mdacessoconnector.model.colaborador.ColaboradorDadosView;
import br.dev.hebio.mdacessoconnector.model.colaborador.SyncStatus;
import br.dev.hebio.mdacessoconnector.repository.ColaboradorRepository;
import br.dev.hebio.mdacessoconnector.util.HashUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ColaboradorService {

    @Autowired
    private ColaboradorRepository colaboradorRepository;

    public void verificarEAtualizarDados(ColaboradorDadosView dadosView) {
        Colaborador colaboradorAtual = colaboradorRepository.findByMatricula(dadosView.matricula());
        String hash = HashUtil.calculateHash(dadosView);

        if (colaboradorAtual == null) {
            // O colaborador não existe no banco de dados local, então criamos um novo colaborador e o salvamos.
            Colaborador novoColaborador = new Colaborador(dadosView);
            novoColaborador.setSyncStatus(SyncService.defineRegistrosASeremSincronizados(dadosView.situacao()));
            novoColaborador.setHash(hash);
            colaboradorRepository.save(novoColaborador);
        } else if (!hash.equals(colaboradorAtual.getHash())) {
            // Os dados do colaborador foram alterados, então atualizamos o colaborador no banco de dados local.
            colaboradorAtual.setMatricula(dadosView.matricula());
            colaboradorAtual.setNome(dadosView.nome());
            colaboradorAtual.setCpf(dadosView.cpf());
            colaboradorAtual.setSituacao(dadosView.situacao());
            colaboradorAtual.setDataAdmissao(dadosView.dataAdmissao());
            colaboradorAtual.setDataDemissao(dadosView.dataDemissao());
            colaboradorAtual.setSyncStatus(SyncStatus.ATUALIZAR);
            colaboradorAtual.setHash(hash);
            colaboradorRepository.save(colaboradorAtual);
        }
    }

    public static Byte defineAcesso(char colaboradorSituacao) {
        /*A - ATIVO, C - CONTRATO DE TRABALHO SUSPENSO, D - DEMITIDO, E - LICENÇA MATER., F - FÉRIAS, G - RECESSO REMUNERADO DE ESTAGIO, I - APOS. POR INCAPACIDADE PERMANENTE,
        K - CESSÃO/REQUISIÇÃO, L - LICENÇA S/VENC, M - SERV. MILITAR, N - MANDATO SINDICAL ÔNUS DO EMPREGADOR, O - DOENÇA OCUPACIONAL, P - AF. PREVIDÊNCIA, Q - PRISÃO/CÁRCERE,
                R - LICENÇA REMUN., S - MANDATO SINDICAL ÔNUS SINDICATO, T - AF. AC. TRABALHO, U - OUTROS, V - AVISO PRÉVIO, W - LICENÇA MATER. COMPL. 180 DIAS, X - C/DEM. NO MÊS,
                Y - LICENÇA PATERNIDADE, Z - ADMISSÃO PROX. MÊS*/

        if (!(colaboradorSituacao == 'A'
                || colaboradorSituacao == 'E'
                || colaboradorSituacao == 'F'
                || colaboradorSituacao == 'L'
                || colaboradorSituacao == 'R'
                || colaboradorSituacao == 'V'
                || colaboradorSituacao == 'W'
                || colaboradorSituacao == 'Y')) {
            return 11;
        }
        return 10;
    }
}