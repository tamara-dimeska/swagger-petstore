package ip.swagger.petstore.utils;

public class Utils {
    public String combineUserObjectsInString(String[] users) {
        return "[" + String.join(",", users) + "]";
    }
}
