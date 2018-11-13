package id.co.tpcc.drypediaapp.api;

import java.util.List;

import id.co.tpcc.drypediaapp.model.ItemResult;
import id.co.tpcc.drypediaapp.model.LoginResult;
import id.co.tpcc.drypediaapp.model.TokoResult;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiInterface {
    @GET("Toko_Laundry")
    Call<List<TokoResult>> getTokos();

    @GET("item/search_by_toko/{id}")
    Call<List<ItemResult>> getItems(@Path("id") String id);

    @FormUrlEncoded
    @POST("login/login")
    Call<LoginResult> login(@Field("identity") String username,
                            @Field("password") String password);

    @FormUrlEncoded
    @POST("login/registration")
    Call<String> registration(@Field("email") String username,
                              @Field("phone") String phone,
                              @Field("password") String password);

    @FormUrlEncoded
    @POST("transaksi/save")
    Call<String> checkout(@Field("user_id") String userId,
                          @Field("alamat") String alamat,
                          @Field("harga") int harga,
                          @Field("berat") String berat,
                          @Field("subtotal") double subtotal);

    @Multipart
    @POST("transaksi/bukti_bayar")
    Call<String> buktiBayar(@Part MultipartBody.Part image,
                            @Part("id_nota") RequestBody idnota);
                            //@Part("token") RequestBody token);

    //@GET("toko/notification")
    //Call<List<NotificationResult>> getNotifications();
}
