/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sss.exceptions;

/**
 * An exception to indicate that an account is already active.
 * @author Jeffrey Kabot
 */
public class AccountActiveException extends Exception
{
    public AccountActiveException() {super();}
    public AccountActiveException(String msg) {super(msg);}
}
