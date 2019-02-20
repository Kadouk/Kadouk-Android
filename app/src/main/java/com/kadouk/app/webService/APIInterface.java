package com.kadouk.app.webService;

import com.kadouk.app.model.AllCategoryResponse;
import com.kadouk.app.model.CategoryResponse;
import com.kadouk.app.model.ContentRespons;
import com.kadouk.app.model.Content;
import com.kadouk.app.model.Details;
import com.kadouk.app.model.RegisterResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface APIInterface{

 @POST("details")

 @FormUrlEncoded
 Call<Details> details (@Field("phone") String phone, @Header("Authorization") String Token);

 @GET("content/show/all")
 Call<ContentRespons> content() ;

 @POST ("get/phone?phone=…")
 @FormUrlEncoded
 Call<RegisterResponse> sendPhoneNumber (@Field("phone") String phone);

 @POST ("login?code=…&phone=…")
 @FormUrlEncoded
 Call<RegisterResponse> sendVerificationCode (@Field("code") String verificationCode,
                                              @Field("phone") String number) ;

 @POST ("register")
 @FormUrlEncoded
 Call<RegisterResponse> register (@Field("name") String name, @Field("gender") String kidGender,
                                  @Field("phone") String phone, @Field("birth") String birth) ;

 @POST ("get/version?version=")
//@POST ("payment?version=")
 @FormUrlEncoded
 Call<ContentRespons> sendAPICode (@Field("version") String API) ;

 @POST ("content/show/cat?cat=")
 @FormUrlEncoded
 Call<CategoryResponse> getContentByID (@Field("cat") int ID) ;

 @POST ("content/show/cat?cat=")
 @FormUrlEncoded
 Call<AllCategoryResponse> getAllCategory (@Field("cat") int ID) ;

 @POST ("content/show/page?id=")
 @FormUrlEncoded
 Call<Content> getAppDataByID (@Field("id") int ID) ;

 @POST("pass")
 @FormUrlEncoded
 Call<Details> sendParentPassword (@Field("pass") String pass, @Header("Authorization") String Token);

 @POST ("content/search")
 @FormUrlEncoded
 Call<CategoryResponse> searchAppDataByDetails (@Field("s") String searchText) ;
}
