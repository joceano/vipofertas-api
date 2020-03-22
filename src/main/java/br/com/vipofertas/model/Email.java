package br.com.vipofertas.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Email {
    private String remetente;
    private String destinatario;
    private String titulo;
    private String corpo;

    public Email(String destinatario, String titulo, String corpo) {
        this.destinatario = destinatario;
        this.titulo = titulo;
        this.corpo = corpo;
        this.remetente = "Portal Vip Ofertas <vipofertas.br@gmail.com>";
    }
}
