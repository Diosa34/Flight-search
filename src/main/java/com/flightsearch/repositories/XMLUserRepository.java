package com.flightsearch.repositories;

import com.flightsearch.exceptions.NotFoundException;
import com.flightsearch.models.User;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.AnyTypePermission;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

@Repository
public class XMLUserRepository {
    final XStream xstream;

    @Value("${LIST_OF_USERS}")
    private String xmlPath;

    public XMLUserRepository() {
        this.xstream = new XStream();
        this.xstream.addPermission(AnyTypePermission.ANY);
    }

    public ArrayList<User> getAll() {
        xstream.alias("user", User.class);
        File xmlFile = new File(xmlPath);
        try {
            if (xmlFile.createNewFile()) {
                FileWriter xmlFileWriter = new FileWriter(xmlFile);
                xmlFileWriter.append("<list></list>");
                xmlFileWriter.close();
            }
            return (ArrayList<User>) this.xstream.fromXML(xmlFile, "list");
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
            xstream.toXML(users, new FileWriter(xmlPath, false));
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
