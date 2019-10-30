package com.digiponic.halokes.Retrofit;

import com.digiponic.halokes.Models.ModelAttendance;
import com.digiponic.halokes.Models.ModelUser;
import com.digiponic.halokes.Models.StructureDefault;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Arunstop on 23-May-19.
 */

public interface WebApi {

    //LOGIN
    @FormUrlEncoded
    @POST("auth/login")
    Call<ModelUser> actLogin(
            @Field("username") String email,
            @Field("password") String password
    );

    @GET("presensi/siswa/{id_user}")
    Call<ModelAttendance> showAttendance(@Path("id_user") String id_user);
//    //LISTING JENIS
//    @GET("kendaraan/jenis")
//    Call<ModelJenis> showJenis();

//    @GET("reservasi/pelanggan/{id_pelanggan}/{status}")
//    Call<ModelBooking> showBooking(@Path("id_pelanggan") int id_pelanggan, @Path("status") String status);

}
