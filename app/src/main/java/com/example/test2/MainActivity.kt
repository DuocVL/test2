package com.example.test2

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var editTextName: EditText
    private lateinit var editTextMSSV: EditText
    private lateinit var buttonAdd: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var studentAdapter: StudentAdapter
    private val studentList = mutableListOf<Student>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        try {
            editTextName = findViewById(R.id.editTextName) ?: throw NullPointerException("editTextName not found")
            editTextMSSV = findViewById(R.id.editTextMSSV) ?: throw NullPointerException("editTextMSSV not found")
            buttonAdd = findViewById(R.id.buttonAdd) ?: throw NullPointerException("buttonAdd not found")
            recyclerView = findViewById(R.id.recyclerView) ?: throw NullPointerException("recyclerView not found")
        } catch (e: Exception) {
            Log.e("MainActivity", "Error initializing views: ${e.message}")
            Toast.makeText(this, "Lỗi khi khởi tạo giao diện", Toast.LENGTH_SHORT).show()
            return
        }

        studentList.addAll(
            listOf(
                Student("Nguyễn Văn A", "20230001"),
                Student("Trần Thị B", "20230002"),
                Student("Lê Văn C", "20230003")
            )
        )

        studentAdapter = StudentAdapter(studentList) { position ->
            if (position in studentList.indices) {  // Kiểm tra index hợp lệ
                studentList.removeAt(position)
                studentAdapter.notifyItemRemoved(position)
            }
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = studentAdapter

        buttonAdd.setOnClickListener {
            val name = editTextName.text.toString().trim()
            val mssv = editTextMSSV.text.toString().trim()

            if (name.isNotEmpty() && mssv.isNotEmpty()) {
                studentList.add(0, Student(name, mssv))
                studentAdapter.notifyItemInserted(0)
                if (studentList.isNotEmpty()) recyclerView.scrollToPosition(0)  // Kiểm tra trước khi cuộn
                editTextName.text.clear()
                editTextMSSV.text.clear()
            } else {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
