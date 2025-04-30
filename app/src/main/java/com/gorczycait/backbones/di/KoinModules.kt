package com.gorczycait.backbones.di

import androidx.sqlite.db.SupportSQLiteDatabase
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.gorczycait.backbones.Database
import com.gorczycait.backbones.presentation.main.MainViewModel
import com.gorczycait.backbones.presentation.video.VideoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    // viewModels
    viewModel { MainViewModel(get()) }
    viewModel { VideoViewModel(get(), get()) }
}

val databaseModule = module {
    factory {
        Database(
            driver = AndroidSqliteDriver(
                schema = Database.Schema,
                context = get(),
                name = "Video.db",
                callback = object : AndroidSqliteDriver.Callback(Database.Schema) {
                    override fun onOpen(db: SupportSQLiteDatabase) {
                        db.setForeignKeyConstraintsEnabled(
                            true,
                        ) // enabling foreign key constraints for the Android SQLite driver.
                    }
                },
            ),
        )
    }
}
