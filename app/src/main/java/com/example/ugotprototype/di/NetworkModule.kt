package com.example.ugotprototype.di

import android.util.Log
import com.example.ugotprototype.BuildConfig
import com.example.ugotprototype.MainActivity
import com.example.ugotprototype.data.api.ApiService
import com.example.ugotprototype.data.api.MessageService
import com.example.ugotprototype.data.api.OAuthService
import com.example.ugotprototype.data.api.SignService
import com.example.ugotprototype.data.api.TeamBuildingService
import com.example.ugotprototype.SharedPreference
import com.example.ugotprototype.data.api.ProfileService
import com.example.ugotprototype.ui.team.view.TeamFragment.Companion.TOKEN_DATA
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(authInterceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS).writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(authInterceptor).build()
    }

    @Provides
    @Singleton
    @GitRetrofit
    fun provideGitRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl(MainActivity.GITHUB_URL).client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    @Provides
    @Singleton
    fun provideBackEndRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl(BuildConfig.BASE_URL).client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    @Provides
    @Singleton
    fun provideGitApiService(@GitRetrofit retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideBackEndService(retrofit: Retrofit): TeamBuildingService {
        return retrofit.create(TeamBuildingService::class.java)
    }

    @Provides
    @Singleton
    fun provideSignService(retrofit: Retrofit): SignService {
        return retrofit.create(SignService::class.java)
    }

    @Provides
    @Singleton
    fun provideMessageService(retrofit: Retrofit): MessageService {
        return retrofit.create(MessageService::class.java)
    }

    @Provides
    @Singleton
    fun provideOAuthService(retrofit: Retrofit): OAuthService {
        return retrofit.create(OAuthService::class.java)
    }

    @Provides
    @Singleton
    fun provideProfileService(retrofit: Retrofit): ProfileService {
        return retrofit.create(ProfileService::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthInterceptor(sharedPreference: SharedPreference): Interceptor {
        return Interceptor { chain ->
            val originalRequest: Request = chain.request()
            val modifiedRequest: Request

            // 깃허브 관련 API는 모두 요청할 때 토큰값을 같이보냄
            if (originalRequest.url.toString().startsWith(MainActivity.GITHUB_URL)) {
                modifiedRequest =
                    originalRequest.newBuilder().header("Authorization", "Bearer $TOKEN_DATA")
                        .build()
                // 백엔드 관련 API일때 토큰값을 보내는경우는 아래조건과 같음
            } else if (originalRequest.url.toString()
                    .startsWith(BuildConfig.BASE_URL) && (!(originalRequest.url.toString()
                    .contains("auth/naver")) && (originalRequest.url.toString()
                    .contains("members") && originalRequest.method == ("DELETE")))
            ) {
                modifiedRequest = originalRequest.newBuilder()
                    .header("Authorization", "Bearer ${sharedPreference.getToken()}").build()
            } else {
                modifiedRequest = originalRequest
            }

            chain.proceed(modifiedRequest)
        }
    }
}