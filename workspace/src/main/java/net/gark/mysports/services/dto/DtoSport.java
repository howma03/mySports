package net.gark.mysports.services.dto;

import net.gark.mysports.domain.interfaces.ISport;
import net.gark.mysports.domain.interfaces.IUtility;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

/**
 * Created by mark on 08/05/2017.
 */
public class DtoSport extends DtoBase implements ISport {

    private final Date created = new Date();
    private Long id = 0l;
    private Long ownerId = 0l;
    private String title;
    private String createdString = "";
    private String details;
    private Rating rating;
    private Long groupId = 0l;
    private String groupName;

    public DtoSport() {
    }

    public DtoSport(final Long id) {
        this.id = id;
    }

    public DtoSport(final ISport from) {
        ISport.COPY(from, this);
        this.id = from.getId();
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public DtoSport setId(final Long id) {
        this.id = id;
        return this;
    }

    @Override
    public Date getCreated() {
        return created;
    }

    @Override
    public DtoSport setCreated(final Date created) {
        if (created == null) {
            this.createdString = "";
        } else {
            this.created.setTime(created.getTime());
            this.createdString = IUtility.format(created);
        }
        return this;
    }

    @Override
    public String getCreatedString() {
        return createdString;
    }

    public DtoSport setCreatedString(final String createdString) {
        this.createdString = createdString;
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

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(final String groupName) {
        this.groupName = groupName;
    }

    public int getAgeInDays() {
        if (this.created == null) {
            return 0;
        }
        final LocalDate today = LocalDate.now();
        final Instant instant = this.created.toInstant();
        final LocalDate createdDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
        return Period.between(createdDate, today).getDays();
    }

    public void setAgeInDays(final int days) {

    }

    @Override
    public String toString() {
        return String.format("sport Id [%d] Owner Id [%d] Group Id [%d] Title [%s]", id, ownerId, groupId, title);
    }
}
