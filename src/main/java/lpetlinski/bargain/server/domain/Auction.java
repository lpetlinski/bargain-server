package lpetlinski.bargain.server.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Auction {
    private Long _offerId;
    private String _title;
    private int _count;
    private Date _endTime;
    private float _buyNowPrice;
    private float _withDeliveryPrice;
    private float _biddingPrice;
    private List<String> _photos = new ArrayList<>();

    public String getTitle() {
        return _title;
    }

    public void setTitle(String title) {
        _title = title;
    }

    public int getCount() {
        return _count;
    }

    public void setCount(int count) {
        _count = count;
    }

    public Date getEndTime() {
        return _endTime;
    }

    public void setEndTime(Date endTime) {
        _endTime = endTime;
    }

    public float getBuyNowPrice() {
        return _buyNowPrice;
    }

    public void setBuyNowPrice(float buyNowPrice) {
        _buyNowPrice = buyNowPrice;
    }

    public List<String> getPhotos() {
        return _photos;
    }

    public void setPhotos(List<String> photos) {
        _photos = photos;
    }

    public Long getOfferId() {
        return _offerId;
    }

    public void setOfferId(Long offerId) {
        _offerId = offerId;
    }

    public float getWithDeliveryPrice() {
        return _withDeliveryPrice;
    }

    public void setWithDeliveryPrice(float withDeliveryPrice) {
        _withDeliveryPrice = withDeliveryPrice;
    }

    public float getBiddingPrice() {
        return _biddingPrice;
    }

    public void setBiddingPrice(float biddingPrice) {
        _biddingPrice = biddingPrice;
    }

}
