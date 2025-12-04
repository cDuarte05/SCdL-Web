package com.SCdl.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Licitacao") // coloque o nome correto da tabela do banco
public class Licitacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "id_usuario_fk")
    private String idUsuarioFk;

    @Column(name = "nome_arquivo")
    private String nomeArquivo;

    @Lob
    @Column(name = "arquivo_pdf")
    private byte[] arquivoPdf;

    @Column(name = "data_envio")
    private LocalDateTime dataEnvio;

    // Getters e Setters
    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public String getIdUsuarioFk() {return idUsuarioFk;}
    public void setIdUsuarioFk(String idUsuarioFk) {this.idUsuarioFk = idUsuarioFk;}

    public String getNomeArquivo() {return nomeArquivo;}
    public void setNomeArquivo(String nomeArquivo) {this.nomeArquivo = nomeArquivo;}

    public byte[] getArquivoPdf() {return arquivoPdf;}
    public void setArquivoPdf(byte[] arquivoPdf) {this.arquivoPdf = arquivoPdf;}

    public LocalDateTime getDataEnvio() {return dataEnvio;}
    public void setDataEnvio(LocalDateTime dataEnvio) {this.dataEnvio = dataEnvio;}
}
