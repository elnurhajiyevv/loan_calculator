package loan.exchange.data.repository


interface FileStorage {
    var url: String?
}

class FileStorageImpl : FileStorage {
    override var url: String? = null
}