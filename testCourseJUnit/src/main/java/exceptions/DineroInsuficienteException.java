package exceptions;

public class DineroInsuficienteException extends RuntimeException{
	
    /**
	 * Excepcion cuando hay dineroonsufiiiente
	 */
	private static final long serialVersionUID = 1L;

	public DineroInsuficienteException(String message) {
        super(message);
    }

}
