package hu.bme.aut.android.mobweb.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter

@Entity(tableName = "inexitem")
data class InExItem(
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) var id: Long? = null,
    @ColumnInfo(name = "sign") var sign: Boolean,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "amount") var amount: Int,
    @ColumnInfo(name = "description") var description: String,
    @ColumnInfo(name = "category") var category: Category,
) {
    enum class Category {
        WORK, SALE, PURCHASE, ENTERTAINMENT, OVERHEAD;
        companion object {
            @JvmStatic
            @TypeConverter
            fun getByOrdinal(ordinal: Int): Category? {
                var ret: Category? = null
                for (cat in values()) {
                    if (cat.ordinal == ordinal) {
                        ret = cat
                        break
                    }
                }
                return ret
            }

            @JvmStatic
            @TypeConverter
            fun toInt(category: Category): Int {
                return category.ordinal
            }
        }
    }
}
