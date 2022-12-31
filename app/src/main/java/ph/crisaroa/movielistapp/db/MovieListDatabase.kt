package ph.crisaroa.movielistapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ph.crisaroa.movielistapp.db.dao.MovieDAO
import ph.crisaroa.movielistapp.db.entity.Movie

@Database(entities = [Movie::class], version = 2, exportSchema = false)
abstract class MovieListDatabase : RoomDatabase() {
    abstract fun getMovieDAO(): MovieDAO

    companion object {
        @Volatile
        private var INSTANCE: MovieListDatabase? = null

        fun getDatabase(context: Context): MovieListDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MovieListDatabase::class.java,
                    "movie_list_database.db"
                )
                    .createFromAsset("database/movie_list_database.db")
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}