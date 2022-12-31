package ph.crisaroa.movielistapp.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie")
data class Movie(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "movie_id") val movieId: Int,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "rating") val rating: String?,
    @ColumnInfo(name = "genre") val genre: String?,
    @ColumnInfo(name = "duration") val duration: String?,
    @ColumnInfo(name = "released_date") val releasedDate: String?,
    @ColumnInfo(name = "trailer_link") val trailerLink: String?,
    @ColumnInfo(name = "poster_location") val posterLocation: String?,
    @ColumnInfo(name = "is_wishlist") val isOnWishlist: Boolean?
)
