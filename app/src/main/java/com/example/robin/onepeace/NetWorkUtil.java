package com.example.robin.onepeace;

import android.os.Build;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import java.io.IOException;
import java.util.Collections;
import java.util.concurrent.TimeUnit;
import okhttp3.CipherSuite;
import okhttp3.ConnectionSpec;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.TlsVersion;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Utkarsh Shukla on 06/10/18.
 */
public class NetWorkUtil {

    private static final String BASE_URL = "https://onepiece";
    private static final long CONNECT_TIMEOUT_MILLIS = 30000;
    private static final long READ_TIMEOUT_MILLIS = 30000;
    private static OkHttpClient.Builder builder;
    private static HttpLoggingInterceptor httpLoggingInterceptor;
    private static Retrofit retrofit;

    public static void dispose() {
        if (retrofit != null) {
            retrofit = null;
            builder = null;
            httpLoggingInterceptor = null;
        }
    }

    public static Retrofit provideRetrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(getClient())
                    .build();
        }
        return retrofit;
    }

    private static OkHttpClient getClient() {

        okhttp3.OkHttpClient.Builder httpBuilder = getOkhttpClientBuilder();
        httpBuilder.connectTimeout(CONNECT_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);
        httpBuilder.readTimeout(READ_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);
        httpBuilder.connectionSpecs(Collections.singletonList(getSpec()));
        httpBuilder.addInterceptor(getHttpLoggingInterceptor());
        httpBuilder.addNetworkInterceptor(getHttpLoggingInterceptor());

        return httpBuilder.build();
    }

    private static ConnectionSpec getSpec() {
        return new ConnectionSpec.Builder(ConnectionSpec.COMPATIBLE_TLS)
                .supportsTlsExtensions(true)
                .tlsVersions(TlsVersion.TLS_1_2, TlsVersion.TLS_1_1, TlsVersion.TLS_1_0)
                .cipherSuites(
                        CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
                        CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
                        CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256,
                        CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA,
                        CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA,
                        CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA,
                        CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA,
                        CipherSuite.TLS_ECDHE_ECDSA_WITH_RC4_128_SHA,
                        CipherSuite.TLS_ECDHE_RSA_WITH_RC4_128_SHA,
                        CipherSuite.TLS_DHE_RSA_WITH_AES_128_CBC_SHA,
                        CipherSuite.TLS_DHE_DSS_WITH_AES_128_CBC_SHA,
                        CipherSuite.TLS_DHE_RSA_WITH_AES_256_CBC_SHA)
                .build();
    }


    private static Interceptor getHttpLoggingInterceptor() {
        if (httpLoggingInterceptor == null) {
            httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(BuildConfig.DEBUG ?
                    HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
        }
        return httpLoggingInterceptor;
    }

    private static OkHttpClient.Builder getOkhttpClientBuilder() {


        if (builder == null) {
            builder = new okhttp3.OkHttpClient.Builder();
            return builder;
        } else {
            return builder;
        }
    }
}
