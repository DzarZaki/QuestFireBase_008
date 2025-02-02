package com.example.pertemuan13.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.pertemuan13.MahasiswaApp
import com.example.pertemuan13.ui.detail.DetailViewModel.DetailViewModel
import com.example.pertemuan13.ui.home.viewmodel.HomeViewModel
import com.example.pertemuan13.ui.insert.viewmodel.InsertViewModel

object PenyediaViewModel{
    val Factory = viewModelFactory {
        initializer {
            HomeViewModel(
                MahasiswaApp().container.repositoryMhs
            )
        }
        initializer {
            InsertViewModel(
                MahasiswaApp().container.repositoryMhs
            )
        }
        /*initializer {
            DetailViewModel(
                MahasiswaApp().container.repositoryMhs
            )
        }*/
    }
}

fun CreationExtras.MahasiswaApp(): MahasiswaApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MahasiswaApp)