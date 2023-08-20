package me.redtea.genrepojsonbukkit.annotations

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class JsonRepo(val pluginPropertyPath: String)
