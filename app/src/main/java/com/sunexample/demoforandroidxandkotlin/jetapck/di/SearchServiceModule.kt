package com.sunexample.demoforandroidxandkotlin.jetapck.di
import com.sunexample.demoforandroidxandkotlin.jetapck.api.SearchService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Qualifier


@InstallIn(ActivityRetainedComponent::class)
@Module
class SearchServiceModule {

    val ApiBaseUrl = "https://www.youtube.com/"
    val NextbaseUrl = "https://www.youtube.com/youtubei/v1/"

    @Provides
    @BindOneService
    fun create(): SearchService {
        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BASIC

        val client = OkHttpClient.Builder()
            .addInterceptor(logger)
            .build()
        return Retrofit.Builder()
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .baseUrl(ApiBaseUrl)
            .client(client)
            .build()
            .create(SearchService::class.java)
    }

    @Provides
    @BindTwoService
    fun createnextpager(): SearchService {
        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BASIC

        val client = OkHttpClient.Builder()
            .addInterceptor(logger)
            .build()
        return Retrofit.Builder()
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .baseUrl(NextbaseUrl)
            .client(client)
            .build()
            .create(SearchService::class.java)
    }

}



@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BindOneService

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BindTwoService

