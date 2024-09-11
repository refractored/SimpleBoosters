package net.refractored.SimpleBoosters.booster

import java.time.Duration
import java.util.UUID

object ActiveBoosters {
    @JvmStatic
    private val registeredBoosters = mutableListOf<Booster>()

    @JvmStatic
    fun getBooster(booster: Booster): Booster? = registeredBoosters.firstOrNull { it == booster }

    /**
     * Register a booster.
     */
    @JvmStatic
    fun registerBooster(
        booster: Booster,
        time: Long,
    ) {
        if (getBooster(booster) != null) throw IllegalArgumentException("Booster already registered")
        registeredBoosters.add(booster)
    }

    /**
     * Unregister a booster.
     */
    @JvmStatic
    fun unregisterBooster(booster: Booster) {
        registeredBoosters.remove(booster)
    }

    @JvmStatic
    fun getActiveBoosters() = registeredBoosters.filter { it.active != null }

    @JvmStatic
    fun updateStatus() {
        for (booster in registeredBoosters) {
            val activatedBooster = booster.active ?: continue
            if (activatedBooster.expireTime > System.currentTimeMillis()) continue
            booster.deactivateBooster()
        }
    }
}

data class ActiveBooster(
    val booster: Booster,
    var expireTime: Long,
    val uuid: UUID,
) {
    fun isExpired() = System.currentTimeMillis() > expireTime

    fun getRemainingMiliseconds() = expireTime - System.currentTimeMillis()

    fun getRemainingDuration() = Duration.ofMillis(getRemainingMiliseconds())

    /**
     * Update the expire time.
     */
    fun updateExpireTime(time: Duration) {
        expireTime = System.currentTimeMillis() + time.toMillis()
    }

    fun addExpireTIme(time: Duration) {
        expireTime += time.toMillis()
    }

    fun subtractExpireTIme(time: Duration) {
        expireTime -= time.toMillis()
    }
}
