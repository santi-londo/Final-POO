package biblioteca;

public class Libro extends RecursoFisico {
    private final String autor;
    private final String isbn;

    public Libro(String codigo, String titulo, int anio, boolean prestable, String autor, String isbn)
            throws RecursoNoPrestadoException {
        super(codigo, titulo, anio, prestable);
        this.autor = autor;
        this.isbn = isbn;
    }

    public String getAutor() { return autor; }
    public String getIsbn() { return isbn; }

    @Override
    public String getTipo() { return "Libro"; }

    // No se sobrescribe equals/hashCode: dos ejemplares del mismo ISBN son recursos
    // distintos (distinto código), así que la igualdad heredada por código es la correcta.

    @Override
    public String toString() {
        return super.toString() + " | Autor: " + autor + " | ISBN: " + isbn;
    }
}
