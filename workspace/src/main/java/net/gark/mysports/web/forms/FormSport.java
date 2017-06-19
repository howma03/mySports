package net.gark.mysports.web.forms;

import net.gark.mysports.domain.interfaces.ISport;

import java.text.SimpleDateFormat;
import java.util.Date;

@Deprecated
public class FormSport implements ISport {

    private static final String pattern = "yyyy/MM/dd HH:mm:ss";
    private static final SimpleDateFormat format = new SimpleDateFormat(pattern);

    private Long id;
    private Date created = new Date();
    private String createdString = "";
    private String title = "";
    private String details = "";
    private Rating rating;
    private Long ownerId;
    private Long groupId;

    @Override
    public Long getId() {
        return id;
    }

    public FormSport setId(final Long id) {
        this.id = id;
        return this;
    }

    @Override
    public Date getCreated() {
        return new Date();
    }

    @Override
    public ISport setCreated(final Date created) {
        this.created = created;
        if (created == null) {
            this.createdString = "";
        } else {
            this.createdString = format.format(created);
        }
        return this;
    }

    @Override
    public String getCreatedString() {
        return createdString;
    }

    @Override
    public Long getOwnerId() {
        return ownerId;
    }

    @Override
    public ISport setOwnerId(final Long ownerId) {
        this.ownerId = ownerId;
        return this;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public ISport setTitle(final String title) {
        this.title = title;
        return this;
    }

    @Override
    public String getDetails() {
        return details;
    }

    @Override
    public ISport setDetails(final String details) {
        this.details = details;
        return this;
    }

    @Override
    public Rating getRating() {
        return rating;
    }

    @Override
    public ISport setRating(final Rating rating) {
        this.rating = rating;
        return this;
    }

    @Override
    public Long getGroupId() {
        return groupId;
    }

    @Override
    public ISport setGroupId(final Long groupId) {
        this.groupId = groupId;
        return this;
    }

    @Override
    public String toString() {
        return String.format("Id [%s] GroupId [%s]Title [%s] Rating [%s]", getId(), groupId, title, rating);
    }

}
