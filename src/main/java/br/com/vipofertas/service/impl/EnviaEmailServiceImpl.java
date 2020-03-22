package br.com.vipofertas.service.impl;

import br.com.vipofertas.model.Email;
import br.com.vipofertas.service.EnviaEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EnviaEmailServiceImpl implements EnviaEmailService{

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void enviar(Email email) throws Exception {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setSubject(email.getTitulo());
        message.setText(email.getCorpo());
        message.setTo(email.getDestinatario());
        message.setFrom(email.getRemetente());

        mailSender.send(message);
    }
}
