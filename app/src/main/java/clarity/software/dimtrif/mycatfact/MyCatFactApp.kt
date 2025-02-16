package clarity.software.dimtrif.mycatfact

import android.app.Application

class CatFactApp : Application() {
    companion object {
        lateinit var database: AppDatabase
        lateinit var networkObserver: NetworkObserver
    }

    override fun onCreate() {
        super.onCreate()
        database = AppDatabase.getDatabase(this)
        networkObserver = NetworkObserver(this)
    }
}
