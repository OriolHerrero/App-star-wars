package com.starwars.app.dagger

import android.app.Application
import com.starwars.app.business.SWBusiness
import com.starwars.app.business.SWBusinessImpl
import com.starwars.app.rest.ApiRepositorySW
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class RetrofitModule(private val application: Application) {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://www.swapi.tech/api/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providesApiRepositorySW(retrofit: Retrofit): ApiRepositorySW {
        return retrofit.create(ApiRepositorySW::class.java)
    }

    @Provides
    fun providesSWBusiness(apiRepositorySW: ApiRepositorySW): SWBusiness {
        return SWBusinessImpl(apiRepositorySW)
    }

    @Provides
    @Singleton
    fun providesApplication(): Application = application
}