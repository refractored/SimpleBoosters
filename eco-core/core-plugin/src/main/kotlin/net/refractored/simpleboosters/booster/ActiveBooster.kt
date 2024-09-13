package net.refractored.simpleboosters.booster

import java.time.Duration
import java.util.*

/**
 * Represents an active booster.
 * This class is used to store and manage an active booster.
 * @param booster The booster.
 * @param expireTime The time the booster expires.
 * @param uuid The UUID of the booster.
 */
data class ActiveBooster(
    val booster: Booster,
    var expireTime: Long,
    val uuid: UUID,
) {
    fun isExpired() = System.currentTimeMillis() > expireTime

    fun getRemainingMiliseconds() = expireTime - System.currentTimeMillis()

    fun getRemainingDuration() = Duration.ofMillis(getRemainingMiliseconds())

    /**
     * Update the expiry time.
     */
    fun updateExpireTime(time: Duration) {
        expireTime = System.currentTimeMillis() + time.toMillis()
        booster.SavedExpireTime = expireTime.toDouble()
    }

    fun addExpireTIme(time: Duration) {
        expireTime += time.toMillis()
        booster.SavedExpireTime = expireTime.toDouble()
    }

    fun subtractExpireTIme(time: Duration) {
        expireTime -= time.toMillis()
        booster.SavedExpireTime = expireTime.toDouble()
    }
}
