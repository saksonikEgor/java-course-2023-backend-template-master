package edu.java.model;

import edu.java.exception.LinkIsAlreadyTrackedException;
import edu.java.exception.LinkIsNotTrackingException;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
public class User {
    private final long userId;
    private final Set<Link> links = new HashSet<>();
    @Setter private UserState state = UserState.AUTHENTICATED;

    public User(long userId) {
        this.userId = userId;
    }

    public void addLink(Link link) throws LinkIsAlreadyTrackedException {
        if (!links.add(link)) {
            throw new LinkIsAlreadyTrackedException(
                "User with id '" + userId + "' is already tracking uri '" + link.getUri() + "'"
            );
        }
    }

    public void removeLink(Link link) throws LinkIsNotTrackingException {
        if (!links.remove(link)) {
            throw new LinkIsNotTrackingException(
                "User with id '" + userId + "' is not tracking uri '" + link.getUri() + "'"
            );
        }
    }
}
