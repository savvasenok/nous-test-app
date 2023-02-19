package xyz.savvamirzoyan.nous.shared_data

import androidx.room.*
import xyz.savvamirzoyan.nous.core.ID

@Dao
interface NousFactsDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg items: NousFactLocal)

    @Query("SELECT * FROM nous_facts")
    suspend fun selectAll(): List<NousFactLocal>

    @Query("SELECT * FROM nous_facts WHERE factId = :nousFactId")
    suspend fun select(nousFactId: ID): NousFactLocal?

    @Delete
    suspend fun delete(item: NousFactLocal)
}
