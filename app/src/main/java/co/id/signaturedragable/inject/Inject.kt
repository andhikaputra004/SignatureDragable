package co.id.signaturedragable.inject

import co.id.signaturedragable.BuildConfig
import co.id.signaturedragable.data.Repository
import co.id.signaturedragable.data.Service
import co.id.signaturedragable.ui.pdf.CreatePDFImageViewModel
import co.id.signaturedragable.ui.pdf.ListPDFViewModel
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.tom_roush.pdfbox.util.PDFBoxResourceLoader
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single {
        Moshi.Builder()
            .build()
    }
    single { Repository(get()) }
    single { PDFBoxResourceLoader.init(androidApplication()); }
    single {
        val builder = OkHttpClient.Builder()
            .readTimeout(60000, TimeUnit.MILLISECONDS)
        builder.addInterceptor(
            HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY)
        )
        builder.build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .client(get())
            .build()

        retrofit
    }
    single { get<Retrofit>().create(Service::class.java) }

}

val viewModelModule = module {
    viewModel { ListPDFViewModel(get()) }
    viewModel { CreatePDFImageViewModel(get(), androidApplication()) }

}