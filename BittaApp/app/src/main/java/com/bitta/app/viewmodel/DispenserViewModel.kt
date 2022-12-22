package com.bitta.app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitta.app.DELAY_FAKE_LOADING_TIME
import com.bitta.app.datasource.DataSource
import com.bitta.app.datasource.dispensers
import com.bitta.app.datasource.reports
import com.bitta.app.model.DispenserWithStatus
import com.bitta.app.model.ReportKind
import com.bitta.app.model.WorkingStatus
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DispenserViewModel : ViewModel() {
    private val _dispensers = MediatorLiveData<List<DispenserWithStatus>>()
    val dispensers = _dispensers as LiveData<List<DispenserWithStatus>>

    init {
        viewModelScope.launch {
            delay(DELAY_FAKE_LOADING_TIME * 2)
            _dispensers.addSource(DataSource.reports) { allReports ->
                _dispensers.value = DataSource.dispensers.map { dispenser ->
                    val dispenserReports = allReports.filter { it.dispenserId == dispenser.id }
                    val workingStatus = if (dispenserReports.isEmpty())
                        WorkingStatus.OK
                    else if (dispenserReports.firstOrNull { it.kind == ReportKind.TECHNICAL_ACTION } != null)
                        WorkingStatus.NOT_WORKING
                    else
                        WorkingStatus.WITH_REPORTS
                    DispenserWithStatus(dispenser, workingStatus)
                }
            }
        }
    }
}