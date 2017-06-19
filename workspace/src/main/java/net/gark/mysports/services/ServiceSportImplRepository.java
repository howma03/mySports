package net.gark.mysports.services;

import net.gark.mysports.domain.entity.Sport;
import net.gark.mysports.domain.interfaces.IGroup;
import net.gark.mysports.domain.interfaces.ISport;
import net.gark.mysports.domain.interfaces.IUser;
import net.gark.mysports.domain.repository.IRepositorySport;
import net.gark.mysports.services.interfaces.IServiceSport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("servicesport")
class ServiceSportImplRepository implements IServiceSport {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceSportImplRepository.class);

    @Autowired
    private IRepositorySport repositorysport;

    public ServiceSportImplRepository() {
        LOGGER.debug("Default CTOR");
    }

    /**
     * Convert a List<sport> to a List<ISport>
     *
     * @param from<ISport>
     * @return List<sport>
     */
    private static List<ISport> convert(final Iterable<Sport> from) {
        final List<ISport> list = new ArrayList<>();
        if (from != null) {
            from.forEach(list::add);
        }
        return list;
    }

    @Override
    public ISport create(final ISport sport) throws ExceptionServicesport {
        if (sport == null) {
            throw new ExceptionServicesport("Cannot create null sport");
        }
        return repositorysport.save(new Sport(sport));
    }

    @Override
    public ISport update(final ISport sport) throws ExceptionServicesport {
        final Sport found = repositorysport.findOne(sport.getId());
        if (found == null) {
            throw new ExceptionServicesport("sport not found - " + sport.getId());
        }
        // Always ensure the owner of the sport is not changed
        sport.setOwnerId(found.getOwnerId());
        // Always ensure the created time of the sport is not modified
        sport.setCreated(found.getCreated());

        ISport.COPY(sport, found);
        repositorysport.save(found);
        return found;
    }

    @Override
    public ISport findOne(final Long id) {
        return repositorysport.findOne(id);
    }

    @Transactional
    @Override
    public void delete(final Long id) {
        final Sport found = repositorysport.findOne(id);
        if (found == null) {
            LOGGER.error("Failed to delete sport - not found - id=" + id);
            return;
        }
        repositorysport.delete(id);
    }

    @Override
    public List<ISport> getAll() {
        return convert(repositorysport.findAll());
    }

    @Override
    public List<ISport> getAllForGroup(final IGroup group) {
        return repositorysport.findByGroupId(group.getId());
    }

    @Override
    public List<ISport> getAllForUser(final IUser user) {
        return repositorysport.findByOwnerIdOrderByCreatedDesc(user.getId());
    }

    @Override
    public List<ISport> get(final Integer page, final Integer pageSize, final Order order) {
        final List<ISport> l = new ArrayList<>();
        if (page != null && pageSize != null) {
            final Pageable pageable;
            if (order == null) {
                pageable = new PageRequest(page, pageSize);
            } else {
                pageable = new PageRequest(page, pageSize, new Sort(order));
            }
            final Page<Sport> list = repositorysport.findAll(pageable);
            for (final ISport a : list) {
                l.add(a);
            }
        }
        return l;
    }

    @Override
    public long getCount() {
        return repositorysport.count();
    }

}
