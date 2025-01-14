package my.app.momschoice.customerFoodPanel;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {

    @Headers({
            "Content-Type: application/json",
            "Authorization: key=YOUR_SERVER_KEY"
    })
    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body NotificationSender body);
}

