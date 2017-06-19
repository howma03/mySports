package net.gark.mysports.domain.repository;

import net.gark.mysports.domain.entity.Sport;
import net.gark.mysports.domain.interfaces.ISport;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface IRepositorySport extends PagingAndSortingRepository<Sport, Long> {
    /**
     * This method will find an User instance in the database by its email.
     * Note that this method is not implemented and its working code will be
     * automagically generated from its signature by Spring Data JPA.
     */
    List<ISport> findByOwnerId(Long ownerId);

    List<ISport> findByOwnerIdOrderByCreatedDesc(Long ownerId);

    List<ISport> findByGroupId(Long groupId);
}