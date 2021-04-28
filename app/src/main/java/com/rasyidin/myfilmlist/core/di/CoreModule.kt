package com.rasyidin.myfilmlist.core.di

import com.rasyidin.myfilmlist.BuildConfig.BASE_URL
import com.rasyidin.myfilmlist.core.data.repository.MoviesRepository
import com.rasyidin.myfilmlist.core.data.repository.TvShowRepository
import com.rasyidin.myfilmlist.core.data.source.remote.MoviesRemoteDataSource
import com.rasyidin.myfilmlist.core.data.source.remote.TvShowRemoteDataSource
import com.rasyidin.myfilmlist.core.data.source.remote.network.MoviesApiService
import com.rasyidin.myfilmlist.core.data.source.remote.network.TvApiService
import com.rasyidin.myfilmlist.core.domain.repository.IMoviesRepository
import com.rasyidin.myfilmlist.core.domain.repository.ITvShowRepository
import com.rasyidin.myfilmlist.core.utils.Constants.REQUEST_TIMEOUT
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val repositoryModule = module {
    single { MoviesRemoteDataSource(get()) }
    single { TvShowRemoteDataSource(get()) }
    single<IMoviesRepository> { MoviesRepository(get()) }
    single<ITvShowRepository> { TvShowRepository(get()) }
}

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
            .build()
    }

    single {
        val movieInstance = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        movieInstance.create(MoviesApiService::class.java)
    }

    single {
        val tvInstance = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        tvInstance.create(TvApiService::class.java)
    }
}

