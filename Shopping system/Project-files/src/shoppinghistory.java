import java.util.HashMap;
import java.util.Map;

public class shoppinghistory {
    private Map<String, Integer> userPurchCountMap;

    public shoppinghistory() {
        userPurchCountMap = new HashMap<>();
    }

    public void addPurchase(String clientId) {
        int currentCount = userPurchCountMap.getOrDefault(clientId, 0);
        userPurchCountMap.put(clientId, currentCount + 1);
    }

    public int getUserPurchCount(String clientId) {
        return userPurchCountMap.getOrDefault(clientId, 0);
    }
}
