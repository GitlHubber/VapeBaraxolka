package ragalik.baraxolka.old_xyina;

import com.google.gson.annotations.SerializedName;

public class Ad {

    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;

    @SerializedName("category")
    private String category;
    @SerializedName("id")
    private String dateTime;
    @SerializedName("id")
    private String town;
    @SerializedName("id")
    private int price;
    @SerializedName("id")
    private int views;
    @SerializedName("id")
    private String subcategory;
    @SerializedName("id")
    private String image1;
    private String image2;
    private String image3;
    private String image4;
    private String image5;
    private String userNickname;
    private String userImage;
    private int sellerId;

    public Ad () {
    }

    public Ad (int id) {
        this.id = id;
    }

    public Ad(int id, String title, String subcategory, String category, String description, String dateTime, String town,
              int price, int views, String image1, String image2, String image3, String image4, String image5, String userNickname, String userImage, int sellerId) {
        this.id = id;
        this.title = title;
        this.subcategory = subcategory;
        this.category = category;
        this.description = description;
        this.views = views;
        this.dateTime = dateTime;
        this.town = town;
        this.price = price;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
        this.image4 = image4;
        this.image5 = image5;
        this.userNickname = userNickname;
        this.userImage = userImage;
        this.sellerId = sellerId;
    }

    public Ad(int id, String title, String subcategory, String description, String dateTime, String town, int price, int views, String image1,
              String image2, String image3, String image4, String image5) {
        this.id = id;
        this.title = title;
        this.subcategory = subcategory;
        this.description = description;
        this.dateTime = dateTime;
        this.town = town;
        this.price = price;
        this.views = views;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
        this.image4 = image4;
        this.image5 = image5;
    }


    public String getUserImage() {
        return userImage;
    }

    public int getSellerId() {
        return sellerId;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public String getCategory() {
        return category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTown() {
        return town;
    }

    public String getTitle() {
        return title;
    }

    public String getImage1() { return image1; }

    public String getImage2() { return image2; }

    public String getImage3() { return image3; }

    public String getImage4() { return image4; }

    public String getImage5() { return image5; }

    public String getDescription() {
        return description;
    }

    public String getDateTime() {
        return dateTime;
    }

    public int getPrice() {
        return price;
    }

    public int getViews() { return views; }

    public String getSubcategory() {
        return subcategory;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}