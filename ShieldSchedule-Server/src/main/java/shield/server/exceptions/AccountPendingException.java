/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shield.server.exceptions;

/**
 * An exception which indicates that an account is not yet approved
 * @author Jeffrey Kabot
 */
public class AccountPendingException extends Exception
{
    public AccountPendingException() {super();}
    public AccountPendingException(String msg) {super(msg);}
}
