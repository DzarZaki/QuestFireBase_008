package com.example.pertemuan13

import android.app.Application
import com.example.pertemuan13.di.MahasiswaContainer

class MahasiswaApp: Application(){
    lateinit var container: MahasiswaContainer
    override fun onCreate() {
        super.onCreate()
        container= MahasiswaContainer()
    }
}