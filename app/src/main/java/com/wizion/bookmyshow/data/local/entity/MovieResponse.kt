package com.wizion.bookmyshow.data.local.entity

import androidx.room.*
import com.wizion.bookmyshow.data.local.typeconverter.MovieTypeConverter

@Entity(tableName = "tbl_movie_data")
data class MovieResponse(
    @PrimaryKey
    val page:Int=1,

    @ColumnInfo(name = "movie_response")
    @TypeConverters(MovieTypeConverter::class)
    val results : List<Movie>
    )