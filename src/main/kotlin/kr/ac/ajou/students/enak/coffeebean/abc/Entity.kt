package kr.ac.ajou.students.enak.coffeebean.abc

interface Entity {
    fun getLastSyncedMatrix(): MutableMap<String, Any>

    fun markDirty()
}