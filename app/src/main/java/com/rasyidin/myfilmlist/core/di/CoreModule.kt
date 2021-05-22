package com.rasyidin.myfilmlist.core.di

import androidx.room.Room
import com.rasyidin.myfilmlist.BuildConfig.BASE_URL
import com.rasyidin.myfilmlist.core.data.repository.MoviesRepository
import com.rasyidin.myfilmlist.core.data.repository.TvShowRepository
import com.rasyidin.myfilmlist.core.data.source.local.MovieLocalDataSource
import com.rasyidin.myfilmlist.core.data.source.local.TvLocalDataSource
import com.rasyidin.myfilmlist.core.data.source.local.room.MyFilmDatabase
import com.rasyidin.myfilmlist.core.data.source.remote.MoviesRemoteDataSource
import com.rasyidin.myfilmlist.core.data.source.remote.TvShowRemoteDataSource
import com.rasyidin.myfilmlist.core.data.source.remote.network.MoviesApiService
import com.rasyidin.myfilmlist.core.data.source.remote.network.TvApiService
import com.rasyidin.myfilmlist.core.domain.repository.IMoviesRepository
import com.rasyidin.myfilmlist.core.domain.repository.ITvShowRepository
import com.rasyidin.myfilmlist.core.utils.Constants.DATABASE_NAME
import com.rasyidin.myfilmlist.core.utils.Constants.REQUEST_TIMEOUT
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val repositoryModule = module {
    factory { CoroutineScope((Dispatchers.IO)) }
    single { MoviesRemoteDataSource(get(), get()) }
    single { TvShowRemoteDataSource(get(), get()) }
    single { MovieLocalDataSource(get()) }
    single { TvLocalDataSource(get()) }
    single<IMoviesRepository> { MoviesRepository(get(), get()) }
    single<ITvShowRepository> { TvShowRepository(get(), get()) }
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

val databaseModule = module {
    factory { get<MyFilmDatabase>().getMovieDao() }
    factory { get<MyFilmDatabase>().getTvShowDao() }
    single {
        Room.databaseBuilder(
            androidContext(),
            MyFilmDatabase::class.java,
            DATABASE_NAME
        ).build()
    }
}

