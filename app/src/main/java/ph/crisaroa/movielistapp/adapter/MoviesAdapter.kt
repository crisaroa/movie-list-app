package ph.crisaroa.movielistapp.adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ph.crisaroa.movielistapp.R
import ph.crisaroa.movielistapp.db.entity.Movie
import java.io.FileInputStream
import java.text.DateFormat
import java.text.SimpleDateFormat

class MoviesAdapter(
    private val movieClickInterface: MovieClickInterface
) :
    RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    private val allMovies = ArrayList<Movie>()

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val title: TextView = v.findViewById(R.id.tv_item_title)
        val description: TextView = v.findViewById(R.id.tv_item_description)
        val wishlistStatus: TextView = v.findViewById(R.id.tv_item_wishlist_status)
        val poster: ImageView = v.findViewById(R.id.iv_item_poster)
        val c: Context = v.context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.item_movie,
            parent, false
        )
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dateSDF = SimpleDateFormat("yyyy-mm-dd")
        val date = dateSDF.parse(allMovies[position].releasedDate.toString())
        val df: DateFormat = SimpleDateFormat("yyyy")
        val year = df.format(date!!)
        val t = allMovies[position].title + " (" + year + ")"
        val d = allMovies[position].duration + " - " + allMovies[position].genre
        val p = allMovies[position].posterLocation
        val isOnWishList = allMovies[position].isOnWishlist
        val posterImage = loadImageBitmap(holder.c, p)

        holder.title.text = t
        holder.description.text = d

        Glide.with(holder.c)
            .load(posterImage)
            .override(200, 300)
            .into(holder.poster)

        if (isOnWishList!!) {
            holder.wishlistStatus.visibility = View.VISIBLE
        } else {
            holder.wishlistStatus.visibility = View.GONE
        }

        holder.itemView.setOnClickListener {
            movieClickInterface.onMovieClick(allMovies[position])
        }
    }

    override fun getItemCount(): Int {
        return allMovies.size
    }

    fun updateList(newList: List<Movie>) {
        allMovies.clear()
        allMovies.addAll(newList)
        notifyDataSetChanged()
    }

    private fun loadImageBitmap(context: Context, imageName: String?): Bitmap? {
        var bitmap: Bitmap? = null
        val fStream: FileInputStream
        try {
            fStream = context.openFileInput(imageName)
            bitmap = BitmapFactory.decodeStream(fStream)
            fStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return bitmap
    }
}

interface MovieClickInterface {
    fun onMovieClick(movie: Movie)
}