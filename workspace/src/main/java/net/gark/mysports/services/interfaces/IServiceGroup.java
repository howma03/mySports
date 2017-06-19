package net.gark.mysports.services.interfaces;

import net.gark.mysports.domain.interfaces.IGroup;
import net.gark.mysports.domain.interfaces.IGroupMember;
import net.gark.mysports.domain.interfaces.IOrganisation;
import net.gark.mysports.domain.interfaces.IUser;
import net.gark.mysports.services.ServiceGroupImplRepository;
import net.gark.mysports.services.dto.DtoGroupMember;

import java.util.List;
import java.util.Optional;

/**
 * Created by mark on 28/05/2017.
 */
public interface IServiceGroup {

    IGroup createGroup(IOrganisation organisation, IGroup group);

    Optional<IGroup> readGroup(Long groupId);

    List<IGroup> readGroups(IOrganisation organisation);

    IGroup updateGroup(IGroup group);

    void deleteGroup(Long id);

    IOrganisation getGroupOwner(Long groupId);

    void setGroupOwner(Long groupId, IOrganisation organisation);

    List<IGroup> readAllGroups();

    List<IGroup> readGroups(IGroupMember.IGroupMemberFilter iGroupMemberFilter);

    List<IGroup> readGroupsManagedByUser(Long userId);

    List<IUser> readGroupAdmins(long groupId);

    /**
     * Group Membership functions
     */

    IGroupMember createGroupMember(DtoGroupMember dtoGroupMember) throws ServiceGroupImplRepository.ServiceGroupException;

    List<IGroup> getMembershipGroupsForUser(Long userId);

    List<IUser> getMembershipUsersForGroup(Long groupId);

    List<IGroupMember> getGroupMembershipsForUser(Long userId);

    List<IGroupMember> getGroupMembershipsForGroup(Long groupId);

    IGroupMember getMembership(Long groupMembershipId);

    IGroupMember updateGroupMember(DtoGroupMember groupMember);
}
