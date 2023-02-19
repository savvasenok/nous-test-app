package xyz.savvamirzoyan.nous.shared_data

import androidx.room.Entity
import androidx.room.PrimaryKey
import xyz.savvamirzoyan.nous.core.ID
import xyz.savvamirzoyan.nous.core.Model
import xyz.savvamirzoyan.nous.core.PictureUrl

@Entity(tableName = "nous_facts")
data class NousFactLocal(
    val factId: ID,
    val pictureUrl: PictureUrl,
    val title: String,
    val description: String,
    @PrimaryKey(autoGenerate = true) val id: ID = 0
) : Model.Data.Local
