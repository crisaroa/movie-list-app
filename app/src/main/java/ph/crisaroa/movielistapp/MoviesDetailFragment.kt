package ph.crisaroa.movielistapp

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.google.android.material.button.MaterialButton
import org.json.JSONObject
import java.io.FileInputStream
import java.text.DateFormat
import java.text.SimpleDateFormat


class MoviesDetailFragment : DialogFragment() {
    private lateinit var listener: AddMovieToWishlistListener

    interface AddMovieToWishlistListener {
        fun onAddToWishlist(movieData: JSONObject)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val ivPoster: ImageView = view.findViewById(R.id.iv_movie_poster)
        val ibCloseMovieDetails: ImageButton = view.findViewById(R.id.ib_close_movie_details)
        val btnToggleWishlist: MaterialButton = view.findViewById(R.id.btn_wishlist_toggle)
        val btnWatchTrailer: MaterialButton = view.findViewById(R.id.btn_watch_trailer)
        val tvTitle: TextView = view.findViewById(R.id.tv_movie_title)
        val tvRating: TextView = view.findViewById(R.id.tv_movie_rating)
        val tvDescription: TextView = view.findViewById(R.id.tv_movie_description)
        val tvGenre: TextView = view.findViewById(R.id.tv_movie_genre)
        val tvReleasedDate: TextView = view.findViewById(R.id.tv_movie_release)

        val title = requireArguments().getString("mt")
        val description = requireArguments().getString("md")
        val rating = requireArguments().getString("mr")
        val genre = requireArguments().getString("mg")
        val duration = requireArguments().getString("mdr")
        val releasedDate = requireArguments().getString("mrd")
        val trailerLink = requireArguments().getString("mtl")
        val posterLocation = requireArguments().getString("mpl")
        var isOnWishlist = requireArguments().getBoolean("mwl")
        val movieId = requireArguments().getInt("id")

        val dateSDF = SimpleDateFormat("yyyy-MM-dd")
        val date = dateSDF.parse(releasedDate!!)
        val df: DateFormat = SimpleDateFormat("dd MMMM yyyy")
        val formattedDate = df.format(date!!)

        val posterImage = loadImageBitmap(requireContext(), posterLocation)

        tvTitle.text = title
        tvRating.text = rating
        tvGenre.text = genre
        tvDescription.text = description
        tvReleasedDate.text = formattedDate

        Glide.with(this)
            .load(posterImage)
            .override(300, 450)
            .into(ivPoster)

        if (isOnWishlist) {
            btnToggleWishlist.text = resources.getString(R.string.remove_from_wishlist)
        } else {
            btnToggleWishlist.text = resources.getString(R.string.add_to_wishlist)
        }

        ibCloseMovieDetails.setOnClickListener {
            val manager = requireActivity().supportFragmentManager
            manager.popBackStack()
        }

        btnToggleWishlist.setOnClickListener {
            isOnWishlist = !isOnWishlist
            val movieJSON = JSONObject()

            movieJSON.put("id", movieId)
            movieJSON.put("mt", title)
            movieJSON.put("md", description)
            movieJSON.put("mr", rating)
            movieJSON.put("mg", genre)
            movieJSON.put("mdr", duration)
            movieJSON.put("mrd", releasedDate)
            movieJSON.put("mpl", posterLocation)
            movieJSON.put("mtl", trailerLink)
            movieJSON.put("mwl", isOnWishlist)
            listener.onAddToWishlist(movieJSON)

            if (isOnWishlist) {
                btnToggleWishlist.text = resources.getString(R.string.remove_from_wishlist)
            } else {
                btnToggleWishlist.text = resources.getString(R.string.add_to_wishlist)
            }
        }

        btnWatchTrailer.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(trailerLink)))
        }
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

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = context as AddMovieToWishlistListener
        } catch (e: ClassCastException) {
            throw ClassCastException(
                (context.toString() +
                        " must implement AddMovieToWishlistListener")
            )
        }
    }
}