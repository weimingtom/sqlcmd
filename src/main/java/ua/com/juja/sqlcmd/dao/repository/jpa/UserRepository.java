package ua.com.juja.sqlcmd.dao.repository.jpa;

import org.springframework.data.repository.CrudRepository;
import ua.com.juja.sqlcmd.model.User;

public interface UserRepository extends CrudRepository<User, Integer> {
//    User findByLogin(String login);
}
