public enum Cuisine {

    AMERICAN  (1, "American"),
    CHINESE   (2, "Chinese"),
    THAI      (3, "Thai"),
    ITALIAN   (4, "Italian"),
    FRENCH    (5, "French"),
    JAPANESE  (6, "Japanese"),
    TURKISH   (7, "Turkish"),
    KOREAN    (8, "Korean"),
    VIETNAMESE(9, "Vietnamese"),
    INDIAN    (10, "Indian"),
    SPANISH   (11, "Spanish"),
    GREEK     (12, "Greek"),
    MEXICAN   (13, "Mexican"),
    MALAYSIAN (14, "Malaysian"),
    AFRICAN   (15, "African"),
    GERMAN    (16, "German"),
    INDONESIAN(17, "Indonesian"),
    RUSSIAN   (18, "Russian"),
    OTHER     (19, "Other");

    private long id;
    private String name;

    Cuisine(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static Cuisine findById(long cuisineId) {
        for (Cuisine cuisine : Cuisine.values()) {
            if (cuisine.getId() == cuisineId) {
                return cuisine;
            }
        }
        return null;
    }
}
