package br.dev.hebio.mdacessoconnector.model.pessoa;

import br.dev.hebio.mdacessoconnector.model.colaborador.Colaborador;
import br.dev.hebio.mdacessoconnector.service.ColaboradorService;

public record Pessoa(
        Long cd_pessoa,
        Long nu_matricula,
        String nm_pessoa,
        Byte cd_situacao_pessoa,
        Integer cd_estrutura_organizacional,
        Integer cd_estrutura_org_empresa,
        String nu_cpf

) {

    public Pessoa(Colaborador colaborador) {
        this(
                Long.parseLong(colaborador.getMatricula()),
                Long.parseLong(colaborador.getMatricula()),
                colaborador.getNome().toUpperCase(),
                ColaboradorService.defineAcesso(colaborador.getSituacao()),
                2,
                1,
                colaborador.getCpf()
        );
    }
}
