package ph.crisaroa.movielistapp.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import ph.crisaroa.movielistapp.db.entity.Movie

@Dao
interface MovieDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovie(patient: Movie)

    @Update
    suspend fun updateMovie(patient: Movie)

    @Delete
    suspend fun deleteMovie(patient: Movie)

    @Query("SELECT * FROM movie ORDER BY title ASC")
    fun loadAllMoviesByTitle(): LiveData<List<Movie>>

    @Query("SELECT * FROM movie ORDER BY released_date ASC")
    fun loadAllMoviesByReleasedDate(): LiveData<List<Movie>>
}