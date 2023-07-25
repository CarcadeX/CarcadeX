import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.statements.api.ExposedBlob
import org.jetbrains.exposed.sql.statements.api.PreparedStatementApi
import org.jetbrains.exposed.sql.statements.jdbc.JdbcPreparedStatementImpl
import org.jetbrains.exposed.sql.vendors.currentDialect
import java.io.Serializable
import java.util.Date
import java.util.UUID
import kotlin.Array
import java.sql.Array as SQLArray

/**
 * @author DRSchlaubi
 */

/**
 * Creates a text array column with [name].
 *
 * @param size an optional size of the array
 */
public fun Table.textArray(name: String, size: Int? = null): Column<Array<String>> =
        array(name, currentDialect.dataTypeProvider.textType(), size)
public fun Table.integerArray(name: String, size: Int? = null): Column<Array<Int>> =
    array(name, currentDialect.dataTypeProvider.integerType(), size)
public fun Table.floatArray(name: String, size: Int? = null): Column<Array<Float>> =
    array(name, currentDialect.dataTypeProvider.floatType(), size)
public fun Table.doubleArray(name: String, size: Int? = null): Column<Array<Double>> =
    array(name, currentDialect.dataTypeProvider.doubleType(), size)
public fun Table.booleanArray(name: String, size: Int? = null): Column<Array<Double>> =
    array(name, currentDialect.dataTypeProvider.booleanType(), size)
public fun Table.byteArray(name: String, size: Int? = null): Column<Array<Byte>> =
    array(name, currentDialect.dataTypeProvider.byteType(), size)
public fun Table.uuidArray(name: String, size: Int? = null): Column<Array<UUID>> =
    array(name, currentDialect.dataTypeProvider.uuidType(), size)
public fun Table.longArray(name: String, size: Int? = null): Column<Array<UUID>> =
    array(name, currentDialect.dataTypeProvider.longType(), size)
public fun Table.shortArray(name: String, size: Int? = null): Column<Array<UUID>> =
    array(name, currentDialect.dataTypeProvider.shortType(), size)

private fun <T : Serializable> Table.array(name: String, underlyingType: String, size: Int? = null) =
    registerColumn<Array<T>>(name, ArrayColumnType<T>(underlyingType, size))

/**
 * Checks whether this string is in the [other] expression.
 *
 * Example:
 * ```kotlin
 * productService.find { "tag" eqAny ProductsTable.tags }
 * ```
 *
 * @see any
 */
public infix fun String.equalsAny(other: Expression<Array<String>>): EqOp =
        stringLiteral(this) eqAny other

/**
 * Invokes the `ANY` function on [expression].
 */
public fun <T : Serializable> any(
        expression: Expression<Array<T>>,
): ExpressionWithColumnType<String?> = CustomStringFunction("ANY", expression)

private infix fun <T : Serializable> Expression<T>.eqAny(other: Expression<Array<T>>): EqOp = EqOp(this, any(other))

/**
 * Implementation of [ColumnType] for the SQL `ARRAY` type.
 *
 * @property underlyingType the type of the array
 * @property size an optional size of the array
 */
public class ArrayColumnType<T : Serializable>(
        private val underlyingType: String, private val size: Int?
) : ColumnType() {
    override fun sqlType(): String = "$underlyingType ARRAY${size?.let { "[$it]" } ?: ""}"

    override fun notNullValueToDB(value: Any): Any = when (value) {
        is Array<*> -> value
        is Collection<*> -> value.toTypedArray()
        else -> error("Got unexpected array value of type: ${value::class.qualifiedName} ($value)")
    }

    override fun valueFromDB(value: Any): Any = when (value) {
        is SQLArray -> value.array as Array<*>
        is Array<*> -> value
        is Collection<*> -> value.toTypedArray()
        else -> error("Got unexpected array value of type: ${value::class.qualifiedName} ($value)")
    }

    override fun setParameter(stmt: PreparedStatementApi, index: Int, value: Any?) {
        if (value == null) {
            stmt.setNull(index, this)
        } else {
            val preparedStatement = stmt as? JdbcPreparedStatementImpl ?: error("Currently only JDBC is supported")
            val array = preparedStatement.statement.connection.createArrayOf(underlyingType, value as Array<*>)
            stmt[index] = array
        }
    }
}