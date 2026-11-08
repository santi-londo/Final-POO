package biblioteca;

import java.time.LocalDate;

public class ServicioPrestamos {

    private final RegistroPrestamos registro = RegistroPrestamos.getInstancia();

    public void prestar(Prestable recurso, Usuario usuario)
            throws RecursoNoPrestadoException, RecursoYaPrestadoException {
        recurso.prestar(usuario);
        registro.registrar(new Prestamo(recurso.getCodigo(), usuario, LocalDate.now()));
    }

    public void devolver(Prestable recurso) throws RecursoNoPrestadoException {
        recurso.devolver();
    }
}
