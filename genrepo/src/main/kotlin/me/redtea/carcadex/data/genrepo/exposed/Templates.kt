fun generateExposedRepo(packageName: String,
                        valueName: String,
                        keyType: String,
                        valueType: String,
                        databasePropPath: String,
                        valueTableName: String): String = """


val ${valueName.lowercase()}Repo = repo<$keyType, $valueType> {
    schema(object : AbstractSchemaStrategy<$keyType, $valueType>() {

        val db: Database = ${databasePropPath}

        init  {
            transaction(db) {
                SchemaUtils.create(${valueTableName})
            }
        }

        override fun all(): MutableCollection<$valueType> = ${valueName}DAO.all().map { it.toData() }.toMutableList()


        override fun removeAll() = ${valueName}DAO.all().forEach { it.delete() }

        override fun close() {
        }


        override fun remove(key: $keyType?) {
            ${valueName}DAO[key!!].delete()
        }

        override fun insert(key: $keyType?, value: $valueType?) {
            value!!.toDAO()
        }

        override fun get(key: $keyType?): $valueType = ${valueType}DAO[key!!].toData()
    })
}
"""