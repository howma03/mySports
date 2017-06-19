package net.gark.mysports.domain.interfaces;

import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * Created by mark on 08/05/2017.
 */
public interface ISport {

    static ISport COPY(final ISport from, final ISport to) {
        if (from == null) {
            return null;
        }
        if (to == null) {
            return null;
        }
        BeanUtils.copyProperties(from, to);
        return to;
    }

    Long getId();

    default ISport setId(final Long id) {
        return this;
    }

    Date getCreated();

    ISport setCreated(Date created);

    String getCreatedString();

    Long getOwnerId();

    ISport setOwnerId(Long ownerId);

    String getTitle();

    ISport setTitle(String title);

    String getDetails();

    ISport setDetails(String details);

    Rating getRating();

    ISport setRating(Rating rating);

    Long getGroupId();

    ISport setGroupId(Long groupId);

    enum Rating {

        Simple(1),
        Medium(2),
        Hard(3);

        int stars = 0;

        Rating(final int stars) {
            this.stars = stars;
        }

        public int getStars() {
            return stars;
        }

        public String getName() {
            return this.toString();
        }
    }
}
