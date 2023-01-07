package com.bitta.app.viewmodel

import androidx.lifecycle.*
import com.bitta.app.datasource.DataSource
import com.bitta.app.datasource.reports
import com.bitta.app.model.Report
import com.bitta.app.model.ReportKind
import com.bitta.app.utils.DELAY_FAKE_LOADING_TIME
import com.bitta.app.utils.filter
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ReportsViewModel : ViewModel() {
    private val _loadingStatus = MutableLiveData(true)
    private val _dispenserId = MutableLiveData(0)
    private val _reports = MediatorLiveData<List<Report>>()

    val loadingStatus: LiveData<Boolean> = _loadingStatus
    val issues = _reports.filter {
        it.kind in listOf(
            ReportKind.TECHNICAL_REPORT,
            ReportKind.AUTOMATIC_REPORT
        )
    }
    val brokenReports = _reports.filter { it.kind == ReportKind.TECHNICAL_ACTION }
    val reports = _reports.filter { it.kind == ReportKind.USER_REPORT }

    init {
        _loadingStatus.value = true
        _reports.addSource(_dispenserId) { onReportsChange() }
        _reports.addSource(DataSource.reports) { onReportsChange() }
    }

    fun loadReportsForDispenser(dispenserId: Int) {
        _dispenserId.value = dispenserId
    }

    private fun onReportsChange() {
        val dispenserId = _dispenserId.value
        val reports = DataSource.reports.value

        _loadingStatus.value = true

        if (dispenserId != null && reports != null) {
            viewModelScope.launch {
                delay(DELAY_FAKE_LOADING_TIME)
                if (_loadingStatus.value == true) {
                    // Still loading, update value
                    _reports.value = reports.filter { it.dispenserId == dispenserId }
                    _loadingStatus.value = false
                }
            }
        }
    }
}