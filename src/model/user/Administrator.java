package model.user;

import java.time.LocalDateTime;

/**
 * Moderator's extended class
 */
public class Administrator extends Moderator{

    public Administrator(int id, String userName, String password, String email, String firstName, String lastName, State state, LocalDateTime lastConnectionTime) {
        super(id, userName, password, email, firstName, lastName, state, lastConnectionTime);
        setPermission(Permission.ADMINISTRATOR);
    }
}
