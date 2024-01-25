package com.senfiles.version1.Model;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Logs")
public class Logs implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String ip;
    @Column(name = "Date", unique = true)
    private String date;

    public Logs() {
    }

    public Logs(String ip, String date) {
        this.ip = ip;
        this.date = date;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getCreatedAt() {
        return date;
    }

    public void setCreatedAt(String createdAt) {
        this.date = createdAt;
    }

}