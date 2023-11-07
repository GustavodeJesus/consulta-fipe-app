package br.ufu.gustavodejesus.buscacarrofipe

import android.support.multidex.MultiDexApplication
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BuscaFipeApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
    }
}
