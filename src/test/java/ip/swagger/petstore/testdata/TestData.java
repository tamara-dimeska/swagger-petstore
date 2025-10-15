package ip.swagger.petstore.testdata;

public final class TestData {
    public static final String USER_PASSWORD = System.getenv("USER_PASSWORD");
    public static final String USER_PHONE = "12345";
    public static final String EMAIL_DOMAIN = "@email.com";
    public static final int USER_ID = 1;
    public static final int USER_STATUS = 1;
    public static final String USER_PASSWORD_UPDATED = System.getenv("USER_NEW_PASSWORD");
    public static final String USER_PHONE_UPDATED = "54321";

    public static final int PET_CATEGORY_ID = 1;
    public static final String PET_URL = "url";

    public static final String DOG_NAME = "Doggo";
    public static final String DOG_CATEGORY = "dogs";
    public static final String[] DOG_TAGS = new String[]{"dog"}; // TODO change this to a list
    public static final String DOG_STATUS = "available";

    public static final String CAT_NAME = "Catto";
    public static final String CAT_CATEGORY = "cats";
    public static final String[] CAT_TAGS = new String[]{"cat"}; // TODO change this to a list -> public static final List<String> CAT_TAGS = List.of("cat");
    public static final String CAT_STATUS = "pending";

    public static final int ORDER_ID = 10;
    public static final int ORDER_PET_ID = 123;
    public static final int ORDER_QUANTITY = 10;
    public static final String ORDER_STATUS = "available";

}
