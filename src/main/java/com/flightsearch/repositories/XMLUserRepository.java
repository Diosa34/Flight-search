package com.flightsearch.repositories;

import com.flightsearch.config.properties.RepositoryProperties;
import com.flightsearch.exceptions.NotFoundException;
import com.flightsearch.models.User;
import com.thoughtworks.xstream.XStream;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class XMLUserRepository {
    final private XStream xstream;
    final private Path xmlPath;

    public XMLUserRepository(RepositoryProperties repositoryProperties) {
        xmlPath = repositoryProperties.getUserXmlFilename();
        xstream = new XStream();
        xstream.allowTypes(new Class[] {User.class});
        xstream.alias("user", User.class);
    }

    public List<User> getAll() {
        if (Files.exists(xmlPath)) {
            return (List<User>) xstream.fromXML(xmlPath.toFile());
        } else {
            return new ArrayList<>();
        }
    }

    public void save(User newUser){
        List<User> users = getAll();
        users.add(newUser);
        saveAll(users);
    }

    public void saveAll(List<User> users){
        try {
            if (!Files.exists(xmlPath)) {
                Files.createDirectories(xmlPath.getParent());
                Files.createFile(xmlPath);
            }
            Files.writeString(xmlPath, xstream.toXML(users));
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
        List<User> users = getAll();
        users.remove(user);
        saveAll(users);
    }
}
