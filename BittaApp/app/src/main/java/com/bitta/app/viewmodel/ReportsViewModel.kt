package com.bitta.app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bitta.app.datasource.DataSource
import com.bitta.app.datasource.reports
import com.bitta.app.filter
import com.bitta.app.model.Report
import com.bitta.app.model.ReportKind

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
        val reports = _reports.value

        _loadingStatus.value = true

        if (dispenserId != null && reports != null) {
            _reports.value = reports.filter { it.dispenserId == dispenserId }
            _loadingStatus.value = false
        }
    }
}