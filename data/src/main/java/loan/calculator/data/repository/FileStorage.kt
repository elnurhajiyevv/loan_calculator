package loan.calculator.data.repository


interface FileStorage {
    var url: String?
}

class FileStorageImpl : FileStorage {
    override var url: String? = null
}