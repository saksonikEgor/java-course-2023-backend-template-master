package edu.java.repository;

import edu.java.exception.LinkIsAlreadyTrackedException;
import edu.java.exception.LinkIsNotTrackingException;
import edu.java.exception.UserIsUnauthenticatedException;
import edu.java.exception.WrongLinkFormatException;
import edu.java.model.Link;
import edu.java.model.User;
import edu.java.model.UserState;
import edu.java.util.URIUtils;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class UserDAO {
    private final Map<Long, User> userById = new HashMap<>();
    private final Map<URI, Link> linkByURI = new HashMap<>();

    public void addUser(long userId) {
        if (userById.containsKey(userId)) {
            refuseWaiting(userId);
            return;
        }
        userById.put(userId, new User(userId));
    }

    private void refuseWaiting(long userId) {
        userById.get(userId).setState(UserState.AUTHENTICATED);
    }

    public void refuseWaitingIfAuthenticated(long userId) {
        if (userById.get(userId) != null) {
            refuseWaiting(userId);
        }
    }

    public List<URI> getAllURIOfUser(long userId) throws UserIsUnauthenticatedException {
        User user = userById.get(userId);
        checkForAuthenticate(user, "Cant get all URIs of user because user is unauthenticated");

        return user.getLinks()
            .stream()
            .map(Link::getUri)
            .toList();
    }

    public void makeTheUserWaitForTrack(long userId) throws UserIsUnauthenticatedException {
        User user = userById.get(userId);
        checkForAuthenticate(user, "Cant make the user wait for track because user is unauthenticated");

        user.setState(UserState.WAITING_FOR_TRACK);
    }

    public void makeTheUserWaitForUntrack(long userId) throws UserIsUnauthenticatedException {
        User user = userById.get(userId);
        checkForAuthenticate(user, "Cant make the user wait for untrack because user is unauthenticated");

        user.setState(UserState.WAITING_FOR_UNTRACK);
    }

    public boolean isUserWaiting(long userId) {
        User user = userById.get(userId);

        if (user == null) {
            return false;
        }

        return user.getState() == UserState.WAITING_FOR_TRACK || user.getState() == UserState.WAITING_FOR_UNTRACK;
    }

    private void checkForAuthenticate(User user, String exceptionMessage) throws UserIsUnauthenticatedException {
        if (user == null) {
            throw new UserIsUnauthenticatedException(exceptionMessage);
        }
    }

    public void handleURIForUser(long userId, String stringURI) throws WrongLinkFormatException,
        LinkIsAlreadyTrackedException, LinkIsNotTrackingException {
        URI uri = URIUtils.castStringToURI(stringURI);

        if (uri == null) {
            throw new WrongLinkFormatException("Cant cast string '" + stringURI + "' to URI");
        }

        User user = userById.get(userId);
        Link link = linkByURI.get(uri);

        if (user.getState() == UserState.WAITING_FOR_TRACK) {
            if (link == null) {
                link = new Link(uri);
            }
            linkByURI.put(uri, link);

            user.addLink(link);
            link.addUser(user);
        } else {
            if (link == null) {
                throw new LinkIsNotTrackingException(
                    "User with id '" + userId + "' is not tracking uri '" + stringURI + "'"
                );
            }
            user.removeLink(link);
            link.removeUser(user);
        }
        refuseWaiting(userId);
    }

    public UserState getStateOfUser(long userId) {
        return userById.get(userId).getState();
    }
}
