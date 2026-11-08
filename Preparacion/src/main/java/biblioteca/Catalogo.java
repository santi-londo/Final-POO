package biblioteca;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class Catalogo<T extends Recurso> {

    private final Map<String, T> recursos = new LinkedHashMap<>();

    /** O(1). */
    public void agregar(T recurso) throws RecursoDuplicadoException {
        if (recurso == null) {
            throw new IllegalArgumentException("El recurso no puede ser nulo.");
        }
        if (recursos.containsKey(recurso.getCodigo())) {
            throw new RecursoDuplicadoException(recurso.getCodigo());
        }
        recursos.put(recurso.getCodigo(), recurso);
    }

    /** O(1). Devuelve true si existía y fue eliminado. */
    public boolean eliminar(String codigo) {
        return recursos.remove(codigo) != null;
    }

    /** O(1). */
    public T buscarPorCodigo(String codigo) throws RecursoNoEncontradoException {
        T recurso = recursos.get(codigo);
        if (recurso == null) {
            throw new RecursoNoEncontradoException(codigo);
        }
        return recurso;
    }

    /** O(n): recorre todos los recursos buscando el fragmento sin distinguir mayúsculas. */
    public List<T> buscarPorTitulo(String fragmento) {
        String buscado = fragmento.toLowerCase();
        return recursos.values().stream()
                .filter(r -> r.getTitulo().toLowerCase().contains(buscado))
                .collect(Collectors.toList());
    }

    /** O(n): solo recursos prestables y que no estén prestados. */
    public List<T> listarDisponibles() {
        return recursos.values().stream()
                .filter(Recurso::estaDisponibleParaPrestamo)
                .collect(Collectors.toList());
    }

    // ---------- Consultas con streams y lambdas ----------

    /** Títulos de todos los libros disponibles, en mayúsculas. */
    public List<String> titulosLibrosDisponiblesEnMayusculas() {
        return recursos.values().stream()
                .filter(r -> r instanceof Libro)
                .filter(r -> r.getEstado() == EstadoDisponibilidad.DISPONIBLE)
                .map(r -> r.getTitulo().toUpperCase())
                .collect(Collectors.toList());
    }

    /** Año descendente y luego título. O(n log n). No altera el orden interno del catálogo. */
    public List<T> ordenarPorAnioYTitulo() {
        return recursos.values().stream()
                .sorted(Comparadores.POR_ANIO_DESC_Y_TITULO)
                .collect(Collectors.toList());
    }

    /** Cuenta los recursos de un tipo dado (Libro.class, Revista.class...) que están prestados. */
    public long contarPrestadosPorTipo(Class<? extends Recurso> tipo) {
        return recursos.values().stream()
                .filter(tipo::isInstance)
                .filter(r -> r.getEstado() == EstadoDisponibilidad.PRESTADO)
                .count();
    }

    // ---------- Acceso de solo lectura ----------

    /** Copia inmutable: protege el estado interno (encapsulamiento). */
    public List<T> obtenerTodos() {
        return Collections.unmodifiableList(new ArrayList<>(recursos.values()));
    }

    public int tamanio() {
        return recursos.size();
    }
}
