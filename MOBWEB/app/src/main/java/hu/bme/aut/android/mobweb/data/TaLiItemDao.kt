package hu.bme.aut.android.mobweb.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
@Dao
interface TaLiItemDao {
    @Query("SELECT * FROM taliitem")
    fun getAll(): List<TaLiItem>

    @Insert
    fun insert(taLiItem: TaLiItem): Long

    @Update
    fun update(taLiItem: TaLiItem)

    @Delete
    fun deleteItem(taLiItem: TaLiItem)
}