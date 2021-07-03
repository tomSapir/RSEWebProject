package enginePackage;

import javax.jws.soap.SOAPBinding;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Users
{
    /******************************************************************************/
    private Map<String, User> users;
    /******************************************************************************/
    public Users() { users = new HashMap<>(); }
    /******************************************************************************/
    public Collection<User> getUsers() { return this.users.values(); }
    /******************************************************************************/
    public void clearData() { users.clear(); }
    /******************************************************************************/
}
