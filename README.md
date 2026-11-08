# Final-POO
# Biblioteca

Sistema de catalogo y prestamos de la Biblioteca Central Hernando Gomez. Java 17 y Maven.

Decisiones de diseno:
- Recurso es abstracta. RecursoFisico (abstracta) agrupa la logica de prestar y devolver para Libro y Revista.
- RecursoDigital extiende Recurso y no implementa Prestable, por eso no se puede prestar.
- La regla de 1923 se valida en el constructor de RecursoFisico.
- Excepciones propias heredan de BibliotecaException: ya prestado, no prestable, no prestado y no encontrado.
- equals y hashCode de Recurso usan solo el codigo, porque es unico y no cambia.
- Catalogo es generico (T extends Recurso) y usa un LinkedHashMap por codigo, con busqueda O(1).
- Las consultas usan streams y lambdas. Los ordenamientos estan en Comparadores (Strategy).
- Patrones: Factory (RecursoFactory) y Singleton (RegistroPrestamos).
- ServicioPrestamos tiene una sola responsabilidad: prestar, devolver y registrar el historial.
- Estado es un enum para evitar textos sueltos.
