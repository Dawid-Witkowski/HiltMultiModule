package winged.example.hiltmultimodule.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import winged.example.core_data.di.DatabaseModule

@Module(includes = [DatabaseModule::class])
@InstallIn(SingletonComponent::class)
object AppModule {
}