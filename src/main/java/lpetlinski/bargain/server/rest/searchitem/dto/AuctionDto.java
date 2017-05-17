package lpetlinski.bargain.server.rest.searchitem.dto;

import lpetlinski.bargain.server.domain.searchitem.Auction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AuctionDto {
    private Long offerId;
    private String title;
    private int count;
    private Date endTime;
    private float buyNowPrice;
    private float withDeliveryPrice;
    private float biddingPrice;
    private List<String> photos = new ArrayList<>();

    public AuctionDto() {
        // do nothing
    }

    public AuctionDto(Auction auction) {
        this.offerId = auction.getOfferId();
        this.title = auction.getTitle();
        this.count = auction.getCount();
        this.endTime = auction.getEndTime();
        this.buyNowPrice = auction.getBuyNowPrice();
        this.withDeliveryPrice = auction.getWithDeliveryPrice();
        this.biddingPrice = auction.getBiddingPrice();
        photos.addAll(auction.getPhotos());

    }

    public Long getOfferId() {
        return offerId;
    }

    public void setOfferId(Long offerId) {
        this.offerId = offerId;
    }

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

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }
}
