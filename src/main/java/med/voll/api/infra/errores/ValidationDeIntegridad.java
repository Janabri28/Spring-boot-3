package med.voll.api.infra.errores;

public class ValidationDeIntegridad extends RuntimeException {
    public ValidationDeIntegridad(String s) {  //se debe marcar una excepción con mensaje de error.
     super(s);

    }
}
