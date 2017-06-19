package net.gark.mysports.web.forms;

import net.gark.mysports.services.dto.DtoGroupMember;

import java.util.ArrayList;
import java.util.List;

@Deprecated
public class FormGroupMembership  {

    private Long groupId; // nick name
    private String groupName; // nick name
    private String invitedUsers; // nick name

    private List<DtoGroupMember> members = new ArrayList<>();

    public FormGroupMembership(){

    }

    public Long getGroupId() {
        return groupId;
    }

    public FormGroupMembership setGroupId(Long groupId) {
        this.groupId = groupId;
        return this;
    }

    public String getGroupName() {
        return groupName;
    }

    public FormGroupMembership setGroupName(String groupName) {
        this.groupName = groupName;
        return this;
    }

    public List<DtoGroupMember> getMembers() {
        return members;
    }

    public void setMembers(List<DtoGroupMember> members) {
        this.members = members;
    }

    public String getInvitedUsers() {
        return invitedUsers;
    }

    public void setInvitedUsers(String invitedUsers) {
        this.invitedUsers = invitedUsers;
    }

    @Override
    public String toString() {
        return String.format("Group Id [%s] Name [%s] ", groupId, groupName);
    }
}
