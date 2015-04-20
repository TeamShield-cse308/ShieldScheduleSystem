/*

 */
package shield.server.exceptions;

/**
 * An exception that indicates that a student account is already approved.
 * @author Jeffrey Kabot
 */
public class AccountApprovedException extends Exception
{

    /**
     * Creates a new instance of <code>AccountApprovedException</code> without
     * detail message.
     */
    public AccountApprovedException()
    {
        super();
    }

    /**
     * Constructs an instance of <code>AccountApprovedException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public AccountApprovedException(String msg)
    {
        super(msg);
    }
}
