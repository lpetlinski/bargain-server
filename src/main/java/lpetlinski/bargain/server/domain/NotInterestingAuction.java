package lpetlinski.bargain.server.domain;

import java.util.Date;

public class NotInterestingAuction {
    private Long _offerId;
    private Date _endTime;

    public Long getOfferId() {
        return _offerId;
    }

    public void setOfferId(Long offerId) {
        _offerId = offerId;
    }

    public Date getEndTime() {
        return _endTime;
    }

    public void setEndTime(Date endTime) {
        _endTime = endTime;
    }
}
