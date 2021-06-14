package com.example.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.Properties;


@Service
public class MailService {
    @Autowired
    private JavaMailSender javaMailSender;

    public SimpleMailMessage sendEmailConsulta(String mail,String tipo, String utente, String hora, String dia) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(mail);
        msg.setSubject(tipo + " Agendada!");
        msg.setText("A sua " + tipo + " para o utente " + utente + " foi agendada para as " + hora + " do dia " + dia + "!\n" +
                        "Por favor compareça no local à hora marcada!\nAo seu dispor,\nClinica Veterinária do Minho"
                    );
        return msg;
    }
    public SimpleMailMessage sendEmailregister(String mail, String senha, String nome) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(mail);
        msg.setSubject("Registo Concluido!");
        msg.setText("Bem Vindo à Clinica Veterinária do Minho " + nome + "!\nEstes são os seus dados de login. Por favor altere a sua senha" +
                " no primeiro acesso à aplicação. \nEmail: " + mail + ";\nSenha: " + senha + ";\nAo seu dispor,\nClinica Veterinária do Minho"
        );

        return msg;
    }

    public SimpleMailMessage sendEmailCancelada(String mail,String tipo, String utente, String hora, String dia) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(mail);
        msg.setSubject(tipo + " Cancelada!");
        msg.setText("A sua " + tipo + " para o utente " + utente + " para as " + hora + " do dia " + dia + " foi cancelada!\n" +
                "Lamentamos a indisponibilidade, por favor tente remarcar para outro dia!\nAo seu dispor,\nClinica Veterinária do Minho"
        );
        return msg;
    }
    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername("clinicaveterinariadominho@gmail.com");
        mailSender.setPassword("projetoEA");

        Properties props = mailSender.getJavaMailProperties();
        System.out.println(props);
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
        props.put("mail.ssl.enable", "true");
        props.put("mail.smtp.ssl.trust", "*");
        return mailSender;
    }
}