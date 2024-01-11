package hu.bme.aut.android.mobweb.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "taliitem")
data class TaLiItem(
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) var id: Long? = null,
    @ColumnInfo(name = "targetSavings") var targetSavings: Int,
    @ColumnInfo(name = "spendingLimit") var spendingLimit: Int,
)