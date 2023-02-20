package xyz.savvamirzoyan.nous.shared_data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import xyz.savvamirzoyan.nous.core.ID
import xyz.savvamirzoyan.nous.core.Model
import xyz.savvamirzoyan.nous.core.PictureUrl

@Entity(tableName = "nous_facts", indices = [Index(value = ["fact_id"], unique = true)])
data class NousFactLocal(
    @ColumnInfo(name = "fact_id") val factId: ID,
    val pictureUrl: PictureUrl,
    val title: String,
    val description: String,
    @PrimaryKey(autoGenerate = true) val Long: ID = 0
) : Model.Data.Local
