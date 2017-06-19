package net.gark.mysports.services.interfaces;

import net.gark.mysports.domain.interfaces.IUser;
import org.springframework.data.domain.Sort.Order;

import java.util.List;

public interface IServiceUser {

    class ExceptionServiceUser extends RuntimeException {

        public ExceptionServiceUser(String message, Exception e) {
            super(message, e);
        }

        public ExceptionServiceUser(String message) {
            super(message);
        }
    }

    IUser addUser(IUser user) throws ExceptionServiceUser;

    IUser updateUser(IUser user) throws ExceptionServiceUser;

    List<String> getAllUserEmailAddresses();

    List<IUser> getAll();

    long getUserCount();

    IUser findUser(Long userId);

    IUser findUserByEmail(String email);

    void deleteUser(Long userId);

    List<IUser> getUsers(Integer page, Integer pageSize, Order order);


}
