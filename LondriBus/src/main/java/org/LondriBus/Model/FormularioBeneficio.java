package org.LondriBus.Model;


import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.util.ArrayList;

public class FormularioBeneficio {
    private ArrayList<MultipartFile> documentos = new ArrayList<>();
    private int numeroUnico;

    public void setDocumento(MultipartFile doc) {
        this.documentos.add(doc);
    }
    public ArrayList<MultipartFile> getDocumentos() {return this.documentos; }
    public int getNumeroUnico() { return this.numeroUnico; }
    public void setNumeroUnico(int n) { this.numeroUnico = n; }
}
