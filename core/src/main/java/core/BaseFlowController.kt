package core

import android.os.Bundle
import android.util.SparseArray
import androidx.annotation.LayoutRes
import com.bluelinelabs.conductor.Router
import timber.log.Timber
import toothpick.ktp.KTP
private const val ROUTER_STATES_KEY = "STATE"
abstract class BaseFlowController(@LayoutRes private val layoutRes: Int) : BaseController(layoutRes)
{
    private val TAG: String
        get() = javaClass.simpleName
    protected var routerStates = SparseArray<Bundle>()
    protected lateinit var childRouter: Router

    override fun onDestroy() {
        log("onDestroy")
        KTP.closeScope(scope)
        super.onDestroy()
    }

    private fun log(log: String) {
        Timber.d("$TAG - $log")
    }


}