fun generateJsonRepo(plugin: String,
                        valueName: String,
                        keyType: String,
                        valueType: String): String = """
val ${valueName.lowercase()}Repo =  repo<$keyType, $valueType>($plugin) {
    save = json::encodeToString
    load = json::decodeFromString
}
"""