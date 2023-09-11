import java.awt.*;
import java.util.ArrayList;

public class FormularioBeneficio {
    private ArrayList<Image> documentos;
    private int numeroUnico;

    public void setDocumento(Image doc) {
        this.documentos.add(doc);
    }

    public ArrayList<Image> getDocumentos() {return this.documentos; }
    public int getNumeroUnico() { return this.numeroUnico; }
    public void setNumeroUnico(int n) { this.numeroUnico = n; }
}
