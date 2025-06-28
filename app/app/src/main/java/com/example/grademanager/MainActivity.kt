package com.example.grademanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.grademanager.ui.theme.GrademanagerTheme

class MainActivity : ComponentActivity() {
    private val viewModel = HalViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GrademanagerTheme {
                GradeManagerScreen(viewModel)
            }
        }
    }
}

@Composable
fun GradeManagerScreen(viewModel: HalViewModel) {
    val halStatus: String by viewModel.halStatus.collectAsState()
    val resultText: String by viewModel.resultText.collectAsState()

    var studentIdx by remember { mutableStateOf("0") }
    var grade by remember { mutableStateOf("0") }


    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = halStatus,
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = resultText,
        )
        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            value = studentIdx,
            onValueChange = { newStudentIdx: String ->
                studentIdx = newStudentIdx
            },
            placeholder = { Text("Student ID") }
        )
        Spacer(modifier = Modifier.height(10.dp))
        TextField(
            value = grade,
            onValueChange = { newGrade ->
                grade = newGrade
            },
            placeholder = { Text("Grade") }
        )
        Spacer(modifier = Modifier.height(30.dp))
        Button(
            onClick = {viewModel.setGrade(Integer.parseInt(studentIdx), Integer.parseInt(grade))},
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Set grade")
        }
        Spacer(modifier = Modifier.height(15.dp))
        Button(
            onClick = {viewModel.getGrade(Integer.parseInt(studentIdx))},
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Get grade")
        }
        Spacer(modifier = Modifier.height(15.dp))
        Button(
            onClick = {viewModel.setGradeListToNull()},
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Set grade list to null")
        }
        Spacer(modifier = Modifier.height(15.dp))
        Button(
            onClick = {viewModel.freeGradeListMemory()},
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Free grade list memory")
        }
        Spacer(modifier = Modifier.height(15.dp))
        Button(
            onClick = {viewModel.initializeGradeList()},
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Initialize grade list")
        }
    }
}