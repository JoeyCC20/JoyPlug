package com.joybox.joyplug.host_sample.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.joybox.joyplug.host.core.PluginManager
import com.joybox.joyplug.host.core.PluginRouter
import com.joybox.joyplug.host_sample.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        val btnView : TextView = binding.btnJump
        homeViewModel.btn.observe(viewLifecycleOwner) {
            btnView.text = it
        }
        binding.btnJump.setOnClickListener {
            PluginRouter.startActivity(
                requireActivity(),
                "com.joybox.joyplug.activity_plugin_sample",
                "com.joybox.joyplug.activity_plugin_sample.MainActivity"
            )
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}