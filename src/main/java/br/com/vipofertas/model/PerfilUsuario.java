package br.com.vipofertas.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class PerfilUsuario {
    String nome;
    String sobrenome;
    String email;
    Date nascimento;
    String senhaAtual;
    String novaSenha;
    String novaSenhaRep;
}
