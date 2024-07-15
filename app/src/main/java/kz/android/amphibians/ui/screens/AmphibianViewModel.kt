package kz.android.amphibians.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.launch
import kz.android.amphibians.AmphibiansApplication
import kz.android.amphibians.data.AmphibiansRepository
import kz.android.amphibians.model.Amphibians
import retrofit2.HttpException
import java.io.IOException

sealed interface AmphibiansUiState {
    data class Success(val amphibians: List<Amphibians>) : AmphibiansUiState
    object Error : AmphibiansUiState
    object Loading : AmphibiansUiState
}

class AmphibianViewModel (private val amphibiansRepository: AmphibiansRepository) : ViewModel(){

    var amphibiansUiState: AmphibiansUiState by mutableStateOf(AmphibiansUiState.Loading)
        private set

    init {
        getAmphibians()
    }

    fun getAmphibians(){
        viewModelScope.launch {
            amphibiansUiState = AmphibiansUiState.Loading
            amphibiansUiState = try {
                AmphibiansUiState.Success(amphibiansRepository.getAmphibians())
            } catch (e: IOException){
                AmphibiansUiState.Error
            } catch (e: HttpException){
                AmphibiansUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                        as AmphibiansApplication)
                val amphibiansRepository = application.container.amphibiansRepository
                AmphibianViewModel(amphibiansRepository = amphibiansRepository)
            }
        }
    }
}