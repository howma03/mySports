package net.gark.mysports.services;

import net.gark.mysports.domain.entity.Group;
import net.gark.mysports.domain.entity.GroupMember;
import net.gark.mysports.domain.entity.User;
import net.gark.mysports.domain.interfaces.IGroup;
import net.gark.mysports.domain.interfaces.IGroupMember;
import net.gark.mysports.domain.interfaces.IOrganisation;
import net.gark.mysports.domain.interfaces.IUser;
import net.gark.mysports.domain.repository.IRepositoryGroup;
import net.gark.mysports.domain.repository.IRepositoryGroupMember;
import net.gark.mysports.domain.repository.IRepositoryUser;
import net.gark.mysports.services.dto.DtoGroupMember;
import net.gark.mysports.services.interfaces.IServiceGroup;
import net.gark.mysports.services.interfaces.IServiceOrganisation;
import net.gark.mysports.services.interfaces.IServicePasswordGenerator;
import net.gark.mysports.services.interfaces.IServiceRegistration;
import org.apache.commons.collections.IteratorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by mark on 28/05/2017.
 */
@Service
public class ServiceGroupImplRepository implements IServiceGroup {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceGroupImplRepository.class);

    @Autowired
    private IRepositoryUser repositoryUser;

    @Autowired
    private IRepositoryGroup repositoryGroup;

    @Autowired
    private IRepositoryGroupMember repositoryGroupMember;

    @Autowired
    private IServiceOrganisation serviceOrganisation;

    @Autowired
    private IServiceRegistration serviceRegistration;

    @Autowired
    private IServicePasswordGenerator passwordGenerator;

    /**
     * CRUD operations for Groups
     */

    @Override
    public IGroup createGroup(final IOrganisation organisation, final IGroup group) {
        final IOrganisation foundOrganisation = this.serviceOrganisation.readOrganisation(organisation.getId());
        if (foundOrganisation == null) {
            LOGGER.warn("Failed to find organisation - id=" + organisation.getId());
            return null;
        }
        IGroup added = new Group();
        IGroup.COPY(group, added);

        added = this.repositoryGroup.save((Group) added);
        LOGGER.info("Created new Group " + added);
        return added;
    }

    @Override
    public List<IGroup> readAllGroups() {
        return IteratorUtils.toList(this.repositoryGroup.findAll().iterator());
    }

    @Override
    public Optional<IGroup> readGroup(final Long groupId) {
        final IGroup group = this.repositoryGroup.findOne(groupId);
        if (group == null) {
            LOGGER.warn("Failed to find group - id=" + groupId);
            return Optional.empty();
        }
        return Optional.of(group);
    }

    @Override
    public List<IGroup> readGroups(final IOrganisation organisation) {
        if (organisation == null) {
            LOGGER.warn("Cannot read groups for null organisation");
            return new ArrayList<>();
        }
        return this.repositoryGroup.findByOwnerOrganisationId(organisation.getId());
    }

    @Override
    public List<IGroup> readGroups(final IGroupMember.IGroupMemberFilter groupMemberFilter) {
        return new Converter<GroupMember, IGroupMember>().convert(this.repositoryGroupMember.findAll())
                .stream()
                .filter(gm -> groupMemberFilter.match(gm))
                .map(gm -> repositoryGroup.findOne(gm.getGroupId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<IGroup> readGroupsManagedByUser(final Long userId) {
        return this.readGroups(gm -> (gm.getUserId().equals(userId) &&
                gm.getPermissions() == IGroupMember.Permission.ADMIN));
    }

    @Override
    public List<IUser> readGroupAdmins(final long groupId) {
        return this.repositoryGroupMember.findByGroupId(groupId)
                .stream()
                .filter(gm -> gm.getPermissions().equals(IGroupMember.Permission.ADMIN))
                .map(gm -> repositoryUser.findOne(gm.getUserId()))
                .collect(Collectors.toList());
    }

    @Override
    public IGroup updateGroup(final IGroup group) {
        LOGGER.info("Updating a Group - " + group);
        final IGroup found = this.repositoryGroup.findOne(group.getId());
        if (found == null) {
            LOGGER.error("Failed to find group - " + group);
            return null;
        }
        // Ensure created time remains unchanged
        group.setCreated(found.getCreated());
        IGroup.COPY(group, found);

        return repositoryGroup.save((Group) found);
    }

    @Override
    public void deleteGroup(final Long id) {
        LOGGER.info("Deleting a group - " + id);
        // TODO: Need to consider the operations when we delete a group
        // TODO: perhaps we simply disable it? - should we orphan user's sports?
        final IGroup found = this.repositoryGroup.findOne(id);
        if (found == null) {
            LOGGER.error("Failed to find group - id=" + id);
            return;
        }
        found.setEnabled(false);
        repositoryGroup.save((Group) found);
    }

    /**
     * Function returns the owner of a group - which will be an organization
     *
     * @param groupId
     * @return IOrganisation
     */
    @Override
    public IOrganisation getGroupOwner(final Long groupId) {
        final IGroup found = this.repositoryGroup.findOne(groupId);
        if (found == null) {
            LOGGER.error("Failed to find group - " + groupId);
            return null;
        }
        final IOrganisation foundOrganisation = this.serviceOrganisation.readOrganisation(found.getOwnerOrganisationId());
        if (foundOrganisation == null) {
            LOGGER.error("Failed to find group owner organisation - groupId=" + groupId);
            return null;
        }
        return foundOrganisation;
    }

    /**
     * Function to set the owner of a group
     *
     * @param groupId
     * @param organisation
     */
    @Override
    public void setGroupOwner(final Long groupId, final IOrganisation organisation) {
        final IGroup found = this.repositoryGroup.findOne(groupId);
        if (found == null) {
            LOGGER.error("Failed to find group - " + groupId);
            return;
        }
        found.setOwnerOrganisationId(organisation.getId());
        this.repositoryGroup.save((Group) found);
        LOGGER.info("Save Group Owner - " + found);
    }


    /***
     * Function to handle the invitation for a user to join a group
     * @param dtoGroupMember
     * @return IGroupMember
     */
    @Override
    public IGroupMember createGroupMember(final DtoGroupMember dtoGroupMember) throws ServiceGroupException {
        IGroupMember groupMember;

        // Validate the group - does it exist?

        final IGroup group = this.repositoryGroup.findOne((dtoGroupMember.getGroupId()));
        if (group == null) {
            LOGGER.warn("Cannot create membership - group not known - " + dtoGroupMember);
            throw new ServiceGroupException("Group cannot be found");
        }

        // TODO: Implement a limit on the number of members in the group - this will be paid for accounts vs free ones

        IUser user = repositoryUser.findByEmail(dtoGroupMember.getEmail());
        if (user == null) {

            // We must create a User account for the new user - before sending the invitation

            final String password = passwordGenerator.generate();

            user = new User();
            user.setEmail(dtoGroupMember.getEmail());
            user.setFirstName(dtoGroupMember.getFirstName());
            user.setLastName(dtoGroupMember.getLastName());
            user.setPassword(password);

            user = serviceRegistration.register(user);
        }

        groupMember = new GroupMember();
        IGroupMember.COPY(dtoGroupMember, groupMember);
        groupMember.setUserId(user.getId());

        groupMember = this.repositoryGroupMember.save((GroupMember) groupMember);

        // TODO: trigger email for membership invitation

        return groupMember;
    }

    /**
     * Function returns a list of the all of the groups a user is a member of
     *
     * @param userId
     * @return List<IGroup>
     */
    @Override
    public List<IGroup> getMembershipGroupsForUser(final Long userId) {
        final List<IGroup> list = new ArrayList<>();
        repositoryGroupMember
                .findByUserId(userId)
                .stream()
                .forEach(gm -> {
                            final Group g = repositoryGroup.findOne(gm.getGroupId());
                            if (g != null) {
                                list.add(g);
                            }
                        }
                );
        return list;
    }

    /***
     * Function returns the User members of the given group
     * @param groupId
     * @return List<IUser>
     */
    @Override
    public List<IUser> getMembershipUsersForGroup(final Long groupId) {
        final List<IUser> list = new ArrayList<>();
        repositoryGroupMember
                .findByGroupId(groupId)
                .stream()
                .forEach(gm -> {
                            final User u = repositoryUser.findOne(gm.getUserId());
                            if (u != null) {
                                list.add(u);
                            }
                        }
                );
        return list;
    }

    /***
     * Returns a list of Group Memberships for a given user
     * @param userId
     * @return List<IGroupMember>
     */
    @Override
    public List<IGroupMember> getGroupMembershipsForUser(final Long userId) {
        final List<IGroupMember> list = new ArrayList<>();
        repositoryGroupMember
                .findByUserId(userId)
                .forEach(gm -> list.add(gm));
        return list;
    }

    /***
     * Returns a list of Group Memberships for a given group
     * @param groupId
     * @return List<IGroupMember>
     */
    @Override
    public List<IGroupMember> getGroupMembershipsForGroup(final Long groupId) {
        final List<IGroupMember> list = new ArrayList<>();
        repositoryGroupMember
                .findByGroupId(groupId)
                .forEach(gm -> list.add(gm));
        return list;
    }

    /***
     * Function to read a specific Group Membership record
     * @param groupMembershipId
     * @return IGroupMember
     */
    @Override
    public IGroupMember getMembership(final Long groupMembershipId) {
        final IGroupMember gm = this.repositoryGroupMember.findOne(groupMembershipId);
        if (gm == null) {
            LOGGER.warn("Cannot find group membership - not found - " + groupMembershipId);
        }
        return gm;
    }


    /**
     * Function to update a group membership
     * Should be limited to changing status and permissions (or disable/enable)
     *
     * @param groupMember
     * @return IGroupMember
     */
    @Override
    public IGroupMember updateGroupMember(final DtoGroupMember groupMember) {
        LOGGER.info("Updating a Group Member - " + groupMember);
        final IGroupMember found = this.repositoryGroupMember.findOne(groupMember.getId());
        if (found == null) {
            LOGGER.error("Failed to find group - " + groupMember);
            return null;
        }
        // We ensure some properties are never modified
        groupMember.setCreated(found.getCreated());
        groupMember.setUserId(found.getUserId());
        groupMember.setGroupId(found.getGroupId());
        IGroupMember.COPY(groupMember, found);

        return repositoryGroupMember.save((GroupMember) found);
    }

    /***
     * Function to delete a Group Membership - we will not implement this function yet; we will simply mark the membership as disabled
     * @param groupMemberId
     * @return IGroupMember
     */
    public IGroupMember deleteGroupMember(final Long groupMemberId) throws ServiceGroupException {
        final IGroupMember gm = this.repositoryGroupMember.findOne(groupMemberId);
        if (gm == null) {
            LOGGER.warn("Cannot delete group membership - not found - " + groupMemberId);
            throw new ServiceGroupException("Group Membership cannot be found");
        }
        gm.setEnabled(false);
        return this.repositoryGroupMember.save((GroupMember) gm);
    }

    public static class ServiceGroupException extends RuntimeException {
        public ServiceGroupException() {

        }

        public ServiceGroupException(final String message) {
            super(message);
        }

        public ServiceGroupException(final String message, final Exception ex) {
            super(message, ex);
        }
    }
}
