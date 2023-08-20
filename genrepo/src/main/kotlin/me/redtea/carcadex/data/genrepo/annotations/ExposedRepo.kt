package me.redtea.carcadex.data.genrepo.annotations

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class ExposedRepo(val databasePropertyPath: String)
