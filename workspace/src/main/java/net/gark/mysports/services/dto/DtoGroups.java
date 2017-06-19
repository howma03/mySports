package net.gark.mysports.services.dto;

import net.gark.mysports.domain.interfaces.IGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by mark on 26/05/2017.
 */
public class DtoGroups {

    private Collection<DtoGroup> data = new ArrayList<>();

    public DtoGroups() {
    }

    public DtoGroups(final List<IGroup> list) {
        list.stream().forEach(g -> data.add(new DtoGroup(g)));
    }

    public Collection<DtoGroup> getData() {
        return data;
    }

    public void setData(final Collection<DtoGroup> data) {
        this.data.clear();
        this.data.addAll(data);
    }

    public Integer getLength() {
        return this.data.size();
    }

    public void add(final DtoGroup dtoGroup) {
        this.data.add(dtoGroup);
    }

    public void addAll(final DtoGroups list) {
        this.data.addAll(list.getData());
    }
}