package net.gark.mysports.services.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mark on 29/05/2017.
 */
public class DtoReportUserRankings {

    private final Long userId;

    private DtoRatingSummary overallSummary;

    private List<DtoRatingSummary> groupRatings = new ArrayList<>();

    public DtoReportUserRankings(Long userId){
        this.userId = userId;

    }
    public Long getUserId() {
        return userId;
    }

    public List<DtoRatingSummary> getGroupRankings() {
        return groupRatings;
    }

    public void setGroupRankings(List<DtoRatingSummary> groupRankings) {
        this.groupRatings = groupRankings;
    }

    public void add(DtoRatingSummary groupRanking){
        this.groupRatings.add(groupRanking);
    }

    public DtoRatingSummary getOverallSummary() {
        return overallSummary;
    }

    public void setOverallSummary(DtoRatingSummary overallSummary) {
        this.overallSummary = overallSummary;
    }
}
