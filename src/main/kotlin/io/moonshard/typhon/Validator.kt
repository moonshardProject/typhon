package io.moonshard.typhon


class Validator<T>(vararg val checkers: (T) -> Boolean) {
    fun check(toCheck: T): Boolean {
        for (f in this.checkers) {
            if (!f(toCheck)) {
                return false
            }
        }
        return true
    }
}