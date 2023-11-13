package org.LondriBus.Model;

import java.util.List;

public class Notificador<LinhasDeOnibus, NoticiasRelevantes> {
    private List<LinhasDeOnibus> statusLinhas;
    private List<NoticiasRelevantes> noticias;

    public Notificador() {}

    public List<NoticiasRelevantes> filtrarNoticias(NoticiasRelevantes filtro) {
        System.out.println("Sistema de notícias relevantes no transporte público em Londrina\n");
        return noticias;
    }

    public List<LinhasDeOnibus> filtrarAnomalias(LinhasDeOnibus filtro) {
        System.out.println("Sistema de anomalias no transporte público em  Londrina\n");
        return statusLinhas;
    }
}
