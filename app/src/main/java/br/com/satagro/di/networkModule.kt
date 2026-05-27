package br.com.satagro.di

import br.com.satagro.data.remote.api.OpenMeteoApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

val networkModule = module {

    single {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()
    }

    single {
        Json { ignoreUnknownKeys = true }
    }

    single {
        Retrofit.Builder()
            .baseUrl("https://api.open-meteo.com/v1/")
            .client(get())
            .addConverterFactory(
                get<Json>().asConverterFactory("application/json".toMediaType())
            )
            .build()
    }

    single<OpenMeteoApi> {
        get<Retrofit>().create(OpenMeteoApi::class.java)
    }
}
