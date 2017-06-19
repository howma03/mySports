package net.gark.mysports.domain.entity;

import net.gark.mysports.domain.interfaces.ISport;
import net.gark.mysports.domain.interfaces.IUtility;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by mark on 08/05/2017.
 */
@Entity
@Table(name = "sport")
public class Sport implements ISport {

    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private final Date created = new Date();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long ownerId;

    @NotNull
    private String title;

    @Lob
    @Column(nullable = false, length = 65535)
    private String details;

    @NotNull
    private Rating rating;

    @Column
    private Long groupId;

    public Sport() {
    }

    public Sport(final Long id) {
        this.id = id;
    }

    public Sport(final ISport from) {
        ISport.COPY(from, this);
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public Date getCreated() {
        return new Date(created.getTime());
    }

    @Override
    public String getCreatedString() {
        return IUtility.format(created);
    }

    @Override
    public ISport setCreated(final Date created) {
        if (created != null) {
            this.created.setTime(created.getTime());
        }
        return this;
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
        return String.format("sport Id [%d] Owner Id [%d] Group Id [%d] Title [%s]", id, ownerId, groupId, title);
    }
}
