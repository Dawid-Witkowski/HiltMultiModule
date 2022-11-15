package winged.example.core_data.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import winged.example.core_data.db.NameDatabase
import winged.example.core_data.db.repository.UserRepository
import winged.example.core_data.utils.Constants
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideNameDatabase(application: Application): NameDatabase {
        return Room
            .databaseBuilder(
                application.applicationContext,
                NameDatabase::class.java,
                Constants.APP_DATABASE
            )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideNameDao(database: NameDatabase) = database.getNameDao()

    @Provides
    fun provideNameRepository(database: NameDatabase) = UserRepository(database)
}