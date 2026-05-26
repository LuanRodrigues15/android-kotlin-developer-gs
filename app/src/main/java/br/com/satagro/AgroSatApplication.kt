package br.com.satagro

import android.app.Application
import br.com.satagro.di.dataModule
import br.com.satagro.di.domainModule
import br.com.satagro.di.networkModule
import br.com.satagro.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AgroSatApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@AgroSatApplication)
            modules(
                networkModule,
                dataModule,
                domainModule,
                presentationModule
            )
        }
    }
}
