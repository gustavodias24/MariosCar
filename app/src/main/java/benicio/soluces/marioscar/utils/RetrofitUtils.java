package benicio.soluces.marioscar.utils;

import benicio.soluces.marioscar.services.ServiceIngur;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtils {

    public static Retrofit createRetrofitIngur(){
        return new Retrofit.Builder()
                .baseUrl("https://api.imgur.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static ServiceIngur createServiceIngur(Retrofit retrofit){
        return retrofit.create(ServiceIngur.class);
    }

}
