package ragalik.baraxolka.network;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import ragalik.baraxolka.network.entities.AdsCount;
import ragalik.baraxolka.network.entities.CategoryResponse;
import ragalik.baraxolka.network.entities.ReasonsResponse;
import ragalik.baraxolka.network.entities.RegisterResponse;
import ragalik.baraxolka.network.entities.ReportResponse;
import ragalik.baraxolka.network.entities.ServerResponse;
import ragalik.baraxolka.network.entities.User;
import ragalik.baraxolka.network.entities.UserPreviewResponse;
import ragalik.baraxolka.network.entities.UserResponse;
import ragalik.baraxolka.paging_feed.AdResponse;
import ragalik.baraxolka.paging_feed.FullAdResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface IApi {

    //GET---GET

    @GET("/scripts/getUserInfo.php")
    Call<List<User>> getUserInfo(@Query("id") int id);

    @GET("/scripts/getAds.php")
    Call<AdResponse> getAds(@Query("page") int page,
                            @Query("status_number") int status_number,
                            @Query("id") int id);

    @GET("/scripts/getAdsCount.php")
    Call<AdsCount> getAdsCount(@Query("status_number") int status_number);

    @GET("/scripts/getMyAds.php")
    Call<AdResponse> getMyAds(@Query("page") int page,
                              @Query("status_number") int status_number,
                              @Query("id") int id,
                              @Query("user_id") int user_id);

    @GET("/scripts/getSearchAds.php")
    Call<AdResponse> getSearchAds(@Query("page") int page,
                              @Query("status_number") int status_number,
                              @Query("where") String where,
                              @Query("sort_field") String sort_field,
                              @Query("sort_type") String sort_type,
                              @Query("user_id") int user_id);

    @GET("/scripts/getFavouritesAds.php")
    Call<AdResponse> getFavouritesAds(@Query("page") int page,
                                      @Query("id") int id);

    @GET("/scripts/acceptRejectAd.php")
    Call<ServerResponse> acceptRejectAd(@Query("id") int id,
                                        @Query("status") int status,
                                        @Query("dateTime") String datetime,
                                        @Query("reason_id") int reason_id,
                                        @Query("reject_message") String reject_message);

    @GET("/scripts/setEditor.php")
    Call<ServerResponse> setEditor(@Query("email") String email);

    @GET("/scripts/getUsersPreview.php")
    Call<UserPreviewResponse> getUsersPreview(@Query("page") int page,
                                              @Query("user_status") int user_status);

    @GET("/scripts/removeEditor.php")
    Call<ServerResponse> removeEditor(@Query("email") String email);

    @GET("/scripts/getFullAd.php")
    Call<FullAdResponse> getFullAd(@Query("id_ad") int id_ad,
                                   @Query("user_id") int user_id);

    @GET("/scripts/getMyReports.php")
    Call<ReportResponse> getMyReports(@Query("page") int page,
                                   @Query("user_id") int user_id);

    @GET("/scripts/getReports.php")
    Call<ReportResponse> getReports(@Query("page") int page);

    @GET("/scripts/authorization.php")
    Call<UserResponse> auth(@Query("email_or_number") String email_or_number,
                            @Query("flag") String flag,
                            @Query("password") String password);

    @GET("/scripts/getMyAdsCount.php")
    Call<AdsCount> getMyAdsCount(@Query("status_number") int status,
                                 @Query("id") int id);

    @GET("/scripts/getSearchAdsCount.php")
    Call<AdsCount> getSearchAdsCount(@Query("status_number") int status,
                                 @Query("where") String where);

    @GET("/scripts/getCategoriesWithSubcategories.php")
    Call<CategoryResponse> getCategoriesWithSubcategories();

    @GET("/scripts/getRejectReasons.php")
    Call<ReasonsResponse> getRejectReasons();

    @GET("/scripts/deleteDeactivateAd.php")
    Call<ServerResponse> deleteDeactivateAds(@Query("ad_id") int ad_id,
                                             @Query("user_id") int user_id);



    //POST---POST

    @FormUrlEncoded
    @POST("/scripts/deleteUserImage.php")
    Call<ServerResponse> deleteAccountImage(@Field("id") int id,
                                            @Field("filename") String filename);

    @FormUrlEncoded
    @POST("/scripts/setDeleteBookmark.php")
    Call<ServerResponse> setDeleteBookmark(@Field("ad_id") int ad_id,
                                           @Field("user_id") int user_id,
                                           @Field("datetime") String datetime,
                                           @Field("flag") String flag);

    @FormUrlEncoded
    @POST("/scripts/insertAdReport.php")
    Call<ServerResponse> insertAdReport(@Field("report_reason_id") int report_reason_id,
                                           @Field("report_message") String report_message,
                                           @Field("datetime") String datetime,
                                           @Field("ad_id") int ad_id,
                                           @Field("isReportExists") int isReportExists,
                                           @Field("user_id") int user_id);

    @FormUrlEncoded
    @POST("/scripts/setRegion.php")
    Call<ServerResponse> setRegion(@Field("region") String region,
                                   @Field("town") String town,
                                   @Field("email") String email);

    @FormUrlEncoded
    @POST("/scripts/setPhoneNumber.php")
    Call<ServerResponse> setPhoneNumber(@Field("phoneNumber") String phoneNumber,
                                        @Field("email") String email);

    @FormUrlEncoded
    @POST("/scripts/showHidePhone.php")
    Call<ServerResponse> showHidePhone(@Field("isPhoneHide") int isPhoneHide,
                                        @Field("email") String email);

    @FormUrlEncoded
    @POST("/scripts/registration.php")
    Call<RegisterResponse> register(@Field("nickname") String nickname,
                                    @Field("email") String email,
                                    @Field("phoneNumber") String phoneNumber,
                                    @Field("region") String region,
                                    @Field("town") String town,
                                    @Field("password") String password);



    //MULTIPART---MULTIPART

    @Multipart
    @POST("/scripts/uploadAccountImage.php")
    Call<String> uploadAccountImage(@Part MultipartBody.Part file,
                                    @Part("email") RequestBody email);

    @Multipart
    @POST("/scripts/insertAd.php")
    Call<ServerResponse> insertAd(@Part("title") RequestBody title,
                                  @Part("description") RequestBody description,
                                  @Part MultipartBody.Part image1,
                                  @Part MultipartBody.Part image2,
                                  @Part MultipartBody.Part image3,
                                  @Part MultipartBody.Part image4,
                                  @Part MultipartBody.Part image5,
                                  @Part("price") RequestBody price,
                                  @Part("nickname") RequestBody nickname,
                                  @Part("dateTime") RequestBody datetime,
                                  @Part("subcategory") RequestBody subcategory);

    @Multipart
    @POST("/scripts/editCreator.php")
    Call<ServerResponse> editCreator(@Part("ad_id") RequestBody ad_id,
                                     @Part("title") RequestBody title,
                                     @Part("description") RequestBody description,
                                     @Part MultipartBody.Part image1,
                                     @Part MultipartBody.Part image2,
                                     @Part MultipartBody.Part image3,
                                     @Part MultipartBody.Part image4,
                                     @Part MultipartBody.Part image5,
                                     @Part("price") RequestBody price,
                                     @Part("dateTime") RequestBody datetime,
                                     @Part("subcategory") RequestBody subcategory);
}
