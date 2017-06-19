package net.gark.mysports.services.dto;

import net.gark.mysports.domain.entity.Sport;
import net.gark.mysports.domain.interfaces.ISport;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * Contains report rating summary data for a user within a group
 */
public class DtoRatingSummary implements Comparable<DtoRatingSummary> {

    private final Long userId;
    private final Long groupId;
    private final String groupName;
    private final Integer totals[] = new Integer[Sport.Rating.values().length];
    private final Integer totalsInLastWeek[] = new Integer[Sport.Rating.values().length];
    private final LocalDate sevenDaysAgo;

    private Integer total = 0;
    private Integer count = 0;
    private Integer rank = 0;

    private Integer totalInLast7Days = 0;
    private Integer countInLast7Days = 0;
    private Integer rankInLast7Days = 0;

    private Integer membersInGroup = 0;

    public DtoRatingSummary(final Long groupId, final String groupName, final Long userId) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.userId = userId;
        for (final ISport.Rating rating : ISport.Rating.values()) {
            totals[rating.ordinal()] = 0;
            totalsInLastWeek[rating.ordinal()] = 0;
        }
        sevenDaysAgo = LocalDate.now().minusDays(7);
    }

    public void record(final ISport a) {
        totals[a.getRating().ordinal()]++;
        total += a.getRating().ordinal() + 1;
        count++;

        final Instant instant = a.getCreated().toInstant();
        final ZonedDateTime zdt = instant.atZone(ZoneId.systemDefault());
        final LocalDate createdDate = zdt.toLocalDate();

        if (createdDate.isAfter(sevenDaysAgo)) {
            totalsInLastWeek[a.getRating().ordinal()]++;
            totalInLast7Days += a.getRating().ordinal() + 1;
            countInLast7Days++;
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof DtoRatingSummary)) {
            return false;
        }
        return compareTo((DtoRatingSummary) o) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }

    @Override
    public int compareTo(final DtoRatingSummary o) {
        return total.compareTo(o.total);
    }

    public Long getUserId() {
        return userId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public Integer[] getTotals() {
        return totals;
    }

    public Integer getTotal() {
        return total;
    }

    public Integer getTotalInLast7Days() {
        return totalInLast7Days;
    }

    public Integer getCount() {
        return count;
    }

    public Integer getCountInLast7Days() {
        return countInLast7Days;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(final Integer rank) {
        this.rank = rank;
    }

    public Integer getMembersInGroup() {
        return membersInGroup;
    }

    public void setMembersInGroup(final Integer membersInGroup) {
        this.membersInGroup = membersInGroup;
    }

    public Integer getRankInLast7Days() {
        return rankInLast7Days;
    }

    public void setRankInLast7Days(final Integer rankInLast7Days) {
        this.rankInLast7Days = rankInLast7Days;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DtoRatingSummary ");
        sb.append(String.format("User [%3d] ", getUserId()));
        sb.append(String.format("Total (score) [%3d] ", getTotal()));
        sb.append(String.format("Total (score) In Last 7 Days [%3d] ", getTotalInLast7Days()));
        sb.append(String.format("Count [%3d] ", getCount()));
        sb.append(String.format("Count In Last 7 Days [%3d] ", getCountInLast7Days()));
        sb.append(String.format("Ranked [%3d/%3d] ", rank, this.membersInGroup));
        return sb.toString();
    }
}
