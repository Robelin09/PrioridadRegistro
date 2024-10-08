package edu.ucne.prioridadregistro.di

import edu.ucne.prioridadregistro.data.local.database.PrioridadDb
import android.content.Context
import androidx.room.Room
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import edu.ucne.prioridadregistro.data.remote.PrioridadApi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    @Provides
    @Singleton
    fun providePrioridadDb(@ApplicationContext appContext: Context) =
        Room.databaseBuilder(
            appContext,
            PrioridadDb::class.java,
            "Prioridad.db"
        ).fallbackToDestructiveMigration()
            .build()


    const val BASE_URL = "https://aplicada2api-cehkeaaug2b2d2h9.canadacentral-01.azurewebsites.net/"

    @Singleton
    @Provides
    fun providesMoshi(): Moshi =
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .add(DateAdater())
            .build()

    @Provides
    @Singleton
    fun providePrioridadApi(moshi:Moshi): PrioridadApi{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(PrioridadApi::class.java)
    }


    @Provides
    @Singleton
    fun providePrioridadDao(prioridadDb: PrioridadDb) = prioridadDb.prioridadDao()

    @Provides
    @Singleton
    fun provideTicketDao(prioridadDb: PrioridadDb) = prioridadDb.ticketDao()

}