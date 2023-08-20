package me.redtea.exposedgenerator.annotations

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.SOURCE)
annotation class ID(val autoIncrement: Boolean = false)
