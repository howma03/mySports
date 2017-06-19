package net.gark.mysports.services.dto;

import net.gark.mysports.domain.entity.Sport;
import net.gark.mysports.domain.interfaces.ISport;

import java.util.*;
import java.util.stream.Collectors;

public class DtoReportGroupRanking {

    private final Long groupId;
    private final Integer totals[];
    private final List<DtoRatingSummary> table;
    private Integer total = 0;

    public DtoReportGroupRanking(final Long groupId, final String groupName, final List<ISport> sports) {
        this.groupId = groupId;
        totals = new Integer[Sport.Rating.values().length];
        for (final ISport.Rating rating : ISport.Rating.values()) {
            totals[rating.ordinal()] = 0;
        }
        final Map<Long, DtoRatingSummary> summaries = new HashMap<>();

        sports.stream().forEach(a -> {
            total += a.getRating().ordinal() + 1;
            totals[a.getRating().ordinal()]++;
            DtoRatingSummary summary = summaries.get(a.getOwnerId());
            if (summary == null) {
                summary = new DtoRatingSummary(groupId, groupName, a.getOwnerId());
                summaries.put(a.getOwnerId(), summary);
            }
            summary.record(a);
        });

        final List<DtoRatingSummary> temp = summaries
                .entrySet()
                .stream()
                .sorted(Comparator.comparing(e -> e.getValue().getTotal()))
                .map(x -> x.getValue())
                .collect(Collectors.toList());

        table = temp.stream().sorted(Comparator.comparingInt(DtoRatingSummary::getTotal).reversed()).collect(Collectors.toList());
        // record each summary's position (rank)
        table.stream().forEach(s -> {
            s.setRank(table.indexOf(s) + 1);
            s.setMembersInGroup(table.size());
        });
    }

    public static void main(final String[] args) {
        final List<ISport> sports = new ArrayList<>();

        Long id = 0l;
        sports.add(new Sport(id++).setOwnerId(1L).setRating(ISport.Rating.values()[0]));
        sports.add(new Sport(id++).setOwnerId(1L).setRating(ISport.Rating.values()[0]));
        sports.add(new Sport(id++).setOwnerId(1L).setRating(ISport.Rating.values()[1]));
        sports.add(new Sport(id++).setOwnerId(2L).setRating(ISport.Rating.values()[1]));
        sports.add(new Sport(id++).setOwnerId(2L).setRating(ISport.Rating.values()[1]));
        sports.add(new Sport(id++).setOwnerId(2L).setRating(ISport.Rating.values()[1]));
        sports.add(new Sport(id++).setOwnerId(2L).setRating(ISport.Rating.values()[1]));
        sports.add(new Sport(id++).setOwnerId(3L).setRating(ISport.Rating.values()[1]));
        sports.add(new Sport(id++).setOwnerId(3L).setRating(ISport.Rating.values()[2]));
        sports.add(new Sport(id++).setOwnerId(3L).setRating(ISport.Rating.values()[2]));
        sports.add(new Sport(id++).setOwnerId(3L).setRating(ISport.Rating.values()[2]));
        sports.add(new Sport(id++).setOwnerId(4L).setRating(ISport.Rating.values()[1]));
        sports.add(new Sport(id++).setOwnerId(4L).setRating(ISport.Rating.values()[1]));
        sports.add(new Sport(id++).setOwnerId(5L).setRating(ISport.Rating.values()[1]));
        sports.add(new Sport(id++).setOwnerId(6L).setRating(ISport.Rating.values()[1]));
        sports.add(new Sport(id++).setOwnerId(7L).setRating(ISport.Rating.values()[1]));

        final DtoReportGroupRanking rankings = new DtoReportGroupRanking(1l, "Test Group", sports);

        System.out.println("--------------------");
        System.out.println(rankings);
        System.out.println("--------------------");
    }

    public DtoRatingSummary getRatingForUser(final Long userId) {
        final List<DtoRatingSummary> result = table.stream()
                .filter(s -> s.getUserId().equals(userId))
                .collect(Collectors.toList());
        if (result.size() > 0) {
            return result.get(0);
        }
        return null;
    }

    public Long getGroupId() {
        return groupId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(String.format("DtoReportGroupRanking - Total [%d]%n", total));
        table.stream().forEach(s -> {
            sb.append(String.format("[%d] %s\n", table.indexOf(s), s));

        });
        return sb.toString();
    }

    /**
     * Function returns ordered list of DtoRatingSummary objects - the order is based on the summary score
     *
     * @return List<DtoRatingSummary>
     */
    public List<DtoRatingSummary> getSummaries() {
        return table;
    }
}
