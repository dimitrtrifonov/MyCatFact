package clarity.software.dimtrif.mycatfact

import android.app.Application
import clarity.software.dimtrif.mycatfact.data.AppDatabase
import clarity.software.dimtrif.mycatfact.network.NetworkObserver

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
