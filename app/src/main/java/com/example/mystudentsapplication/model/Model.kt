package com.example.mystudentsapplication.model

import android.os.Looper
import androidx.core.os.HandlerCompat
import com.example.mystudentsapplication.model.dao.AppLocalDB
import com.example.mystudentsapplication.model.dao.AppLocalDbRepository
import java.util.concurrent.Executors

class Model private constructor() {

    private val database: AppLocalDbRepository = AppLocalDB.db
    private val executor = Executors.newSingleThreadExecutor()
    private val mainHandler = HandlerCompat.createAsync(Looper.getMainLooper())

    companion object {
        val shared = Model()
    }

    fun getAllStudents(callback: (List<Student>) -> Unit) {
        executor.execute {
            val students = database.studentDao().getAllStudents()
            mainHandler.post {
                callback(students)
            }
        }
    }

    fun addStudent(student: Student, callback: () -> Unit) {
        executor.execute {
            database.studentDao().insertStudents(student)
            mainHandler.post {
                callback()
            }
        }
    }

    fun updateStudent(id: String, student: Student, callback: () -> Unit) {
        executor.execute {
            database.studentDao().updateStudent(
                oldId = id,
                newId = student.id,
                fullname = student.fullname,
                phone = student.phone,
                address = student.address,
                isChecked = student.isChecked
            )
            mainHandler.post {
                callback()
            }
        }
    }

    fun deleteStudent(student: Student, callback: () -> Unit) {
        executor.execute {
            database.studentDao().delete(student)
            mainHandler.post {
                callback()
            }
        }
    }

    fun updateStudentIsCheckedById(id: String, isChecked: Boolean) {
        executor.execute {
            database.studentDao().updateIsCheckedById(id, isChecked)
        }
    }
}