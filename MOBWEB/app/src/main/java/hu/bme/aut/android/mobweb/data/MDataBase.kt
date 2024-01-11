package hu.bme.aut.android.mobweb.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [InExItem::class, TaLiItem::class], version = 1)
@TypeConverters(value = [InExItem.Category::class])
abstract class MDataBase : RoomDatabase() {
    abstract fun inExItemDao(): InExItemDao
    abstract fun taLiItemDao(): TaLiItemDao

    companion object {
        fun getDatabase(applicationContext: Context): MDataBase {
            return Room.databaseBuilder(
                applicationContext,
                MDataBase::class.java,
                "mDataBase"
            ).build();
        }
    }
}