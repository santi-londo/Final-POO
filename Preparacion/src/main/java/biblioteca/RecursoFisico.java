package biblioteca;

/**
 * Nivel intermedio de la jerarquía: lo que comparten Libro y Revista (prestar/devolver).
 * Evita duplicar la lógica de préstamo en ambas clases (DRY).
 */
public abstract class RecursoFisico extends Recurso implements Prestable {
    public static final int ANIO_MINIMO_PRESTABLE = 1923;

    private final boolean prestable;
    private Usuario usuarioPrestamo;

    protected RecursoFisico(String codigo, String titulo, int anio, boolean prestable)
            throws RecursoNoPrestadoException {
        super(codigo, titulo, anio);
        // Regla de negocio: nada anterior a 1923 puede registrarse como prestable.
        if (prestable && anio < ANIO_MINIMO_PRESTABLE) {
            throw new RecursoNoPrestadoException("El recurso '" + codigo + "' fue publicado en " + anio
                    + " (antes de " + ANIO_MINIMO_PRESTABLE + ") y no puede registrarse como prestable.");
        }
        this.prestable = prestable;
    }

    public boolean isPrestable() { return prestable; }

    @Override
    public Usuario getUsuarioPrestamo() { return usuarioPrestamo; }

    @Override
    public void prestar(Usuario usuario) throws RecursoNoPrestadoException, RecursoYaPrestadoException {
        if (usuario == null) {
            throw new IllegalArgumentException("El usuario es obligatorio para prestar.");
        }
        if (!prestable) {
            throw new RecursoNoPrestadoException("El recurso '" + getCodigo() + "' es solo de consulta en sala.");
        }
        if (getEstado() == EstadoDisponibilidad.PRESTADO) {
            throw new RecursoYaPrestadoException(getCodigo(), usuarioPrestamo.getNombre());
        }
        this.usuarioPrestamo = usuario;
        setEstado(EstadoDisponibilidad.PRESTADO);
    }

    @Override
    public void devolver() throws RecursoNoPrestadoException {
        if (getEstado() != EstadoDisponibilidad.PRESTADO) {
            throw new RecursoNoPrestadoException(getCodigo());
        }
        this.usuarioPrestamo = null;
        setEstado(EstadoDisponibilidad.DISPONIBLE);
    }

    @Override
    public boolean estaDisponibleParaPrestamo() {
        return prestable && getEstado() == EstadoDisponibilidad.DISPONIBLE;
    }
}