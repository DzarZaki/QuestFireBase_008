package com.example.pertemuan13.ui.home.pages

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pertemuan13.ui.PenyediaViewModel
import com.example.pertemuan13.ui.home.viewmodel.FormState
import com.example.pertemuan13.ui.home.viewmodel.InsertViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.reflect.Modifier

@Composable
fun InsertMhsView(
    onBack: () -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier(),
    viewModel: InsertViewModel = viewModel(factory =
    PenyediaViewModel.Factory)
) {
    val uiState = viewModel.uiState // State utama untuk loading, success, error
    val uiEvent = viewModel.uiEvent // State untuk form dan validasi
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

// Observasi perubahan state untuk snackbar dan navigasi
    LaunchedEffect(uiState) {
        when (uiState) {
            is FormState.Success -> {
                println("InsertMhsView: uiState is FormState.Success, navigate to home " + uiState.message)
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(uiState.message) // Tampilkan snackbar
                }
                delay(700)
                // Navigasi langsung
                onNavigate()
                viewModel.resetSnackBarMessage() // Reset snackbar state
            }
            is FormState.Error -> {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(uiState.message)
                }
            }
            else -> Unit
        }
    }
}