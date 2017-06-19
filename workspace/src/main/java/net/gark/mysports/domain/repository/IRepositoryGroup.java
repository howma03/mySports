package net.gark.mysports.domain.repository;

import net.gark.mysports.domain.entity.Group;
import net.gark.mysports.domain.entity.User;
import net.gark.mysports.domain.interfaces.IGroup;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface IRepositoryGroup extends PagingAndSortingRepository<Group, Long> {
    List<IGroup> findByOwnerOrganisationId(Long id);
}