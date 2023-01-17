package com.shadow.entity;

import javax.persistence.*;

@Entity
@Table(name = "email", schema = "public")
public class Email {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "receiver")
    private String receiver;
    @Column(name = "subject")
    private String subject;
    @Column(name = "body")
    private String body;

    public Email() {
    }

    public Email(String receiver, String subject, String body) {
        this.receiver = receiver;
        this.subject = subject;
        this.body = body;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return String.format("Email{to=%s, body=%s}", receiver, body);
    }
}

