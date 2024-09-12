package net.refractored.simpleboosters.booster

object RegisteredBoosters {
    /**
     * List of registered boosters.
     */
    @JvmStatic
    private val registeredBoosters = mutableListOf<Booster>()

    @JvmStatic
    fun getBooster(booster: Booster): Booster? = registeredBoosters.firstOrNull { it == booster }

    @JvmStatic
    fun unregisterAllBoosters() {
        registeredBoosters.clear()
    }

    @JvmStatic
    fun registerBooster(
        booster: Booster,
        time: Long,
    ) {
        if (getBooster(booster) != null) throw IllegalArgumentException("Booster already registered")
        registeredBoosters.add(booster)
    }

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
            if (!activatedBooster.isExpired()) continue
            booster.deactivateBooster()
        }
    }
}
