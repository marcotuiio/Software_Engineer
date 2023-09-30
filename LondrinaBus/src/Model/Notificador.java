package Model;

import java.util.List;

public class Notificador<LinhasDeOnibus, NoticiasRelevantes> {
    private List<LinhasDeOnibus> statusLinhas;
    private List<NoticiasRelevantes> noticias;

    public Notificador(List<LinhasDeOnibus> statusLinhas, List<NoticiasRelevantes> noticias) {
        this.statusLinhas = statusLinhas;
        this.noticias = noticias;
    }

    public List<NoticiasRelevantes> filtrarNoticias(NoticiasRelevantes filtro) {
        
        
        return noticias;
    }

    public List<LinhasDeOnibus> filtrarAnomalias(LinhasDeOnibus filtro) {
        
        
        return statusLinhas;
    }
}
