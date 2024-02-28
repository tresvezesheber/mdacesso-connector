package br.dev.hebio.mdacessoconnector.model.colaborador;

import br.dev.hebio.mdacessoconnector.service.ColaboradorService;
import br.dev.hebio.mdacessoconnector.util.AbreviaNome;

import java.time.LocalDateTime;

public record CartaoColaborador(
        String codigo,
        String nome,
        String mensagem,
        String via,
        String senha,
        boolean jornadaUnica,
        String jornada,
        String acesso,
        String numCartao,
        String numRG,
        boolean visitante,
        LocalDateTime dataInicioValidade,
        LocalDateTime dataFimValidade
) {
    public CartaoColaborador(Colaborador colaborador) {
        this(
                colaborador.getMatricula().replaceFirst("^0+", ""),
                AbreviaNome.abreviarNomeETransformaEmMaiusculo(colaborador.getNome()),
                "0",
                "1",
                colaborador.getCpf().substring(0, 6),
                true,
                "010",
                ColaboradorService.defineAcesso(colaborador.getSituacao()),
                String.format("%1$10s", colaborador.getMatricula()).replace(' ', '0'),
                colaborador.getCpf(),
                false,
                colaborador.getDataCriacao(),
                colaborador.getDataCriacao().plusYears(10)
        );
    }
}
