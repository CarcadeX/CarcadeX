package me.redtea.carcadex.data.genrepo.exposed

import me.redtea.carcadex.data.genrepo.exposed.UserConverter.toDAO
import me.redtea.carcadex.data.genrepo.exposed.UserConverter.toData
import me.redtea.carcadex.data.kotlinextensions.repo
import me.redtea.carcadex.data.schema.AbstractSchemaStrategy
import me.redtea.carcadex.data.schema.SchemaStrategy
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*
import java.util.function.Predicate



data class User(
    val id: Int,
    val online: Boolean,
    var col: Collection<String>
)

object Users : IdTable<Int>() {
    val online = bool("online")

    override val id: Column<EntityID<Int>> = integer("id").entityId()
    override val primaryKey: PrimaryKey = PrimaryKey(id)
}

//DAO User
class UserDAO(id: EntityID<Int>) : Entity<Int>(id) {
    companion object : EntityClass<Int, UserDAO>(Users)
    var online by Users.online
    fun col(): Collection<String> = UserColDAO.collection(this)
    fun col(col: Collection<String>) { UserColDAO.collection(col, this) }
}

//Converter User
object UserConverter {
    fun User.toDAO(): UserDAO {
        val dao = UserDAO.findById(this@toDAO.id)
        return if(dao == null) UserDAO.new(this@toDAO.id) {
            this.online = this@toDAO.online
            UserColDAO.collection(this@toDAO.col, this)
        } else UserDAO[this@toDAO.id].let {
            with(it) {
                this.online = this@toDAO.online
                UserColDAO.collection(this@toDAO.col, this)
            }
            it
        }
    }
    fun UserDAO.toData(): User =
        UserDAO[this@toData.id].let {
            User(it.id.value, online = it.online, col = UserColDAO.collection(it))
        }

    fun User.updateDAO() {
        toDAO()
    }
}

object UserCol : IntIdTable() {
    val value = varchar("value", 255)
    val owner = reference("owner", Users)
}

class UserColDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<UserColDAO>(UserCol) {
        fun collection(owner: UserDAO): Collection<String> = UserColDAO.all().filter { it.owner == owner }.map { it.value }
        fun collection(col: Collection<String>, owner: UserDAO) {
            UserColDAO.all().filter { it.owner == owner }.forEach { it.delete() }
            col.forEach {
                UserColDAO.new {
                    value = it
                    this.owner = owner
                }
            }
        }
    }

    var value by UserCol.value
    var owner by UserDAO referencedOn UserCol.owner


}
