package net.gark.mysports.domain.repository;

import net.gark.mysports.domain.entity.Group;
import net.gark.mysports.domain.entity.GroupMember;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface IRepositoryGroupMember extends PagingAndSortingRepository<GroupMember, Long> {

    List<GroupMember> findByUserId(Long userId);

    List<GroupMember> findByGroupId(Long groupId);

}