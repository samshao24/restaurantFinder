import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class RestaurantFinder {

    int MAX_LIMIT = 5;

    public static void main(String[] args) {

        RestaurantFinder finder = new RestaurantFinder();

        System.out.println("Loading data... Please wait...");
        List<Restaurant> data = finder.loadData();
        System.out.println("All restaurants data loaded in the memory");

        RestaurantSearchCriteria criteria = new RestaurantSearchCriteria();
        Scanner input = new Scanner(System.in);

        System.out.println("========================================================");
        String nameCriteria = finder.collectStringInput("Restaurant", input);
        criteria.setName(nameCriteria);

        System.out.println("========================================================");
        String cuisineCriteria = finder.collectStringInput("Cuisine", input);
        criteria.setCuisine(cuisineCriteria);

        System.out.println("========================================================");
        Float customerRatingCriteria = finder.collectFloatInput("Customer Rating", input);
        criteria.setCustomerRating(customerRatingCriteria);

        System.out.println("========================================================");
        Float distanceCriteria = finder.collectFloatInput("Distance", input);
        criteria.setDistance(distanceCriteria);

        System.out.println("========================================================");
        Float priceCriteria = finder.collectFloatInput("Price", input);
        criteria.setPrice(priceCriteria);

        System.out.println("========================================================");
        List<Restaurant> result = finder.findBestMatchedRestaurant(data, criteria);

        if (!result.isEmpty() && result.size() > 0) {
            System.out.println("Matching restaurants are : ");
            result.forEach(r -> System.out.println(r));
        } else {
            System.out.println("There is no matching restaurant found.");
        }
    }

    List<Restaurant> loadData() {
        List<Restaurant> restaurantList = new ArrayList<>();
        try {

            InputStream is = this.getClass().getResourceAsStream("restaurants.csv");

            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;
            boolean lineSkip = true;
            while ((line = br.readLine()) != null) {
                if (lineSkip) {
                    lineSkip = false;
                    continue;
                }
                String[] values = line.split(",");

                String name = values[0];
                Float customerRating = Float.parseFloat(values[1]);
                Float distance = Float.parseFloat(values[2]);
                Float price = Float.parseFloat(values[3]);
                long cuisineId = Long.parseLong(values[4]);
                Cuisine cuisine = Cuisine.findById(cuisineId);
                Restaurant restaurant = new Restaurant(cuisine, name,
                        customerRating, distance, price);

                restaurantList.add(restaurant);
            }
        } catch (FileNotFoundException fnfe) {
            System.out.println("Unable to find file to load data.");
            System.exit(0);
        } catch (IOException ioe) {
            System.out.println("Found file to load data but unable to load data.");
            System.exit(0);
        } catch (Exception e) {
            System.out.println("General exception happened when loading data.");
            System.exit(1);
        }
        return restaurantList;
    }

    public String collectStringInput(String inputName, Scanner scanner) {
        System.out.print("Please Enter " + inputName + " : ");
        String input = scanner.nextLine();
        return input;
    }

    public Float collectFloatInput(String inputName, Scanner scanner) {
        System.out.print("Please Enter " + inputName + " : ");
        String input = scanner.nextLine();
        if (!input.isEmpty()) {
            try {
                Float parsedInput = Float.parseFloat(input);
                return parsedInput;
            } catch (NumberFormatException e) {
                System.out.println("Invalid " + inputName + " provided. Please submit again");
                return collectFloatInput(inputName, scanner);
            }
        }
        return null;
    }

    /**
     * This method uses java lamda expression to filter, sort and limit results,
     * based on provided criteria, which is used to filter from source data.
     *
     * @param data
     * @param criteria
     * @return
     */
    public List<Restaurant> findBestMatchedRestaurant(List<Restaurant> data,
                                                      RestaurantSearchCriteria criteria) {
        Predicate<Restaurant> compositePredicate = createPredicates(criteria);
        List<Restaurant> resultList = data.stream()
                .filter(compositePredicate)
                .sorted(new RestaurantComparator())
                .limit(MAX_LIMIT)
                .collect(Collectors.toList());
        return resultList;
    }

    /**
     * This function is to build conditional combined Predicate based on provided
     * criteria.
     * If not criteria is provided, then always return TRUE for filtering purpose.
     *
     * @param criteria
     * @return
     */
    public Predicate<Restaurant> createPredicates(RestaurantSearchCriteria criteria) {

        List<Predicate<Restaurant>> predicateList = new ArrayList<>();

        String nameCriteria = criteria.getName();
        if (!nameCriteria.isEmpty()) {
            predicateList.add(r ->
                    r.getName().toUpperCase().indexOf(nameCriteria.toUpperCase()) != -1);
        }

        String cuisineCriteria = criteria.getCuisine();
        if (!cuisineCriteria.isEmpty()) {
            predicateList.add(r ->
                    r.getCuisine().getName().toUpperCase().indexOf(cuisineCriteria.toUpperCase()) != -1);
        }

        Float customerRatingCriteria = criteria.getCustomerRating();
        if (customerRatingCriteria != null) {
            predicateList.add(r ->
                    r.getPrice() >= customerRatingCriteria);
        }

        Float distanceCriteria = criteria.getDistance();
        if (distanceCriteria != null) {
            predicateList.add(r -> r.getDistance() <= distanceCriteria);
        }

        Float priceCriteria = criteria.getPrice();
        if (priceCriteria != null) {
            predicateList.add(r -> r.getPrice() <= priceCriteria);
        }

        if (!predicateList.isEmpty() && predicateList.size() > 0) {
            Predicate<Restaurant> compositePredicate = predicateList.get(0);
            for (int i = 1; i < predicateList.size(); i++) {
                compositePredicate = compositePredicate.and(predicateList.get(i));
            }
            return compositePredicate;
        }
        return r -> Boolean.TRUE;
    }
}
