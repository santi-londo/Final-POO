package biblioteca;

public class RecursoNoEncontradoException extends BibliotecaException {
    public RecursoNoEncontradoException(String codigo) {
        super("No existe ningún recurso con código '" + codigo + "' en el catálogo.");
    }
}