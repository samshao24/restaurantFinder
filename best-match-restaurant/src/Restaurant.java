public class Restaurant {

    private Cuisine cuisine;
    private String name;
    private float customerRating;
    private float distance;
    private float price;

    public Restaurant(Cuisine cuisine, String name, float customerRating, float distance, float price) {
        this.cuisine = cuisine;
        this.name = name;
        this.customerRating = customerRating;
        this.distance = distance;
        this.price = price;
    }

    public Restaurant() {}

    public Cuisine getCuisine() {
        return cuisine;
    }

    public void setCuisine(Cuisine cuisine) {
        this.cuisine = cuisine;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getCustomerRating() {
        return customerRating;
    }

    public void setCustomerRating(float customerRating) {
        this.customerRating = customerRating;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        return sb.append("[Name:] ").append(this.name).append(" ")
                .append("[Cuisine:] ").append(this.cuisine.getName()).append(" ")
                .append("[Distance:] ").append(this.distance).append(" ")
                .append("[Customer Rating:] ").append(this.customerRating).append(" ")
                .append("[Price:] ").append(this.price).append(" ").toString();
    }
}
