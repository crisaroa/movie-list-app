package ph.crisaroa.movielistapp

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import org.json.JSONObject
import ph.crisaroa.movielistapp.adapter.MovieClickInterface
import ph.crisaroa.movielistapp.adapter.MoviesAdapter
import ph.crisaroa.movielistapp.db.entity.Movie
import ph.crisaroa.movielistapp.viewmodel.MovieViewModel
import java.io.FileOutputStream


class MainActivity : AppCompatActivity(),
    MoviesDetailFragment.AddMovieToWishlistListener,
    SortBottomSheetDialogFragment.SortListener,
    MovieClickInterface {
    //MaterialButton declarations
    private lateinit var btnSort: MaterialButton

    //RecyclerView declaration
    private lateinit var rvMovies: RecyclerView
    private lateinit var rvMoviesAdapter: MoviesAdapter
    private lateinit var rvMoviesLayoutManager: LinearLayoutManager

    //Database declarations
    private lateinit var mVM: MovieViewModel

    //String declarations
    private lateinit var movieTitle: String
    private lateinit var movieDescription: String
    private lateinit var movieRating: String
    private lateinit var movieGenre: String
    private lateinit var movieDuration: String
    private lateinit var movieReleasedDate: String
    private lateinit var movieTrailerLink: String
    private lateinit var moviePosterLocation: String

    //Boolean declarations
    private var isOnWishlist: Boolean = false

    //Integer declarations
    private var movieId: Int = 0
    private var currentId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContentView(R.layout.activity_main)

        initializeVariables()
        initializeListeners()

        if (firstRun()) {
            preloadPosters()
        }
    }

    private fun initializeVariables() {
        //MaterialButton initializations
        btnSort = findViewById(R.id.btn_sort)

        //RecyclerView initializations
        rvMovies = findViewById(R.id.rv_movies)
        rvMoviesAdapter = MoviesAdapter(this)
        rvMoviesLayoutManager = LinearLayoutManager(this)
        rvMovies.layoutManager = rvMoviesLayoutManager
        rvMovies.adapter = rvMoviesAdapter

        mVM = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[MovieViewModel::class.java]

        mVM.allMoviesByTitle.observe(this) { list ->
            list?.let {
                rvMoviesAdapter.updateList(it)
            }
        }
    }

    private fun initializeListeners() {
        //MaterialButton listeners
        btnSort.setOnClickListener {
            SortBottomSheetDialogFragment().apply {
                show(supportFragmentManager, tag)
            }
        }
    }

    private fun preloadPosters() {
        /*movieTitle = "Tenet"
        movieDescription = "Armed with only one word, Tenet, and fighting for the survival of the entire world, " +
                "a Protagonist journeys through a twilight world of international espionage on a mission that will " +
                "unfold in something beyond real time."
        movieRating = "7.8"
        movieDuration = "2h 30 min"
        movieGenre = "Action, Sci-Fi"
        movieReleasedDate = "2020-09-03"
        movieTrailerLink = "https://www.youtube.com/watch?v=LdOM0x0XDMo"*/
        //isOnWishlist = false

        moviePosterLocation = "poster_tenet"
        var bm = BitmapFactory.decodeResource(resources, R.drawable.poster_tenet)
        saveImage(this, bm, moviePosterLocation)

        /*mVM.addMovie(Movie(1, movieTitle, movieDescription, movieRating, movieGenre, movieDuration, movieReleasedDate, movieTrailerLink, moviePosterLocation, isOnWishlist))

        movieTitle = "Spider-Man: Into the Spider-Verse"
        movieDescription = "Teen Miles Morales becomes the Spider-Man of his universe, and must join with five " +
                "spider-powered individuals from other dimensions to stop a threat for all realities."
        movieRating = "8.4"
        movieGenre = "Action, Animation, Adventure"
        movieDuration = "1h 57min"
        movieReleasedDate = "2018-12-14"
        movieTrailerLink = "https://www.youtube.com/watch?v=tg52up16eq0"*/
        //isOnWishlist = false

        moviePosterLocation = "poster_spiderman"
        bm = BitmapFactory.decodeResource(resources, R.drawable.poster_spiderman)
        saveImage(this, bm, moviePosterLocation)

        /* mVM.addMovie(Movie(2, movieTitle, movieDescription, movieRating, movieGenre, movieDuration, movieReleasedDate, movieTrailerLink, moviePosterLocation, isOnWishlist))

         movieTitle = "Knives Out"
         movieDescription = "A detective investigates the death of a patriarch of an eccentric, combative family."
         movieRating = "7.9"
         movieGenre = "Comedy, Crime, Drama"
         movieDuration = "2h 10min"
         movieReleasedDate = "2019-11-27"
         movieTrailerLink = "https://www.youtube.com/watch?v=qGqiHJTsRkQ"*/
        //isOnWishlist = false

        moviePosterLocation = "poster_knives"
        bm = BitmapFactory.decodeResource(resources, R.drawable.poster_knives)
        saveImage(this, bm, moviePosterLocation)

        /*mVM.addMovie(Movie(3, movieTitle, movieDescription, movieRating, movieGenre, movieDuration, movieReleasedDate, movieTrailerLink, moviePosterLocation, isOnWishlist))

        movieTitle = "Guardians of the Galaxy"
        movieDescription = "A group of intergalactic criminals must pull together to stop a fanatical warrior with " +
                "plans to purge the universe."
        movieRating = "8.0"
        movieGenre = "Action, Adventure, Comedy"
        movieDuration = "2h 1min"
        movieReleasedDate = "2014-08-01"
        movieTrailerLink = "https://www.youtube.com/watch?v=d96cjJhvlMA"*/
        //isOnWishlist = false

        moviePosterLocation = "poster_guardians"
        bm = BitmapFactory.decodeResource(resources, R.drawable.poster_guardians)
        saveImage(this, bm, moviePosterLocation)

        /*mVM.addMovie(Movie(4, movieTitle, movieDescription, movieRating, movieGenre, movieDuration, movieReleasedDate, movieTrailerLink, moviePosterLocation, isOnWishlist))

        movieTitle = "Avengers: Age of Ultron"
        movieDescription = "When Tony Stark and Bruce Banner try to jump-start a dormant peacekeeping " +
                "program called Ultron, things go horribly wrong and it's up to Earth's mightiest heroes to stop the " +
                "villainous Ultron from enacting his terrible plan."
        movieRating = "7.3"
        movieGenre = "Action, Adventure, Sci-Fi"
        movieDuration = "2h 21min"
        movieReleasedDate = "2015-05-01"
        movieTrailerLink = "https://www.youtube.com/watch?v=tmeOjFno6Do"*/
        //isOnWishlist = false

        moviePosterLocation = "poster_avengers"
        bm = BitmapFactory.decodeResource(resources, R.drawable.poster_avengers)
        saveImage(this, bm, moviePosterLocation)

        //mVM.addMovie(Movie(5, movieTitle, movieDescription, movieRating, movieGenre, movieDuration, movieReleasedDate, movieTrailerLink, moviePosterLocation, isOnWishlist))
    }

    private fun saveImage(context: Context, b: Bitmap, imageName: String?) {
        val foStream: FileOutputStream
        try {
            foStream = context.openFileOutput(imageName, Context.MODE_PRIVATE)
            b.compress(Bitmap.CompressFormat.JPEG, 100, foStream)
            foStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onAddToWishlist(movieData: JSONObject) {
        movieId = movieData.getInt("id")
        movieTitle = movieData.getString("mt")
        movieDescription = movieData.getString("md")
        movieRating = movieData.getString("mr")
        movieGenre = movieData.getString("mg")
        movieDuration = movieData.getString("mdr")
        movieReleasedDate = movieData.getString("mrd")
        movieTrailerLink = movieData.getString("mtl")
        moviePosterLocation = movieData.getString("mpl")
        isOnWishlist = movieData.getBoolean("mwl")

        val updatedMovie = Movie(
            movieId,
            movieTitle,
            movieDescription,
            movieRating,
            movieGenre,
            movieDuration,
            movieReleasedDate,
            movieTrailerLink,
            moviePosterLocation,
            isOnWishlist
        )
        mVM.updateMovie(updatedMovie)
        val message = if (isOnWishlist) "Movie has been added to your watchlist."
        else "Movie has been removed from your watchlist"
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onMovieClick(movie: Movie) {
        val fragmentManager = supportFragmentManager
        val mdf = MoviesDetailFragment()
        val data = Bundle()
        val transaction = fragmentManager.beginTransaction()
        currentId = movie.movieId

        data.putString("mt", movie.title)
        data.putString("md", movie.description)
        data.putString("mr", movie.rating)
        data.putString("mg", movie.genre)
        data.putString("mdr", movie.duration)
        data.putString("mrd", movie.releasedDate)
        data.putString("mtl", movie.trailerLink)
        data.putString("mpl", movie.posterLocation)
        data.putBoolean("mwl", movie.isOnWishlist!!)
        data.putInt("id", movie.movieId)
        mdf.arguments = data
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        transaction
            .add(android.R.id.content, mdf)
            .addToBackStack(null)
            .commit()
    }

    override fun changeSort(sort: String) {
        when (sort) {
            "Title" -> {
                mVM.allMoviesByTitle.observe(this) { list ->
                    list?.let {
                        rvMoviesAdapter.updateList(it)
                    }
                }
            }
            "Released date" -> {
                mVM.allMoviesByReleasedDate.observe(this) { list ->
                    list?.let {
                        rvMoviesAdapter.updateList(it)
                    }
                }
            }
        }
    }

    private fun firstRun(): Boolean {
        val preferences = getPreferences(MODE_PRIVATE)
        val rb = preferences.getBoolean("rb", false)
        if (!rb) {
            val editor = preferences.edit()
            editor.putBoolean("RanBefore", true)
            editor.apply()
        }
        return !rb
    }
}