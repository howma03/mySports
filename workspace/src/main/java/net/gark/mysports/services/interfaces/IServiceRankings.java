package net.gark.mysports.services.interfaces;

import net.gark.mysports.domain.interfaces.IUser;
import net.gark.mysports.services.dto.DtoRatingSummary;
import net.gark.mysports.services.dto.DtoReportGroupRanking;
import net.gark.mysports.services.dto.DtoReportUserRankings;

/**
 * Created by mark on 25/05/2017.
 */
public interface IServiceRankings {
    DtoReportUserRankings getRankingsForUser(IUser user);
}
