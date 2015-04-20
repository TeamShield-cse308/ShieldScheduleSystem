/*

 */
package shield.server.exceptions;

/**
 * Indicates that the user tried to log in with the wrong password.
 * @author Jeffrey Kabot
 */
public class WrongPasswordException extends Exception
{

    /**
     * Creates a new instance of <code>WrongPasswordException</code> without
     * detail message.
     */
    public WrongPasswordException()
    {
        super();
    }

    /**
     * Constructs an instance of <code>WrongPasswordException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public WrongPasswordException(String msg)
    {
        super(msg);
    }
}
