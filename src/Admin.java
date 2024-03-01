import com.sun.security.jgss.GSSUtil;

import java.io.IOException;

public class Admin extends User {
    public Admin (String password) {
        super("admin", password);
    }
}
