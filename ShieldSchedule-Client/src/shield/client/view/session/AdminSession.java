/*

 */
package shield.client.view.session;

import java.util.List;
import shield.shared.dto.SimpleSchool;

/**
 * A session manager for administrator users.
 * @author Jeffrey Kabot
 */
public class AdminSession implements Session
{
    private SimpleSchool schoolAdded;
    
    private List<SimpleSchool> schools;

    public SimpleSchool getSchoolAdded() {
        return schoolAdded;
    }

    public void setSchoolAdded(SimpleSchool schoolAdded) {
        this.schoolAdded = schoolAdded;
    }

    public List<SimpleSchool> getSchools() {
        return schools;
    }

    public void setSchools(List<SimpleSchool> schools) {
        this.schools = schools;
    }
    
    
}
