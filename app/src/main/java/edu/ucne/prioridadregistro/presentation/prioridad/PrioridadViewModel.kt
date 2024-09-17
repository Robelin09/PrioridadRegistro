package edu.ucne.prioridadregistro.presentation.prioridad

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.prioridadregistro.data.local.entities.PrioridadEntity
import edu.ucne.prioridadregistro.data.repository.PrioridadRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PrioridadViewModel @Inject constructor(
    private val prioridadRepository: PrioridadRepository
) : ViewModel(){
    private val _uiState = MutableStateFlow(Uistate())
    val uiState = _uiState.asStateFlow()

    init {
        getPrioridades()
    }

    fun save(){
        viewModelScope.launch{
            if (_uiState.value.descripcion.isNullOrBlank() || _uiState.value.diasCompromiso <= 0){
                _uiState.update {
                    it.copy(errorMessage = "La Descripcion no puede ser vacia")
                }
            }
            else{
                prioridadRepository.save(_uiState.value.toEntity())
                nuevo()
            }
        }
    }
    fun nuevo(){
        _uiState.update{
            it.copy(
                prioridadId=null,
                descripcion="",
                diasCompromiso = 0,
                errorMessage = null
            )
        }
    }
    fun select(prioridadId:Int){
        viewModelScope.launch{
            if (prioridadId > 0){
                val prioridad = prioridadRepository.getPrioridad(prioridadId)
                _uiState.update{
                    it.copy(
                        prioridadId = prioridad?.prioridadId,
                        descripcion = prioridad?.descripcion?: "",
                        diasCompromiso = prioridad?.diasCompromiso?: 0

                    )
                }
            }
        }
    }
    fun onDescripcionChange(descripcion: String) {
        _uiState.update {
            it.copy(descripcion = descripcion)
        }
    }
    fun onDiasCompromisoChange(diasCompromiso: Int) {
        _uiState.update {
            it.copy(diasCompromiso = diasCompromiso)
        }
    }
    fun delete () {
        viewModelScope.launch{
            prioridadRepository.delete(_uiState.value.toEntity())
        }
    }
    private fun getPrioridades(){
        viewModelScope.launch{
            prioridadRepository.getPrioridades().collect {prioridad ->
                _uiState.update{
                    it.copy(prioridades = prioridad)
                }
            }
        }
    }
}
data class Uistate(
    val prioridadId : Int? = null,
    val descripcion : String = "",
    val diasCompromiso : Int = 0,
    val errorMessage : String? = null,
    val prioridades : List<PrioridadEntity> = emptyList()
)
fun Uistate.toEntity() = PrioridadEntity(
    prioridadId = prioridadId,
    descripcion = descripcion,
    diasCompromiso = diasCompromiso
)

