package biblioteca;

public class RecursoDuplicadoException extends BibliotecaException {
    public RecursoDuplicadoException(String codigo) {
        super("Ya existe un recurso con código '" + codigo + "' en el catálogo.");
    }
}