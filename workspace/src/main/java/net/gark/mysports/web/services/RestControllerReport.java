package net.gark.mysports.web.services;

import net.gark.mysports.services.dto.DtoRatingSummary;
import net.gark.mysports.services.dto.DtoReportUserRankings;
import net.gark.mysports.services.interfaces.IServiceRankings;
import net.gark.mysports.web.controllers.ControllerAbstract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by mark on 25/05/2017.
 */
@RestController
@RequestMapping("/api/report")
public class RestControllerReport extends ControllerAbstract {

    @Autowired
    private IServiceRankings serviceRankings;

    @RequestMapping(value = "/summary", method = RequestMethod.GET)
    public DtoReportUserRankings get() {
        if (this.getLoggedInUser() != null) {
            return serviceRankings.getRankingsForUser(this.getLoggedInUser());
        }
        return null;
    }
}
