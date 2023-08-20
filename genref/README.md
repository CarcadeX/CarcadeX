Instruction for kotlin v. 1.8.21
<h3>Step 1  - configure build.gradle</h3>
<p>Add <b>Kotlin 1.8.21</b> and <b>KSP</b> plugins:</p>

```kotlin
kotlin("jvm") version "1.8.21"
id("com.google.devtools.ksp") version "1.8.21-1.0.11"
```

Add <b>CarcadeX GenRef</b> and <b>KotlinBukkitAPI</b> to project:</p>

```kotlin
dependencies {
    implementation("br.com.devsrsouza.kotlinbukkitapi:architecture:1.0.0-SNAPSHOT")
    compileOnly("io.github.iredtea:carcadex-genref:[LATEST_VERSION]")
    ksp("io.github.iredtea:carcadex-genref:[LATEST_VERSION]")
}
```

<h3>Step 2  - create plugin functions</h3>

```kotlin
@Plugin
fun KotlinPlugin.start() {
    // Plugin enable logic
}


//Optional
@OnDisable
fun KotlinPlugin.stop() {
    // Disable enable logic
}
```
