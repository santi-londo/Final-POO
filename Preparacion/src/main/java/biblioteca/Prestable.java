package biblioteca;


public interface Prestable {
    void prestar(Usuario usuario) throws RecursoNoPrestadoException, RecursoYaPrestadoException;

    void devolver() throws RecursoNoPrestadoException;

    Usuario getUsuarioPrestamo();

    String getCodigo();
}
