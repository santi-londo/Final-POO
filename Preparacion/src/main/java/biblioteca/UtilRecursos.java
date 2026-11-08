package biblioteca;

import java.util.Collection;

public final class UtilRecursos {

    private UtilRecursos() { }

    /** Wrapper Integer: acepta null y permite validar entradas opcionales. */
    public static boolean anioValido(Integer anio) {
        return anio != null && anio > 0 && anio <= java.time.Year.now().getValue();
    }

    /** Conversión String -> Integer (wrapper) con valor nulo si el texto no es un número. */
    public static Integer parsearAnio(String texto) {
        try {
            return Integer.valueOf(texto.trim());
        } catch (NumberFormatException | NullPointerException e) {
            return null;
        }
    }

    /** Wildcard "? extends": solo LEE de la colección, acepta List<Libro>, List<Recurso>, etc. */
    public static void imprimir(String titulo, Collection<? extends Recurso> recursos) {
        System.out.println("\n--- " + titulo + " (" + recursos.size() + ") ---");
        recursos.forEach(r -> System.out.println("  " + r));
    }

    /** Wildcard "? super": solo ESCRIBE libros, el destino puede ser Collection<Libro> o Collection<Recurso>. */
    public static void copiarLibros(Collection<? extends Recurso> origen, Collection<? super Libro> destino) {
        for (Recurso r : origen) {
            if (r instanceof Libro libro) {
                destino.add(libro);
            }
        }
    }

    /** Colección -> arreglo, para trabajar con Arrays (por ejemplo Arrays.sort). */
    public static Recurso[] aArreglo(Collection<? extends Recurso> recursos) {
        return recursos.toArray(new Recurso[0]);
    }
}