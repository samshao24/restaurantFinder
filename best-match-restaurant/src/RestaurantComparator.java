import java.util.Comparator;

public class RestaurantComparator implements Comparator<Restaurant> {

    @Override
    public int compare(Restaurant o1, Restaurant o2) {
        Float o1Distance = o1.getDistance();
        Float o2Distance = o2.getDistance();
        if (o1Distance.equals(o2Distance)) {
            Float o1CustomerRating = o1.getCustomerRating();
            Float o2CustomerRating = o2.getCustomerRating();
            if (o1CustomerRating.equals(o2CustomerRating)) {
                Float o1Price = o1.getPrice();
                Float o2Price = o2.getPrice();
                return o1Price.compareTo(o2Price);
            } else {
                return o2CustomerRating.compareTo(o1CustomerRating);
            }
        } else {
            return o1Distance.compareTo(o2Distance);
        }
    }
}
