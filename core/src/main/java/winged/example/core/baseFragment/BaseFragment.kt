package winged.example.core.baseFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController

/**
 * A class that manages binding variable for fragments,
 * it also contains navigation functions
 */
abstract class BaseFragment<T : ViewDataBinding>(@LayoutRes private val fragmentRes: Int) : Fragment() {
    private var _binding: T? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, fragmentRes, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun navigateTo(targetDestination: Int, popUpToInclusive: Boolean = false) {
        findNavController().navigate(
            targetDestination,
            null,
            NavOptions.Builder().setPopUpTo(targetDestination, popUpToInclusive).build()
        )
    }

    fun navigateUp() {
        findNavController().navigateUp()
    }
}