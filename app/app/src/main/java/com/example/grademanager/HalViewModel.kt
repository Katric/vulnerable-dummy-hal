package com.example.grademanager

import android.hardware.grademanager.IGradeManager
import android.os.IBinder
import android.os.RemoteException
import android.os.ServiceManager
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HalViewModel : ViewModel() {
    private val TAG = "GradeManagerApp"
    private val HAL_SERVICE_NAME = "android.hardware.grademanager.IGradeManager/default"

    private var gradeManagerHal: IGradeManager? = null

    private val _halStatus = MutableStateFlow("HAL: Connecting...")
    val halStatus = _halStatus.asStateFlow()

    private val _resultText = MutableStateFlow("Press a button to start")
    val resultText = _resultText.asStateFlow()

    init {
        Log.i(TAG, "Starting HAL View Model...")
        connectToHal()
    }

    fun setGrade(studentIdx: Int, grade: Int) = launchHalCall{
        gradeManagerHal?.setGrade(studentIdx, grade)
        _resultText.value = "Set grade of student $studentIdx to $grade"
    }

    fun getGrade(studentIdx: Int) = launchHalCall {
        val grade = gradeManagerHal?.getGrade(studentIdx)
        _resultText.value = "Grade of student $studentIdx is $grade"
    }

    fun setGradeListToNull() = launchHalCall {
        gradeManagerHal?.clearGradesAndSetGradeListToNull()
        _resultText.value = "Set grade list to null"
    }

    fun freeGradeListMemory() = launchHalCall {
        gradeManagerHal?.clearGradesAndFreeGradeList()
        _resultText.value = "Grade list memory is freed!"
    }

    fun initializeGradeList() = launchHalCall {
        gradeManagerHal?.initializeGradeList()
        _resultText.value = "Initialized new grade list"
    }

    private fun connectToHal() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                Log.d(TAG, "Trying to get HAL Service: $HAL_SERVICE_NAME")
                val binder: IBinder? = ServiceManager.waitForDeclaredService(HAL_SERVICE_NAME)

                if (binder == null) {
                    _halStatus.value = "HAL $HAL_SERVICE_NAME not found"
                    Log.e(TAG, "HAL-Service not found! Is it running?")
                    return@launch
                }

                gradeManagerHal = IGradeManager.Stub.asInterface(binder)
                _halStatus.value = "HAL: Connected!"
                Log.d(TAG, "HAL-Service connected successfully")
            } catch (e: Exception) {
                _halStatus.value = "HAL: Connection error"
                Log.e(TAG, "Error while connecting to the HAL")
            }
        }
    }

    private fun launchHalCall(block: suspend () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            if (gradeManagerHal == null) {
                _resultText.value = "HAL not connected!"
                return@launch
            }

            try {
                block()
            } catch (e: RemoteException) {
                Log.e(TAG, "Lost connection to HAL! Did it crash?")
                _halStatus.value = "HAL: Connection lost! (Crashed?)"
                _resultText.value = "Error: HAL-Process crashed"
                gradeManagerHal = null
            }
        }
    }
}