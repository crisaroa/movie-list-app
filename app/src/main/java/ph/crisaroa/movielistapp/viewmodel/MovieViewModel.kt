package ph.crisaroa.movielistapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ph.crisaroa.movielistapp.db.MovieListDatabase
import ph.crisaroa.movielistapp.db.entity.Movie
import ph.crisaroa.movielistapp.db.repository.MovieRepository

class MovieViewModel(application: Application) : AndroidViewModel(application) {
    val allMoviesByTitle: LiveData<List<Movie>>
    val allMoviesByReleasedDate: LiveData<List<Movie>>
    private val repository: MovieRepository

    init {
        val dao = MovieListDatabase.getDatabase(application).getMovieDAO()
        repository = MovieRepository(dao)
        allMoviesByTitle = repository.moviesByTitle()
        allMoviesByReleasedDate = repository.moviesByReleasedDate()
    }

    fun deleteMovie(movie: Movie) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(movie)
    }

    fun updateMovie(movie: Movie) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(movie)
    }

    fun addMovie(movie: Movie) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(movie)
    }
}