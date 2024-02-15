package edu.java.model;

import lombok.Getter;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
public class Link {
    private final URI uri;
    private final Set<User> users = new HashSet<>();

    public Link(URI uri) {
        this.uri = uri;
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void removeUser(User user) {
        users.remove(user);
    }
}
