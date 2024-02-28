package br.dev.hebio.mdacessoconnector.service;

import br.dev.hebio.mdacessoconnector.model.colaborador.SyncStatus;
import org.springframework.stereotype.Service;

@Service
public class SyncService {

    public static SyncStatus defineRegistrosASeremSincronizados(char colaboradorSituacao) {
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
            return SyncStatus.NAO_SINCRONIZAR;
        }
        return SyncStatus.CRIAR;
    }

}
