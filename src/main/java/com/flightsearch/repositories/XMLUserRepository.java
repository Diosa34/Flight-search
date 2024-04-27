package com.flightsearch.repositories;

import com.flightsearch.config.properties.RepositoryProperties;
import com.flightsearch.exceptions.NotFoundException;
import com.flightsearch.models.User;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.AnyTypePermission;
import org.springframework.stereotype.Repository;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Objects;

@Repository
public class XMLUserRepository {
    final private XStream xstream;
    final private Path xmlPath;

    public XMLUserRepository(RepositoryProperties repositoryProperties) {
        xmlPath = repositoryProperties.getUserXmlFilename();
        xstream = new XStream();
        xstream.addPermission(AnyTypePermission.ANY);
    }

    public ArrayList<User> getAll() {
        xstream.alias("user", User.class);
        try {
            if (!xmlPath.toFile().exists()) {
                Files.createDirectories(xmlPath.getParent());
                Files.writeString(xmlPath, "<list></list>");
            }
            return (ArrayList<User>) xstream.fromXML(xmlPath.toFile(), "list");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void save(User newUser){
        xstream.alias("user", User.class);
        ArrayList<User> users = getAll();
        users.add(newUser);
        saveAll(users);
    }

    public void saveAll(ArrayList<User> users){
        try {
            xstream.toXML(users, new FileWriter(xmlPath.toFile(), false));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public User getById(Long id) {
        return getAll().stream()
                .filter(elem -> Objects.equals(elem.getId(), id))
                .findFirst()
                .orElseThrow(() -> new NotFoundException(id, "User XML"));
    }

    public void delete(User user) {
        ArrayList<User> users = getAll();
        users.remove(user);
        saveAll(users);
    }
}
