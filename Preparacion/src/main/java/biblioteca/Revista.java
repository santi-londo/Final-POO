package biblioteca;

public class Revista extends RecursoFisico {
    private final int numeroEdicion;
    private final String editorial;

    public Revista(String codigo, String titulo, int anio, boolean prestable, int numeroEdicion, String editorial)
            throws RecursoNoPrestadoException {
        super(codigo, titulo, anio, prestable);
        this.numeroEdicion = numeroEdicion;
        this.editorial = editorial;
    }

    public int getNumeroEdicion() { return numeroEdicion; }
    public String getEditorial() { return editorial; }

    @Override
    public String getTipo() { return "Revista"; }

    @Override
    public String toString() {
        return super.toString() + " | Edición: " + numeroEdicion + " | Editorial: " + editorial;
    }
}