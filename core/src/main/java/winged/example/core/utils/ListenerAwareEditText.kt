package winged.example.core.utils

import android.content.Context
import android.text.TextWatcher
import android.util.AttributeSet
import com.google.android.material.textfield.TextInputEditText

/**
 * This class was created to fix a strange memory leak:
 * The [R.id.repeatPasswordTIET] caused it, quite possibly because of it's inputType?
 *
 * Why did it happen in the first place?:
 * TextView's (or more likely EditText's)
 * have a tendency to not remove their listeners once they are not needed.
 * It's just not configured that way,
 * maybe due to expecting the callers to remove the listeners themselves once they are not needed.
 * A lot of other listeners get cleared once they are not needed,
 * but the TextWatcher is an exception unfortunately.
 *
 * So, this class takes care of removing ALL of the listeners, even the ones I did not set...
 */

class ListenerAwareEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
): TextInputEditText(context, attrs, android.R.attr.editTextStyle) {

    private companion object {
        val textChangedListenersStatic: MutableList<TextWatcher> = ArrayList()
    }

    private val textChangedListeners: MutableList<TextWatcher> = ArrayList()

    /**
     * Swap the listeners added in the companion object list with the actual.
     */
    init {
        textChangedListeners.addAll(textChangedListenersStatic)
        textChangedListenersStatic.clear()
    }

    /**
     * Overridden to hold a reference of the listener
     */
    override fun addTextChangedListener(watcher: TextWatcher?) {
        super.addTextChangedListener(watcher)
        watcher?.let {
            try {
                textChangedListeners.add(it)
            } catch (ignore: NullPointerException) {
                textChangedListenersStatic.add(it)
            }
        }
    }

    /**
     * Overridden to release the listener
     */
    override fun removeTextChangedListener(watcher: TextWatcher?) {
        super.removeTextChangedListener(watcher)
        watcher?.let {
            // NullPointerException may happen because this method
            // can be called before the object itself is constructed,
            // from the super classes.
            // So, to hold the values, a static list in a
            // companion object was used, and then the elements
            // get transferred to the actual list, clearing the
            // static one.
            try {
                textChangedListeners.remove(it)
            } catch (ignore: NullPointerException) {
                textChangedListenersStatic.remove(it)
            }
        }
    }

    /**
     * Clears the text changed listeners. Call this from the
     * fragment's [onDestroyView]!!!
     */
    fun clearTextChangedListeners() {
        textChangedListeners.forEach {
            super.removeTextChangedListener(it)
        }
        textChangedListeners.clear()
    }
}