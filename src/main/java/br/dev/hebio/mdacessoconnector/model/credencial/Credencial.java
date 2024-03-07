package br.dev.hebio.mdacessoconnector.model.credencial;

import br.dev.hebio.mdacessoconnector.model.colaborador.Colaborador;

public record Credencial(
        Byte cd_tipo_credencial,
        Integer cd_estrutura_organizacional,
        Byte tp_tecnologia,
        Long nu_credencial,
        Byte fl_bloqueada,
        Byte fl_credencial_publica
) {
    public Credencial(Colaborador colaborador) {
        this(
                (byte) 1,
                1,
                (byte) 1,
                Long.parseLong(colaborador.getMatricula()),
                (byte) 0,
                (byte) 0
        );
    }
}