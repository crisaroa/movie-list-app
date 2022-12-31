package ph.crisaroa.movielistapp.db.repository

import ph.crisaroa.movielistapp.db.dao.MovieDAO
import ph.crisaroa.movielistapp.db.entity.Movie

class MovieRepository(private val dao: MovieDAO) {
    fun moviesByTitle() = dao.loadAllMoviesByTitle()
    fun moviesByReleasedDate() = dao.loadAllMoviesByReleasedDate()

    suspend fun insert(movie: Movie) {
        dao.insertMovie(movie)
    }

    suspend fun update(movie: Movie) {
        dao.updateMovie(movie)
    }

    suspend fun delete(movie: Movie) {
        dao.deleteMovie(movie)
    }
}