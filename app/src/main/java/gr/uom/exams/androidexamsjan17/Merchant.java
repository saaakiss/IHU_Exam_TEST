package gr.uom.exams.androidexamsjan17;

/**
 * Created by Thodoris on 24-Jan-17.
 */

public class Merchant {

    private String id;
    private String legalName;
    private String category;
    private String address;
    private String review;

    public Merchant(String id, String legalName,String category, String address, String imageUrl, String review) {
        this.id = id;
        this.legalName = legalName;
        this.category = category;
        this.address = address;
        this.review = review;
    }

    public String getAddress() {
        return address;
    }

    public String getCategory() {
        return category;
    }

    public String getId() {
        return id;
    }

    public String getLegalName() {
        return legalName;
    }

    public String getReview() {
        return review;
    }
}
