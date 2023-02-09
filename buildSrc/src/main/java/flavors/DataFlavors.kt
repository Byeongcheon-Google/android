package flavors

enum class DataFlavors(val applicstionIdSuffix: String? = null) {
    Fake(".fake"), Stage(".stage"), Production
}