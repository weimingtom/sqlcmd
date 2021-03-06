package ua.com.juja.sqlcmd.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.juja.sqlcmd.dao.hibernate.UserActionDao;
import ua.com.juja.sqlcmd.dao.hibernate.UserDao;
import ua.com.juja.sqlcmd.dao.jpa.ConnectionRepository;
import ua.com.juja.sqlcmd.dao.jpa.UserActionRepository;
import ua.com.juja.sqlcmd.dao.jpa.UserRepository;
import ua.com.juja.sqlcmd.dao.jpa.UserRoleRepository;
import ua.com.juja.sqlcmd.dao.manager.DatabaseManager;
import ua.com.juja.sqlcmd.model.*;
import ua.com.juja.sqlcmd.service.LogService;

import java.util.*;

@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private DatabaseManager manager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConnectionRepository connectionRepository;

    @Autowired
    private UserActionRepository userActionRepository;

    @Autowired
    UserDao userDao;

    @Autowired
    UserActionDao userActionDao;

  @Override
    @Transactional
    public UserAction saveUserAction(String description) {
        User user = getActiveUser();
        DatabaseConnection connection = getDatabaseConnection();
        UserAction action = new UserAction();
        action.setUser(user);
        action.setDatabaseConnection(connection);
        action.setAction(description);
        action.setDate(new Date());

        //JPA
        return userActionRepository.save(action);

        //Hibernate
//        return userActionDao.create(action);
    }

    private User getActiveUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user;
        if (auth == null) {
            user = new User();
            user.setUsername("empty");
            user.setPassword("empty");
            user.setEmail("empty");
        } else {
            String username = auth.getName();
            user = userRepository.findByUsername(username);
            if (user == null) {
                user = new User();
                user.setUsername("empty");
                user.setPassword("empty");
                user.setEmail("empty");
            }
        }
        return user;
    }

    private DatabaseConnection getDatabaseConnection() {
        DatabaseConnection connection = manager.getDatabaseConnection();
        if (connection == null) {
            connection =  connectionRepository.findByServerAndPortAndDatabaseNameAndUserName(
                    "empty", "empty", "empty", "empty");
            if (connection == null) {
                connection = new DatabaseConnection();
                connection.setServer("empty");
                connection.setPort("empty");
                connection.setDatabaseName("empty");
                connection.setUserName("empty");
                connectionRepository.save(connection);
            }
        } else {
            String server = connection.getServer();
            String port = connection.getPort();
            String databaseName = connection.getDatabaseName();
            String databaseUserName = connection.getUserName();
            connection =  connectionRepository.findByServerAndPortAndDatabaseNameAndUserName(
                    server, port, databaseName, databaseUserName);
            if (connection == null) {
                connection = new DatabaseConnection();
                connection.setServer(server);
                connection.setPort(port);
                connection.setDatabaseName(databaseName);
                connection.setUserName(databaseUserName);
                connectionRepository.save(connection);
            }
        }
        return connection;
    }

    @Override
    public List<UserAction> getUserActions() {
        List<UserAction> actions = (List<UserAction>) userActionRepository.findAll();
        for (UserAction action: actions) {
            action.getUser().setUserRoles(new HashSet<UserRole>());
        }
        return actions;
    }

    @Override
    public void deleteUserActions() {
        userActionRepository.deleteAll();
    }
}
