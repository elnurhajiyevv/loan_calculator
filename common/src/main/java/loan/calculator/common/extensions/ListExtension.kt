package loan.calculator.common.extensions


/**
 * This extension function is responsible to check if list position is bigger than list size to
 * eliminate indexoutofbounds exception
 */
fun <E> List<E>.getIfExists(position: Int): E? {
    return if (position in 0 until size)
        get(position)
    else
        null
}

/**
 * This extension function is responsible to check if list position is bigger than list size to
 * eliminate indexoutofbounds exception
 */
fun <E> Array<E>.getIfExists(position: Int): E? {
    return if (position in 0 until size)
        get(position)
    else
        null
}
