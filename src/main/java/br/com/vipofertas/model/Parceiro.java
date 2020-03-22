package br.com.vipofertas.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Getter
@Setter
public class Parceiro {

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
    @NotEmpty
    private String nome;

    @Column(nullable=false)
    @NotEmpty
    private String ramo;

    private String imagem;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parceiro", cascade = CascadeType.ALL)
    private Set<EnderecoParceiro> enderecosParceiro;
}