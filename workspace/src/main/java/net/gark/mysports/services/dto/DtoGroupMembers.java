package net.gark.mysports.services.dto;

import net.gark.mysports.domain.interfaces.IGroupMember;

import java.util.List;

/**
 * Created by mark on 26/05/2017.
 */
public class DtoGroupMembers extends DtoList<DtoGroupMember> {

    public DtoGroupMembers(final List<IGroupMember> list) {
        list.stream().forEach(gm -> data.add((DtoGroupMember) gm));
    }

    public DtoGroupMembers(final DtoGroupMembers from) {
        data.addAll(from.getData());
    }

    public DtoGroupMembers() {

    }

}