package com.comentacomigo.comenta_comigo_backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String remetente;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void recuperaSenha(String destinatario, String link) {

        SimpleMailMessage mensagem = new SimpleMailMessage();

        mensagem.setFrom(remetente);
        mensagem.setTo(destinatario);
        mensagem.setSubject("Comenta Comigo - Recuperação de senha");
        mensagem.setText("Olá, Amiguinho! Recebemos um pedido seu para redefinir a sua senha. \n\n"
                + "Clique no link abaixo (ele vale por 30 minutos):\n"
                + link + "\n\n"
                + "Se não foi você que pediu, é só ignorar este e-mail. Se cuida!");

        mailSender.send(mensagem);

    }

}
