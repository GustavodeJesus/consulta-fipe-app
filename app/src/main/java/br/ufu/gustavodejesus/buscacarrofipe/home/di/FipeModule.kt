package br.ufu.gustavodejesus.buscacarrofipe.home.di

import android.content.Context
import br.ufu.gustavodejesus.buscacarrofipe.home.data.remote.FipeApi
import br.ufu.gustavodejesus.buscacarrofipe.home.data.remote.client.OkHttpBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object FipeModule {

    @Provides
    @Singleton
    fun provideRetrofit(@ApplicationContext context: Context): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://BuscaF-Busca-ClGnjpK0bwl5-1751371759.sa-east-1.elb.amazonaws.com:8080/api/v1/fipe/")
            .client(OkHttpBuilder(context).build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideAPI(retrofit: Retrofit): FipeApi = retrofit.create()

}