plugins {
    id("java")
    kotlin("jvm")
}

group = "me.redtea"
version = "lastest"

repositories {
    mavenCentral()
}

dependencies {

}

tasks.test {
    useJUnitPlatform()
}

tasks.register("wrapper") {

}

tasks.register("prepareKotlinBuildScriptModel") {

}