package com.bitta.app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitta.app.DELAY_FAKE_LOADING_TIME
import com.bitta.app.datasource.DataSource
import com.bitta.app.datasource.reportsByDispenser
import com.bitta.app.filter
import com.bitta.app.model.Report
import com.bitta.app.model.ReportKind
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ReportsViewModel : ViewModel() {
    private val _loadingStatus = MutableLiveData(true)
    private val _reports = MutableLiveData<List<Report>>()

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
    }

    fun loadReportsForDispenser(dispenserId: Int) {
        val actualReports = _reports.value.orEmpty()
        if (actualReports.firstOrNull()?.dispenserId != dispenserId) {
            _loadingStatus.value = true
            viewModelScope.launch {
                delay(DELAY_FAKE_LOADING_TIME)
                _reports.postValue(DataSource.reportsByDispenser(dispenserId))
                _loadingStatus.postValue(false)
            }
        }
    }
}