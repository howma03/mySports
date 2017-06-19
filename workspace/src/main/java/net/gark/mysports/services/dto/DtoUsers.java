package net.gark.mysports.services.dto;

import java.util.List;

/**
 * Created by mark on 26/05/2017.
 */
public class DtoUsers extends DtoList<DtoUser> {

    public DtoUsers() {

    }

    public DtoUsers(final DtoUsers from) {
        data.addAll(from.getData());
    }

    public DtoUsers(final List<DtoUser> data) {
        this.data.addAll(data);
    }
}