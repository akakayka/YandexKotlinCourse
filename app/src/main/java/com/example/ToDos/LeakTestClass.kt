package com.example.ToDos

class LeakTestClass {
    companion object {
        private val leakedInstances = mutableListOf<TestInnerClass>()
    }

    fun createLeak() {
        val temp = TestInnerClass()
        leakedInstances.add(temp)
    }

    inner class TestInnerClass {
        fun leakTest(){
            println(1)
        }
    }
}