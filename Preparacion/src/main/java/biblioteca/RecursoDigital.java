package biblioteca;


public class RecursoDigital extends Recurso {
    private final String urlAcceso;

    public RecursoDigital(String codigo, String titulo, int anio, String urlAcceso) {
        super(codigo, titulo, anio);
        this.urlAcceso = urlAcceso;
    }

    public String getUrlAcceso() { return urlAcceso; }

    @Override
    public String getTipo() { return "Digital"; }

    @Override
    public String toString() {
        return super.toString() + " | URL: " + urlAcceso + " | Solo consulta en sala";
    }
}