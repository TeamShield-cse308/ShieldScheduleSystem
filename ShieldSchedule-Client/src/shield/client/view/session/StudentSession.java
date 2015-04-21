/*

 */
package shield.client.view.session;

import shield.shared.dto.SimpleStudent;

/**
 * A session manager for student users.
 * @author Jeffrey Kabot
 */
public class StudentSession implements Session
{
    private SimpleStudent studentAccount;

    public StudentSession(SimpleStudent acct)
    {
        studentAccount = acct;
    }
    
    private SimpleStudent getStudentAccount()
    {
        return studentAccount;
    }
    
}
