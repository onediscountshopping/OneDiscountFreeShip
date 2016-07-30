package bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yhy on 2016/7/28.
 */
public class Fengqiang implements Serializable{
    private double disCount;
    private String endDate;
    private String imgUrlSml;
    private String name;
    private List<ProductInfo>infos;
    private int brandId;

    public double getDisCount() {
        return disCount;
    }

    public void setDisCount(double disCount) {
        this.disCount = disCount;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getImgUrlSml() {
        return imgUrlSml;
    }

    public void setImgUrlSml(String imgUrlSml) {
        this.imgUrlSml = imgUrlSml;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ProductInfo> getInfos() {
        return infos;
    }

    public void setInfos(List<ProductInfo> infos) {
        this.infos = infos;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public class ProductInfo {
        private String productImg;
        private double newPrice,oldPrice;

        public String getProductImg() {
            return productImg;
        }

        public void setProductImg(String productImg) {
            this.productImg = productImg;
        }

        public double getNewPrice() {
            return newPrice;
        }

        public void setNewPrice(double newPrice) {
            this.newPrice = newPrice;
        }

        public double getOldPrice() {
            return oldPrice;
        }

        public void setOldPrice(double oldPrice) {
            this.oldPrice = oldPrice;
        }
    }
}
