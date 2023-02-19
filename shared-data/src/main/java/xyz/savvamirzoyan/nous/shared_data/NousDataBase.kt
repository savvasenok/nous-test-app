package xyz.savvamirzoyan.nous.shared_data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        NousFactLocal::class
    ],
    exportSchema = true,
    version = 1
)
abstract class NousDataBase : RoomDatabase() {
    abstract val nousFactsDao: NousFactsDAO
}