package com.kadouk.app.webService;


import com.kadouk.app.model.App;
import com.kadouk.app.model.CatagoryResponse;
import com.kadouk.app.model.ContentRespons;
import com.kadouk.app.model.Details;
import com.kadouk.app.model.Response;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface APIInterface{

//@GET("login")
// Call <Response> login();


 @POST("details")
 @FormUrlEncoded
 Call<Details> details (@Field("phone") String phone, @Header("Authorization") String Token);

 @GET("content/show/all")
 Call<ContentRespons> content() ;

 @POST ("get/phone?phone=…")
 @FormUrlEncoded
 Call<Response> sendPhoneNumber (@Field("phone") String phone);

 @POST ("login?code=…&phone=…")
 @FormUrlEncoded
 Call<Response> sendVerificationCode (@Field("code") String verificationCode, @Field("phone") String number) ;

 @POST ("register")
 @FormUrlEncoded
 Call<Response> register (@Field("name") String name, @Field("gender") String kidGender, @Field("phone") String phone, @Field("birth") String birth) ;

 @POST ("get/version?version=")
 @FormUrlEncoded
 Call<ContentRespons> sendAPICode (@Field("version") String API) ;

 @POST ("content/show/cat?cat=")
 @FormUrlEncoded
 Call<CatagoryResponse> getContentByID (@Field("cat") int ID) ;

 @POST ("content/show/page?id=")
 @FormUrlEncoded
 Call<App> getAppDataByID (@Field("id") int ID) ;
}
