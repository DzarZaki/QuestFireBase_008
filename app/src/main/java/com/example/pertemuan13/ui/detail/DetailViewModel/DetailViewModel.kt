package com.example.pertemuan13.ui.detail.DetailViewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pertemuan13.Navigasi.DestinasiDetail
import com.example.pertemuan13.model.Mahasiswa
import com.example.pertemuan13.repository.RepositoryMhs
import com.example.pertemuan13.ui.insert.viewmodel.MahasiswaEvent
import com.example.pertemuan13.ui.insert.viewmodel.toMhsModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DetailViewModel (
    savedStateHandle: SavedStateHandle,
    private val repositoryMhs: RepositoryMhs,
) : ViewModel() {
    private val _nim: String = checkNotNull(savedStateHandle[DestinasiDetail.route])

    val detailUiState: StateFlow<DetailUiState> = repositoryMhs.getMhs(_nim)
        .filterNotNull()
        .map {

            DetailUiState(
                detailUiEvent = it.toDetailUiEvent(),
                isLoading = false,
            )
        }
        .onStart {
            emit(DetailUiState(isLoading = true))
            delay(600)
        }
        .catch {

            emit(
                DetailUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = it.message ?: "Terjadi Kesalahan",
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(2000),
            initialValue = DetailUiState(
                isLoading = true,
            ),
        )

    fun deleteMhs() {
        val currentEvent = detailUiState.value.detailUiEvent
        if (currentEvent != MahasiswaEvent()) {
            viewModelScope.launch {

                repositoryMhs.deleteMhs(currentEvent.toMhsModel())
            }
        }
    }
}

data class DetailUiState(
    val detailUiEvent: MahasiswaEvent = MahasiswaEvent(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = "",
) {
    val isUiEventEmpty: Boolean
        get() = detailUiEvent == MahasiswaEvent()
    val isUiEventNotEmpty: Boolean
        get() = detailUiEvent != MahasiswaEvent()
}

fun Mahasiswa.toDetailUiEvent(): MahasiswaEvent {
    return MahasiswaEvent(
        nama = nama,
        nim = nim,
        jenisKelamin = jeniskelamin,
        alamat = alamat,
        kelas = kelas,
        angkatan = angkatan,

    )
}