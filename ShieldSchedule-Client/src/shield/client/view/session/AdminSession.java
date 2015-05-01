/*

 */
package shield.client.view.session;

import shield.shared.dto.SimpleSchool;

/**
 * A session manager for administrator users.
 * @author Jeffrey Kabot
 */
public class AdminSession implements Session
{
    private SimpleSchool schoolAdded;

    public SimpleSchool getSchoolAdded() {
        return schoolAdded;
    }

    public void setSchoolAdded(SimpleSchool schoolAdded) {
        this.schoolAdded = schoolAdded;
    }
    
}
