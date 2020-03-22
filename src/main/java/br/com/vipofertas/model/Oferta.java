package br.com.vipofertas.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
public class Oferta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    @NotNull
    private Long ordem;

    @Column(nullable=false)
    @NotNull
    private Boolean ativo;

    @Column(nullable=false)
    @NotNull
    private Date dtcriacao;

    @Column(nullable=false)
    @NotNull
    private Date dtinicial;

    @Column(nullable=false)
    @NotNull
    private Date dtfinal;

    @Column(nullable=false)
    @NotNull
    private Double preco;

    @Column(nullable=false)
    @NotNull
    private Double precopromo;

    @Column(length=20, nullable=false)
    @NotNull
    private String nome;

    @Column(length=20, nullable=false)
    @NotNull
    private String descricao;

    @Column(length=20, nullable=false)
    @NotNull
    private String unidade;

    private String imagem;

    @Transient
    private Boolean isNotificar;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    Usuario usuario;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parceiro_id", referencedColumnName = "id")
    Parceiro parceiro;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "oferta", cascade = CascadeType.REMOVE)
    private Set<Notificacao> notificacoes;
}