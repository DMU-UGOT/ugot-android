package com.example.ugotprototype.di

import android.util.Log
import com.example.ugotprototype.BuildConfig
import com.example.ugotprototype.MainActivity
import com.example.ugotprototype.SharedPreference
import com.example.ugotprototype.data.api.*
import com.example.ugotprototype.di.api.CommunityService
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
    fun provideStudyService(retrofit: Retrofit): StudyService {
        return retrofit.create(StudyService::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthInterceptor(sharedPreference: SharedPreference): Interceptor {
        return Interceptor { chain ->
            val originalRequest: Request = chain.request()
            val modifiedRequest: Request

            if (originalRequest.url.toString().startsWith(MainActivity.GITHUB_URL)) {
                modifiedRequest =
                    originalRequest.newBuilder().header("Authorization", "Bearer $TOKEN_DATA")
                        .build()
            } else if (originalRequest.url.toString()
                    .startsWith(BuildConfig.BASE_URL) && (!(originalRequest.url.toString()
                    .contains("auth/naver")) && !((originalRequest.url.toString()
                    .contains("members")) && (originalRequest.method == ("POST"))))
            ) {
                modifiedRequest = originalRequest.newBuilder()
                    .header("Authorization", "Bearer ${sharedPreference.getToken()}").build()
            } else {
                modifiedRequest = originalRequest
            }


            Log.d("test", modifiedRequest.toString())
            chain.proceed(modifiedRequest)
        }
    }

    @Provides
    @Singleton
    fun provideCommunityService(retrofit: Retrofit): CommunityService {
        return retrofit.create(CommunityService::class.java)
    }

    @Provides
    @Singleton
    fun provideCommunityCommentService(retrofit: Retrofit): CommentService {
        return retrofit.create(CommentService::class.java)
    }

    @Provides
    @Singleton
    fun provideGroupService(retrofit: Retrofit): GroupService {
        return retrofit.create(GroupService::class.java)
    }
}