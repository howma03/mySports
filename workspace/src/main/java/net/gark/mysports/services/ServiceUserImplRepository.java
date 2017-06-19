package net.gark.mysports.services;

import net.gark.mysports.domain.entity.User;
import net.gark.mysports.domain.interfaces.IUser;
import net.gark.mysports.domain.repository.IRepositoryUser;
import net.gark.mysports.services.interfaces.IServiceUser;
import org.apache.commons.collections.IteratorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service("userService")
public class ServiceUserImplRepository implements IServiceUser {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceUserImplRepository.class);

    private static final BCryptPasswordEncoder ENCODER = new BCryptPasswordEncoder();

    @Autowired
    private IRepositoryUser userRepository;

    public ServiceUserImplRepository() {
    }

    /**
     * Function to convert a List<User> to a List<IUser>
     *
     * @param from<User>
     * @return List<User>
     */
    private static List<IUser> convert(final Iterable<User> from) {
        final List<IUser> list = new ArrayList<>();
        if (from != null) {
            for (final IUser ps : from) {
                list.add((User) ps);
            }
        }
        return list;
    }

    /**
     * Adds a new user
     *
     * @param user
     * @return IUser
     * @throws ExceptionServiceUser
     */
    @Override
    public IUser addUser(final IUser user) throws ExceptionServiceUser {
        final User foundUser = userRepository.findByEmail(user.getEmail());
        if (foundUser != null) {
            throw new ExceptionServiceUser("User already exists - " + foundUser);
        }

        try {
            final User newUser = new User(user);
            LOGGER.info("Adding user - new user=" + user);
            // if a password is provided we need to convert it


            newUser.setPassword(ENCODER.encode(user.getPassword()));
            newUser.setVerified(false);
            newUser.setVerificationCode(UUID.randomUUID().toString());
            newUser.setEnabled(true);
            newUser.setAdmin(false);

            return userRepository.save(newUser);

        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new ExceptionServiceUser("addUser()", e);
        }
    }

    /**
     * Function to update a User
     *
     * @param user
     * @return IUser
     * @throws ExceptionServiceUser
     */
    @Override
    public IUser updateUser(final IUser user) throws ExceptionServiceUser {
        if (user == null) {
            throw new ExceptionServiceUser("User cannot be null!");
        }
        final User found = userRepository.findOne(user.getId());
        if (found == null) {
            throw new ExceptionServiceUser("User not found - " + user.getEmail());
        }
        // Ensure created time remains unchanged
        if (user.getPassword() == null) {
            user.setPassword(found.getPassword());
        }
        if (user.getVerificationCode() == null) {
            user.setVerificationCode(found.getVerificationCode());
        }
        user.setCreated(found.getCreated());
        IUser.COPY(user, found);
        return userRepository.save(found);
    }

    @Override
    public IUser findUser(final Long userId) {
        return userRepository.findOne(userId);
    }

    @Override
    public IUser findUserByEmail(final String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional
    @Override
    public void deleteUser(final Long userId) {
        final User foundUser = userRepository.findOne(userId);
        if (foundUser == null) {
            return;
        }
        foundUser.setEnabled(false);
        this.updateUser(foundUser);
        // userRepository.delete(userId);
    }

    @Override
    public List<String> getAllUserEmailAddresses() {
        final List<String> l = new ArrayList<>();
        IteratorUtils.toList(userRepository.findAll().iterator()).forEach(u -> l.add(((User) u).getEmail()));
        return l;
    }

    @Override
    public List<IUser> getAll() {
        return convert(userRepository.findAll());
    }

    @Override
    public List<IUser> getUsers(final Integer page, final Integer pageSize, final Order order) {
        final List<IUser> l = new ArrayList<>();
        if (page != null && pageSize != null) {
            final Pageable pageable;
            if (order == null) {
                pageable = new PageRequest(page, pageSize);
            } else {
                pageable = new PageRequest(page, pageSize, new Sort(order));
            }
            final Page<User> list = userRepository.findAll(pageable);
            for (final User u : list) {
                l.add(u);
            }
        }
        return l;
    }

    @Override
    public long getUserCount() {
        return userRepository.count();
    }

}
