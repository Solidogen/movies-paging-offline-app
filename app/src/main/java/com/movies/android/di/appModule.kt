package com.movies.android.di

import androidx.room.Room
import com.movies.android.BuildConfig
import com.movies.android.data.api.MovieApi
import com.movies.android.data.database.MovieDatabase
import com.movies.android.data.repository.MovieRepository
import com.movies.android.ui.movies.popularmovies.PopularMoviesViewModel
import com.movies.android.util.Injection
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.MainScope
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

val appModule = module {
    single(named(Injection.NonCancellableScope)) { MainScope() }
    single {
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        }
    }
    single<Moshi> { Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build() }
    single<Converter.Factory> { MoshiConverterFactory.create(get()) }
    single<Retrofit.Builder> {
        Retrofit.Builder()
            .baseUrl("https://test.com")
            .addConverterFactory(get())
    }
    single {
        OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()
    }
    single<Retrofit> {
        get<Retrofit.Builder>()
            .client(get())
            .build()
    }
    single<MovieApi> { get<Retrofit>().create(MovieApi::class.java) }
    single {
        Room.databaseBuilder(androidContext(), MovieDatabase::class.java, "movie-database")
            .fallbackToDestructiveMigration()
            .build()
    }
    single { get<MovieDatabase>().movieDao() }
    single {
        MovieRepository(
            nonCancellableScope = get(
                named(Injection.NonCancellableScope)
            ),
            movieDao = get(),
            movieApi = get()
        )
    }
    viewModel { PopularMoviesViewModel(movieRepository = get()) }
}