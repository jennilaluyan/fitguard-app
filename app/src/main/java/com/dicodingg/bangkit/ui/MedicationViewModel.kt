package com.dicodingg.bangkit.ui.medication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MedicationViewModel : ViewModel() {
    private val _medications = MutableLiveData<List<Medication>>(mutableListOf())
    val medications: LiveData<List<Medication>> = _medications

    fun addMedication(medication: Medication) {
        val currentList = _medications.value?.toMutableList() ?: mutableListOf()
        currentList.add(medication)
        _medications.value = currentList  // This will trigger UI update
    }

    fun deleteMedication(medication: Medication) {
        val currentList = _medications.value?.toMutableList() ?: mutableListOf()
        currentList.remove(medication)
        _medications.value = currentList
    }
}
