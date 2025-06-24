package com.example.yanadenv.data.remote;


import com.example.yanadenv.data.model.Assignee;
import com.example.yanadenv.data.model.Campain;
import com.example.yanadenv.data.model.Clinicgroup;
import com.example.yanadenv.data.model.CodigoToken;
import com.example.yanadenv.data.model.Participant;
import com.example.yanadenv.data.model.Post;
import com.example.yanadenv.data.model.Project;

import com.example.yanadenv.data.model.Readgroup;
import com.example.yanadenv.data.model.Socioeconomico;
import com.example.yanadenv.data.model.Token;
import com.example.yanadenv.data.model.campain.Campain2;
import com.example.yanadenv.data.model.editar.Clinicgroup2;
import com.example.yanadenv.data.model.projectFull.Project3;
import com.example.yanadenv.data.model.readGroup.Readgroup2;
import com.example.yanadenv.data.model.readGroupFull.ReadgroupFull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface APIService {


    @POST("participant/full")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<Void> savePost(@Header("Authorization") String token,@Body Post post);



   // @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("auth")
    Call<CodigoToken> urlToken(@Body Token token);

 //  @GET("participant")
  // Call<DatosParticipantes> participantData(@Query("draw") int draw, @Query("start") int start,
   //                                         @Query("length") int length);

    @GET("readgroup/fullp/{participantId}")
     Call<List<Readgroup>>  readGroupData(@Path("participantId") String participantId );


    @GET("readgroup")
    Call<ReadgroupFull>  readGroupIdFull( @Header("Authorization") String token, @Query("draw")  int draw,
                                         @Query("start") int start,
                                         @Query("length") int length,@Query("projectId") String id );

    @GET("readgroup/full/{id}")
    Call<Readgroup2>  readGroupFull(@Header("Authorization") String token,@Path("id") String id);



    @GET("project")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
     Call<Project3> getProject(@Header("Authorization") String token , @Query("draw") int draw, @Query("start") int start,
                               @Query("length") int length);


    @GET("clinicgroup/full/{id}")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<Project> clinicgroup(@Header("Authorization") String token ,@Path("id") String id);

    @GET("clinicgroup")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<Clinicgroup> getClicgroupFull(@Header("Authorization") String token,@Query("draw")  int draw,
                                       @Query("start") int start,
                                       @Query("length") int length);


    @GET("participant/{id}")
    //@Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<Participant> getParticipante(
            @Header("Authorization") String token,
            @Path("id") String id
    );

 //Call<Void> savePost(@Header("Authorization") String token,@Body Post post);



 @GET("campain/{id}")
    Call<Campain> getCampain(@Path("id")  String id);

   @GET("campain")
   @Headers({ "Content-Type: application/json;charset=UTF-8"})
   Call<Campain2> getCampaiFull(@Header("Authorization") String token,@Query("draw") int draw, @Query("start") int start,
                                @Query("length") int length);

    @GET("socieconomic/participant/{id}")
    Call<Socioeconomico> getSocioeconomico(@Path("id")  String id);

    @GET("assignee/{id}")
    Call<Assignee> getAssignee(@Path("id")  String id);

    @GET("clinicgroup/fullp/{participantId}")
    Call<ArrayList<Clinicgroup2>> getClinicos(@Path("participantId")  String participantId);


    @PUT("participant/{id}")
    Call<Void> updateParticipante( @Path("id")  String id ,
                                   @Body Participant participant);


    @PUT("socieconomic/{id}")
    Call<Void> updateSocioeconomico( @Path("id")  String id ,
                                   @Body Socioeconomico socioeconomico);

   @PUT("assignee/{id}")
    Call<Void> updateAssigneParicipante( @Path("id")  String id ,
                                  @Body Assignee assigne);

   @PUT("clinicgroup/{id}")
    Call<Void> updateClinicos( @Path("id")  String id ,
                                  @Body Clinicgroup2 clinicgroup);



// @PUT("socieconomic/{id}")
// Call<Void> updateSocioeconomic( @Path("id")  String id ,
//                                      @Body Socioeconomico assigne);


    //clinicgroup/full
    @POST("readdata/upload")
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    Call<Void> editProfile(@Query("readvalues") String readvalues,
                           @Query("participantId") String participantId ,
                           @Query("file") Object file);


}