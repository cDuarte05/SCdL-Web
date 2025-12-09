package com.SCdl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.SCdl.model.Comparacao;
import com.SCdl.service.AnaliseService;

@RestController
@RequestMapping("/api/analise")
public class AnaliseController {

    @Autowired
    private AnaliseService service;

    @PostMapping(value="/enviar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Comparacao> enviar(
        @RequestParam("upload_proposta") MultipartFile proposta,
        @RequestParam("upload_licitacao") MultipartFile licitacao
    ) throws Exception {
        Comparacao c = service.comparar(proposta, licitacao);
        return ResponseEntity.ok(c);
    }
}
