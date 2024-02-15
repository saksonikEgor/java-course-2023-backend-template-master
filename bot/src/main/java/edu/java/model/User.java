package edu.java.model;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
public class User {
    private final long userId;
    @Setter private UserState state = UserState.UNAUTHENTICATED;
    private final List<Link> links = new ArrayList<>();

    public User(long userId) {
        this.userId = userId;
    }

    public void addLink(Link link) {
        links.add(link);
    }

    public void removeLink(Link link) {
        links.remove(link);
    }
}
