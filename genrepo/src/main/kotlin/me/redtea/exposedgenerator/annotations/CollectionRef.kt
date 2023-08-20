package me.redtea.exposedgenerator.annotations

@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.SOURCE)
annotation class CollectionRef(val collectionName: String = "")
