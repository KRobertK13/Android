package hu.bme.aut.android.mobweb.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
@Dao
interface InExItemDao {
    @Query("SELECT * FROM inexitem")
    fun getAll(): List<InExItem>

    @Insert
    fun insert(inExItem: InExItem): Long

    @Update
    fun update(inExItem: InExItem)

    @Delete
    fun deleteItem(inExItem: InExItem)
}