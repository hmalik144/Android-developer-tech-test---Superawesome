package com.example.h_mal.superawesomefilereader

import android.app.Application
import com.example.h_mal.superawesomefilereader.db.AppDatabase
import com.example.h_mal.superawesomefilereader.repository.Repo
import com.example.h_mal.superawesomefilereader.ui.viewmodel.MainViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton


class AppClass : Application(), KodeinAware{


    override val kodein
        = Kodein.lazy{ import(androidXModule(this@AppClass))

        bind() from provider {
            MainViewModelFactory(
                instance()
            )
        }
        bind() from singleton {
            Repo(
                instance(),
                instance()
            )
        }
        bind() from singleton { AppDatabase(instance()) }

    }
}