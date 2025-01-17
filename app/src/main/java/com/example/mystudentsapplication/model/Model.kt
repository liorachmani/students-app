package com.example.mystudentsapplication.model

class Model private constructor() {
    val students: MutableList<Student> = ArrayList();

    companion object {
        val shared = Model()
    }

    init {
        for (i in 0..20) {
            val student = Student(
                fullname = "Name $i",
                id = "id $i",
                phone = "123456789",
                address = "Israel",
                isChecked = false
            )
            students.add(student)
        }
    }
}