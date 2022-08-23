import java.util.ArrayList;
import java.util.List;

public class TwitterApiResponse {
    private List<Tweet> data = new ArrayList<>();

    public List<Tweet> getData() {
        return data;
    }

    public void setData(List<Tweet> data) {
        this.data = data;
    }
}
