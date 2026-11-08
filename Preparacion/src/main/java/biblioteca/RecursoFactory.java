package biblioteca;


public final class RecursoFactory {

    private RecursoFactory() { }

    public static Libro crearLibro(String codigo, String titulo, int anio, boolean prestable,
                                   String autor, String isbn) throws RecursoNoPrestadoException {
        return new Libro(codigo, titulo, anio, prestable, autor, isbn);
    }

    public static Revista crearRevista(String codigo, String titulo, int anio, boolean prestable,
                                       int edicion, String editorial) throws RecursoNoPrestadoException {
        return new Revista(codigo, titulo, anio, prestable, edicion, editorial);
    }

    public static RecursoDigital crearDigital(String codigo, String titulo, int anio, String url) {
        return new RecursoDigital(codigo, titulo, anio, url);
    }
}