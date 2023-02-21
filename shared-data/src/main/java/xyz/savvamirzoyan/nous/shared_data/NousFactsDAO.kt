package xyz.savvamirzoyan.nous.shared_data

import androidx.room.*
import xyz.savvamirzoyan.nous.core.ID

@Dao
interface NousFactsDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg items: NousNewsItemLocal)

    @Query("SELECT * FROM nous_facts")
    suspend fun selectAll(): List<NousNewsItemLocal>

    @Query("SELECT * FROM nous_facts WHERE fact_id = :nousFactId")
    suspend fun select(nousFactId: ID): NousNewsItemLocal?

    @Delete
    suspend fun delete(item: NousNewsItemLocal)
}
