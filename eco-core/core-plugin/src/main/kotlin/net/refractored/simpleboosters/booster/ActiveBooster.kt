package net.refractored.simpleboosters.booster

import java.time.Duration
import java.util.*

/**
 * Represents an active booster.
 * This class is used to store and manage an active booster.
 * @param booster The booster.
 * @param length The time the booster expires.
 * @param uuid The UUID of the booster.
 */
data class ActiveBooster(
    val booster: Booster,
    /**
     * The length in milliseconds of the booster.
     */
    var length: Long,
    val uuid: UUID,
) {
    /**
     * The expiry time of the booster.
     */
    val expiryTime = System.currentTimeMillis() + length

    fun isExpired() = expiryTime <= System.currentTimeMillis()

    fun getRemainingMillis(): Long = expiryTime - System.currentTimeMillis()

    fun getRemainingDuration(): Duration = Duration.ofMillis(getRemainingMillis())

    init {
        booster.SavedExpireTime = length.toDouble()
    }

    /**
     * Update the expiry time.
     */
    fun updateExpireTime(time: Duration) {
        length = time.toMillis()
        booster.SavedExpireTime = time.toMillis().toDouble()
    }

    fun addExpireTime(time: Duration) {
        length += time.toMillis()
        booster.SavedExpireTime = length.toDouble()
    }

    fun subtractExpireTime(time: Duration) {
        length -= time.toMillis()
        booster.SavedExpireTime = length.toDouble()
    }
}
