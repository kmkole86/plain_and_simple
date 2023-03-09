package com.kmkole86.remote.di

import android.util.Log
import com.kmkole86.remote.BuildConfig
import com.kmkole86.remote.utils.Constants.HTTP_TIME_OUT
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.features.observer.*
import io.ktor.client.request.*
import io.ktor.http.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteModule {

    @Singleton
    @Provides
    fun provideHttpClient(): HttpClient = HttpClient(Android) {

        engine {
            connectTimeout = HTTP_TIME_OUT
            socketTimeout = HTTP_TIME_OUT
        }

        install(JsonFeature) {
            serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }

        install(DefaultRequest) {
            url {
//                parameters.append("api_key", BuildConfig.API_KEY)
                parameters.append("api_key", "a794ee27f47722d30bc1c67e3df3522a")
            }
            header(HttpHeaders.ContentType, ContentType.Application.Json)
        }

        if (BuildConfig.DEBUG) {
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Log.e("http_log", "response: ${message}")
                        //TODO: implement
                    }
                }
                level = LogLevel.ALL
            }
        }


        install(ResponseObserver) {
            onResponse { response ->
                Log.e("http_log", "response: ${response}")
                //TODO: implement
            }
        }
    }
}