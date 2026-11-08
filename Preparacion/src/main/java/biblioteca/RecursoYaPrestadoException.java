package biblioteca;


public class RecursoYaPrestadoException extends BibliotecaException {
    public RecursoYaPrestadoException(String codigo, String nombreUsuario) {
        super("El recurso '" + codigo + "' ya se encuentra prestado a " + nombreUsuario + ".");
    }
}