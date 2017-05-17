package lpetlinski.bargain.server.domain.searchitem;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class NotInterestingAuction {
    @NotNull
    private Long offerId;
    @NotNull
    private Date endTime;

    public Long getOfferId() {
        return offerId;
    }

    public void setOfferId(Long offerId) {
        this.offerId = offerId;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
