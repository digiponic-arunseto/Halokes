package com.digiponic.halokes.Retrofit;

import com.digiponic.halokes.Models.ModelAssignment;
import com.digiponic.halokes.Models.ModelAttendance;
import com.digiponic.halokes.Models.ModelClass;
import com.digiponic.halokes.Models.ModelCounseling;
import com.digiponic.halokes.Models.ModelExtra;
import com.digiponic.halokes.Models.ModelGrade;
import com.digiponic.halokes.Models.ModelSchedule;
import com.digiponic.halokes.Models.ModelScheduleDetail;
import com.digiponic.halokes.Models.ModelStudent;
import com.digiponic.halokes.Models.ModelUser;
import com.digiponic.halokes.Models.StructureDefault;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
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

    @GET("tugas/{id_user}/")
    Call<ModelAssignment> showAssignment(@Path("id_user") String id_user);

    @FormUrlEncoded
    @POST("tugas/search/{id_user}")
    Call<ModelAssignment> showAssignmentDetail(
            @Path("id_user") String id_user,
            @Field("id_mapel") String id_mapel,
            @Field("keyword") String keyword,
            @Field("sort_date") String sort_date,
            @Field("sort_nama_tugas") String sort_nama_tugas
    );

    @GET("nilai/siswa/{id_user}")
    Call<ModelGrade> showGrade(@Path("id_user") String id_user);

    @GET("nilai/siswa/{id_user}/{id_mapel}")
    Call<ModelGrade> showGradeDetail(
            @Path("id_user") String id_user,
            @Path("id_mapel") String id_mapel);

//    @FormUrlEncoded
//    @PUT("siswa/{id_user}/edit/emailpass")
//    Call<StructureDefault> NavEditProfile(
//            @Path("id_user") String id_user,
//            @Field("email") String email,
//            @Field("password") String password
//    );

    @GET("jadwal/siswa/{id_user}")
    Call<ModelSchedule> showSchedule(@Path("id_user") String id_user);

    @GET("jadwal/siswa/{id_user}/{hari}")
    Call<ModelSchedule> showScheduleDetail(
            @Path("id_user") String id_user,
            @Path("hari") String hari);

    @GET("kelas/siswa/{id_user}")
    Call<ModelClass> showClass(@Path("id_user") String id_user);

    @GET("konseling/catatan/{id_user}")
    Call<ModelCounseling> showCounseling(@Path("id_user") String id_user);

    @GET("ekskul/siswa/{id_user}")
    Call<ModelExtra> showExtra(@Path("id_user") String id_user);

    @GET("ekskul/siswa/{id_user}/{ekskul_url}")
    Call<ModelExtra> showExtraDetail(
            @Path("id_user") String id_user,
            @Path("ekskul_url") String ekskul_url);

    @GET("ringkasan/siswa/{id_user}")
    Call<ModelStudent> showStudentBio(
            @Path("id_user") String id_user);

    @FormUrlEncoded
    @POST("tugas/validasi/")
    Call<StructureDefault> actAssignmentSubmit(
            @Field("siswa") String id_user,
            @Field("id_tugas") String tugas,
            @Field("status") int status
    );

    @Multipart
    @POST("bio/siswa/{id_user}")
    Call<StructureDefault> actUpdatePic(
            @Path("id_user") String id_user,
            @Part("image\"; filename=\"myfile.jpg\"") RequestBody file
    );

    @Multipart
    @POST("siswa/{id_user}/edit/emailpass")
    Call<ModelUser> actEditProfile(
            @Path("id_user") String id_user,
            @Part("email") RequestBody email,
            @Part("password") RequestBody password,
            @Part("image\"; filename=\"myfile.jpg\"") RequestBody foto
    );
}
