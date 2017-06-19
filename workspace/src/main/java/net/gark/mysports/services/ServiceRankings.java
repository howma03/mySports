package net.gark.mysports.services;

import net.gark.mysports.domain.interfaces.IGroup;
import net.gark.mysports.domain.interfaces.ISport;
import net.gark.mysports.domain.interfaces.IUser;
import net.gark.mysports.services.dto.DtoRatingSummary;
import net.gark.mysports.services.dto.DtoReportGroupRanking;
import net.gark.mysports.services.dto.DtoReportUserRankings;
import net.gark.mysports.services.interfaces.IServiceGroup;
import net.gark.mysports.services.interfaces.IServiceRankings;
import net.gark.mysports.services.interfaces.IServiceSport;
import net.gark.mysports.services.interfaces.IServiceUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service to determine rankings
 * <p>
 * Initially we only have a single group
 * <p>
 * All users will be ranked against each other
 * <p>
 * We will first total all sports (all and for the past 7 days)
 * <p>
 * An sport will be weighted
 * <p>
 * Then we will determine each users ranking
 */
@Service("serviceRankings")
public class ServiceRankings implements IServiceRankings {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceRankings.class);

    @Autowired
    private IServiceUser serviceUser;

    @Autowired
    private IServiceSport serviceSport;

    @Autowired
    private IServiceGroup serviceGroup;


    /**
     * Provides for the user their ranking within each group - it also provide a summary of the user activity (over all groups)
     *
     * @param user
     * @return DtoReportUserRankings
     */
    @Override
    public DtoReportUserRankings getRankingsForUser(final IUser user) {
        final DtoReportUserRankings rankings = new DtoReportUserRankings(user.getId());
        final List<IGroup> groups = serviceGroup.getMembershipGroupsForUser(user.getId());
        groups.stream().forEach(g -> {
            final List<ISport> sports = serviceSport.getAllForGroup(g);
            /**
             * We have to obtain all the sports recorded in the group and compare these for the user
             */
            final DtoReportGroupRanking groupRanking = new DtoReportGroupRanking(g.getId(), g.getName(), sports);
            final DtoRatingSummary userGroupRatingSummary = groupRanking.getRatingForUser(user.getId());
            rankings.add(userGroupRatingSummary);
        });

        final List<ISport> sports = serviceSport.getAllForUser(user);
        final DtoReportGroupRanking groupRanking = new DtoReportGroupRanking(null, "", sports);
        final DtoRatingSummary overallSummary = groupRanking.getRatingForUser(user.getId());
        rankings.setOverallSummary(overallSummary);

        return rankings;
    }
}
