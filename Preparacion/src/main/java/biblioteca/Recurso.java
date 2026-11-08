package biblioteca;

import java.util.Objects;

/**
 * Clase abstracta: un "recurso" genérico nunca se instancia, solo sus tipos concretos.
 * Concentra los atributos comunes (abstracción + herencia).
 */
public abstract class Recurso {
    private static int totalCreados = 0; // static: cuenta cuántos recursos se han creado

    private final String codigo;
    private final String titulo;
    private final int anioPublicacion;
    private EstadoDisponibilidad estado;

    protected Recurso(String codigo, String titulo, int anioPublicacion) {
        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException("El código es obligatorio.");
        }
        if (titulo == null || titulo.isBlank()) {
            throw new IllegalArgumentException("El título es obligatorio.");
        }
        this.codigo = codigo;
        this.titulo = titulo;
        this.anioPublicacion = anioPublicacion;
        this.estado = EstadoDisponibilidad.DISPONIBLE;
        totalCreados++;
    }

    public String getCodigo() { return codigo; }
    public String getTitulo() { return titulo; }
    public int getAnioPublicacion() { return anioPublicacion; }
    public EstadoDisponibilidad getEstado() { return estado; }

    // protected: solo las subclases cambian el estado (encapsulamiento).
    protected void setEstado(EstadoDisponibilidad estado) {
        this.estado = estado;
    }

    public static int getTotalCreados() { return totalCreados; }

    /** Cada tipo se describe a sí mismo (polimorfismo). */
    public abstract String getTipo();

    /** Por defecto un recurso no se presta; los físicos prestables lo sobrescriben. */
    public boolean estaDisponibleParaPrestamo() {
        return false;
    }

    // equals/hashCode solo por código: es el identificador único del catálogo.
    // El estado se excluye porque es mutable y rompería colecciones basadas en hash.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Recurso)) return false;
        return codigo.equals(((Recurso) o).codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    // toString: representación legible para consola; las subclases la extienden.
    @Override
    public String toString() {
        return String.format("[%s] %s | \"%s\" (%d) | %s",
                getTipo(), codigo, titulo, anioPublicacion, estado.getDescripcion());
    }
}