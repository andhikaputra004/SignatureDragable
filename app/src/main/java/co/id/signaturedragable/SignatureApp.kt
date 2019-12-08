package co.id.signaturedragable

import android.app.Application
import co.id.signaturedragable.inject.networkModule
import co.id.signaturedragable.inject.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin

class SignatureApp : Application(){

    lateinit var koinApplication: KoinApplication

    override fun onCreate() {
        super.onCreate()
        koinApplication = startKoin {
            androidContext(this@SignatureApp)
            modules(
                networkModule, viewModelModule
            )
        }
    }
}