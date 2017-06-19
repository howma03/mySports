package net.gark.mysports.services.interfaces;

import net.gark.mysports.domain.interfaces.ISport;
import net.gark.mysports.domain.interfaces.IGroup;
import net.gark.mysports.domain.interfaces.IUser;
import org.springframework.data.domain.Sort.Order;

import java.util.List;

public interface IServiceSport {

    ISport create(ISport user) throws ExceptionServicesport;

    ISport update(ISport user) throws ExceptionServicesport;

    List<ISport> getAll();

    List<ISport> getAllForGroup(IGroup group);

    List<ISport> getAllForUser(IUser user);

    long getCount();

    ISport findOne(Long id);

    void delete(Long id) throws ExceptionServicesport;

    List<ISport> get(Integer page, Integer pageSize, Order order);

    class ExceptionServicesport extends RuntimeException {

        public ExceptionServicesport(final String message, final Exception e) {
            super(message, e);
        }

        public ExceptionServicesport(final String message) {
            super(message);
        }
    }

}
