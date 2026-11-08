package biblioteca;

import java.util.Comparator;

public final class Comparadores {

    private Comparadores() { }

    // Año descendente y, como segundo criterio, título ignorando mayúsculas.
    public static final Comparator<Recurso> POR_ANIO_DESC_Y_TITULO =
            Comparator.comparingInt(Recurso::getAnioPublicacion).reversed()
                    .thenComparing(Recurso::getTitulo, String.CASE_INSENSITIVE_ORDER);

    public static final Comparator<Recurso> POR_TITULO =
            Comparator.comparing(Recurso::getTitulo, String.CASE_INSENSITIVE_ORDER);
}
