package br.dev.hebio.mdacessoconnector.model.colaborador;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Colaborador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String matricula;

    private String nome;

    private String cpf;

    private char situacao;

    private LocalDateTime dataAdmissao;

    private LocalDateTime dataDemissao;

    @Enumerated(EnumType.STRING)
    private SyncStatus syncStatus;

    @CreationTimestamp
    private LocalDateTime dataCriacao;

    @UpdateTimestamp
    private LocalDateTime dataUltimaAtualizacao;

    private String hash;

    public Colaborador(ColaboradorDadosView colaboradorDadosView) {
        this.matricula = colaboradorDadosView.matricula();
        this.nome = colaboradorDadosView.nome();
        this.cpf = colaboradorDadosView.cpf();
        this.situacao = colaboradorDadosView.situacao();
        this.dataAdmissao = colaboradorDadosView.dataAdmissao();
        this.dataDemissao = colaboradorDadosView.dataDemissao();
    }
}
