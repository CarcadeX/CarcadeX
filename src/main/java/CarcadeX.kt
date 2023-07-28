import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import me.redtea.carcadex.kotlin.extensions.load
import me.redtea.carcadex.kotlin.extensions.repo
import me.redtea.carcadex.kotlin.extensions.save

data class SomeDomain(val tag: String, var age: Int, var balance: Float)

fun main() {
    val domainRepo = repo<String, SomeDomain> {
        save {
            Json.encodeToString(it)
        }
        load {
            Json.decodeFromString(it)
        }
    }
    domainRepo["key1"] = SomeDomain("key1", 1, 100.0f)
    domainRepo["key2"] = SomeDomain("key2", 2, 200.0f)
    domainRepo["key3"] = SomeDomain("key3", 3, 300.0f)
    domainRepo.saveAll()
}