package lpetlinski.bargain.server.domain.searchitem;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Auction {
    @NotNull
    private Long offerId;
    @NotNull
    private String title;
    private int count;
    private Date endTime;
    private float buyNowPrice;
    private float withDeliveryPrice;
    private float biddingPrice;
    private List<String> photos = new ArrayList<>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public float getBuyNowPrice() {
        return buyNowPrice;
    }

    public void setBuyNowPrice(float buyNowPrice) {
        this.buyNowPrice = buyNowPrice;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }

    public Long getOfferId() {
        return offerId;
    }

    public void setOfferId(Long offerId) {
        this.offerId = offerId;
    }

    public float getWithDeliveryPrice() {
        return withDeliveryPrice;
    }

    public void setWithDeliveryPrice(float withDeliveryPrice) {
        this.withDeliveryPrice = withDeliveryPrice;
    }

    public float getBiddingPrice() {
        return biddingPrice;
    }

    public void setBiddingPrice(float biddingPrice) {
        this.biddingPrice = biddingPrice;
    }

}
