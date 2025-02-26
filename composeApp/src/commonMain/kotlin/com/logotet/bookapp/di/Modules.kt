package com.logotet.bookapp.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.logotet.bookapp.book.data.DefaultBookRepository
import com.logotet.bookapp.book.data.local.BookDatabase
import com.logotet.bookapp.book.data.local.DatabaseFactory
import com.logotet.bookapp.book.data.local.RoomLocalBookDataSource
import com.logotet.bookapp.book.data.network.HttpClientFactory
import com.logotet.bookapp.book.data.network.KtorRemoteBookDataSource
import com.logotet.bookapp.book.data.network.RemoteBookDataSource
import com.logotet.bookapp.book.domain.BookRepository
import com.logotet.bookapp.book.presentation.details.BookDetailsViewModel
import com.logotet.bookapp.book.presentation.list.BookListViewModel
import io.ktor.client.HttpClient
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule: Module

val sharedModule = module {
    single<HttpClient> { HttpClientFactory.create(get()) }

    singleOf(::KtorRemoteBookDataSource).bind<RemoteBookDataSource>()

    single {
        get<DatabaseFactory>().create()
            .setDriver(BundledSQLiteDriver())
            .build()
    }
    single { get<BookDatabase>().bookDao }

    single {
        RoomLocalBookDataSource(get())
    }

    viewModelOf(::BookListViewModel)
    viewModelOf(::BookDetailsViewModel)
    singleOf(::DefaultBookRepository).bind<BookRepository>()
}
