package com.example.whyhmm.domain.di

import android.content.Context
import com.example.whyhmm.data.RemoteDataSource
import com.example.whyhmm.data.apicall.UserApi
import com.example.whyhmm.domain.utils.CShowProgress
import com.example.whyhmm.domain.utils.SessionManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun providesAuthApi(remoteDataSource: RemoteDataSource): UserApi {
        return remoteDataSource.buildApi(UserApi::class.java)
    }

//    @Provides
//    fun providesAuthApiFieldOn(remoteDataSource: RemoteDataSourceFieldOn): FieldonApi {
//        return remoteDataSource.buildApi(FieldonApi::class.java)
//    }


    @Provides
    fun provideSharedPrefs(@ApplicationContext context: Context): SessionManager {
        return SessionManager(context)
    }

    @Provides
    fun provideProgressDialog() : CShowProgress {
        return CShowProgress()
    }

//    @Provides
//    fun provideCheckInternet() : CheckInternet {
//        return CheckInternet()
//    }

//    @Provides
//    fun provideNetworkUtil(@ApplicationContext context: Context) : NetworkUtil {
//        return NetworkUtil(context)
//    }
//
//    @Provides
//    fun provideFirebaseMessaging() : MyFirebaseMessagingService {
//        return MyFirebaseMessagingService()
//    }
}