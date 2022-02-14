package ru.kudesnik.aleftestwork.ui.main

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.coroutines.*
import ru.kudesnik.aleftestwork.R
import ru.kudesnik.aleftestwork.databinding.MainFragmentBinding
import ru.kudesnik.aleftestwork.model.AppState

class MainFragment : Fragment(), CoroutineScope by MainScope() {
    private val viewModel: MainViewModel by lazy { ViewModelProvider(this)[MainViewModel::class.java] }
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

//    private var adapter: MainFragmentAdapter? = null

    private val adapter: MainFragmentAdapter by lazy {
        MainFragmentAdapter(object :
            OnItemViewClickListener {
            override fun onItemViewClick(data: String) {
                Toast.makeText(requireContext(), "Открываю: $data", Toast.LENGTH_SHORT).show()
                val frManager = activity?.supportFragmentManager
                frManager?.let {
                    val bundle = Bundle().apply {
                        putString(DetailsFragment.BUNDLE_EXTRA, data)
                    }

                    frManager.beginTransaction()
                        .add(R.id.container, DetailsFragment.newInstance(bundle))
                        .addToBackStack("")
                        .commitAllowingStateLoss()
                }
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {

  viewModel.getData()
                mainFragmentRecyclerView.layoutManager =
                    GridLayoutManager(requireContext(), getScreenOrientation())
                viewModel.getLiveData().observe(viewLifecycleOwner, { renderData(it) })
                mainSwipe.setOnRefreshListener {
                    swipeSetColor()
                    viewModel.getData()
                    mainSwipe.isRefreshing = false
                }
            }
        }

        private fun getScreenOrientation(): Int {
            return when (resources.configuration.orientation) {
                Configuration.ORIENTATION_PORTRAIT -> 2
                Configuration.ORIENTATION_LANDSCAPE -> 3
                else -> 1
            }
        }

        private fun swipeSetColor() = with(binding) {
            mainSwipe.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light
            )
        }

        override fun onDestroyView() {
            super.onDestroyView()
            _binding = null
        }

        private fun renderData(appState: AppState) = with(binding) {
            when (appState) {
                is AppState.Success -> {
                    progressBar.visibility = View.GONE
                    mainFragmentRecyclerView.visibility = View.VISIBLE
                    adapter.setData(appState.modelData)
                    mainFragmentRecyclerView.adapter = adapter

/*                adapter = MainFragmentAdapter(object : OnItemViewClickListener {
                    override fun onItemViewClick(data: String) {
                        val manager = activity?.supportFragmentManager
                        manager?.let { manager ->
                            val bundle = Bundle().apply {
                                putString(DetailsFragment.BUNDLE_EXTRA, data)
                            }

                            manager.beginTransaction()
                                .add(R.id.container, DetailsFragment.newInstance(bundle))
                                .addToBackStack("")
                                .commitAllowingStateLoss()
                        }
                    }
                })*/
                }
                is AppState.Loading -> {
                    progressBar.visibility = View.VISIBLE
                    mainFragmentRecyclerView.visibility = View.GONE
                }
            }
        }


        interface OnItemViewClickListener {
            fun onItemViewClick(data: String)
        }

        companion object {
            fun newInstance() = MainFragment()
        }
    }