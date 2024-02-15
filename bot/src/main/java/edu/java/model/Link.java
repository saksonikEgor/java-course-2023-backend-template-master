package edu.java.model;

import lombok.Getter;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Getter
public class Link {
    private final URI uri;
    private final List<User> users = new ArrayList<>();

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
