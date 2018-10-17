package com.kadouk.app.webService;


import com.kadouk.app.model.ContentRespons;
import com.kadouk.app.model.Details;
import com.kadouk.app.model.Response;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIInterface{

//@GET("login")
// Call <Response> login();

 @Headers("Authorization: Bearer " +"eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6Ijg5NjJhNTE0NzBhMDI1ZTQzNTY0NWM1NWM0YzhiN2RlNzE0OTlhNjMxMzA3YjVkYTMzMGMzYmQ4MjFlNzc4YjlkOGJlZTdhZTFiYzE0MTUxIn0.eyJhdWQiOiIxIiwianRpIjoiODk2MmE1MTQ3MGEwMjVlNDM1NjQ1YzU1YzRjOGI3ZGU3MTQ5OWE2MzEzMDdiNWRhMzMwYzNiZDgyMWU3NzhiOWQ4YmVlN2FlMWJjMTQxNTEiLCJpYXQiOjE1MzM5MDY0MzQsIm5iZiI6MTUzMzkwNjQzNCwiZXhwIjoxNTY1NDQyNDM0LCJzdWIiOiIxMDMiLCJzY29wZXMiOltdfQ.A5dqXQzjBIJBjllQAQnPo3WrSWGpA2fX81F0jG0RdYmSiO_7pJn-V2LcskDHOg-R8YBCCSp0NUo8hQ_4VjFlOpj-R2REpGILyvG4reiFLn6YxFuHM7KGIi6SHTA5whNQjUpgbmA5yh0Y6WipjJs-C6L_UaCf2Rkah1LEoG1XRW8U-2jP5v8GYglh0wHFSxyflwOBL1I07pBReqxm_nptMxU6_ypO4SyW4i2Y4RMx0BIDLfkg17h0N1Ed9kBjLRCxI280ooQHBUhRPXxlE2W7guTSd5J3dDo9rJkzIF-XBbQUQxmbOtsyp0of2H559iXZGrQcr_ie0vJKuFN5kO0d7gZUS2LMF9ziZd0NC-2zrCzSnqvZkOpXwQQ5h2GXLG1pyZDEOEy1H9xQk1_UmrgJ75KxenM9HEG-huJ9bUGDrFqAUxr6fqxif68PRQdo2Eenm8oCFZAbyt9Q-gGjt6QNOYDii1MpvUSKzRuB03EtDnjElj2_usdV9qfh9jahA5XhazL7PDdezxas--HDsMSQx1yfIidXZnQjF17hgYr5jtKM3uGKk422bjM-6mzqn4vvB7UVgB6W7ZAQ7EBtQZWghWnurjXCe3QyjbwU70c4mMmHBEhLGIYmh7R_tD3N-fCPxb8OZAycBModkQjkflyfUl7WQqJosXt6qKtNuguNegw" )
 @POST ("details")
 @FormUrlEncoded
 Call<Details> details(@Field("phone") String phone) ;

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

}
