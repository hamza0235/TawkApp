package com.example.tawkapp.ui.users

import android.content.Context
import android.nfc.tech.MifareUltralight
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tawkapp.OnItemClickListener
import com.example.tawkapp.adapters.UsersAdapter
import com.example.tawkapp.databinding.UsersFragmentBinding
import androidx.navigation.fragment.findNavController
import com.example.tawkapp.TawkApplication
import com.example.tawkapp.model.users.Users
import com.example.tawkapp.utils.Utility
import com.example.tawkapp.networking.Resource
import com.example.tawkapp.recievers.NetworkReceiver
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class UsersFragment : Fragment(), OnItemClickListener, NetworkReceiver.ConnectivityReceiverListener {

    private var lastUserID: Int = 1
    private val mViewModel: UsersViewModel by viewModels()

    private lateinit var mBinding: UsersFragmentBinding

    private var mUsersList = ArrayList<Users>()

    @Inject
    lateinit var mAdapter: UsersAdapter

    private var isAlreadyDataLoaded = false
    private var isSearchEnable = false
    private var isIntialDataLoaded = false

    companion object {
        fun newInstance() = UsersFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = UsersFragmentBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.recyclerView.also {
            it.layoutManager = LinearLayoutManager(requireContext())
            it.addItemDecoration(
                DividerItemDecoration(context, LinearLayout.VERTICAL)
            )
            it.adapter = mAdapter
            mAdapter.setOnItemClickListener(this)
        }

        mBinding.shimmerViewContainer.startShimmerAnimation()
        mViewModel.userResponse.observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.LOADING -> {

                }
                Resource.Status.SUCCESS -> {
                    var tempList = it.data as ArrayList<Users>

                    if (!isIntialDataLoaded){
                        mUsersList.clear()
                        mUsersList.addAll(tempList)
                    }

                    if (isAlreadyDataLoaded)
                        mUsersList.addAll(tempList)

                    if (isSearchEnable){
                        var searchList = mUsersList.filter { text ->
                            text.login!!.toLowerCase()?.contains(mBinding.etSearch.text.toString().toLowerCase(), true)
                        } as ArrayList<Users>

                        mAdapter.setUsersList(searchList)
                    }else{
                        mAdapter.setUsersList(mUsersList)
                    }

                    isIntialDataLoaded = true;
                    isAlreadyDataLoaded = false

                    if (mBinding.progressBar.visibility == View.VISIBLE)
                        mBinding.progressBar.visibility = View.GONE

                    hideShimmer()
                }
                Resource.Status.ERROR -> {
                    hideShimmer()
                }
            }
        }

        mViewModel.getUsers()

        mBinding.recyclerView.addOnScrollListener(onScrollChangeListener);

        mBinding.etSearch.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                var filterName = s.toString().toLowerCase()
                if (filterName.isNotEmpty()){
                    isSearchEnable = true

                    var searchList = mUsersList.filter { text ->
                        text.login!!.toLowerCase()?.contains(filterName, true)
                    } as ArrayList<Users>

                    mAdapter.setUsersList(searchList)

                }else{
                    mAdapter.setUsersList(mUsersList)
                    isSearchEnable = false;
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })

        TawkApplication().getInstance()?.updateUserData?.observe(viewLifecycleOwner){
            if (it){
                isIntialDataLoaded = false
                mViewModel.getUserList(lastUserID)
            }
        }
    }

    private val onScrollChangeListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

            if (!isSearchEnable) {
                if (visibleItemCount + firstVisibleItemPosition >= totalItemCount
                    && firstVisibleItemPosition >= 0
                    && totalItemCount >= MifareUltralight.PAGE_SIZE
                ) {
                    if (!isAlreadyDataLoaded){
                        isAlreadyDataLoaded = true
                        mBinding.progressBar.visibility = View.VISIBLE

                        lastUserID = 1
                        if (mUsersList.size > 0)
                            lastUserID = mUsersList[mUsersList.size - 1].id!!

                        if (Utility.isNetWorkAvaialable(requireContext())) {
                            mViewModel.getUserList(lastUserID)
                        } else {
                            Toast.makeText(context, "No Internet Available", Toast.LENGTH_SHORT).show()
                        }

                    }

                }
            }
        }
    }

    fun hideShimmer(){
        mBinding.shimmerViewContainer.stopShimmerAnimation()
        mBinding.shimmerViewContainer.visibility = View.GONE
    }

    override fun onItemClick(item: Users) {
        findNavController().navigate(
            UsersFragmentDirections.actionUsersFragmentToUserDetailFragment(
                item as Users
            )
        )
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        Log.e("**********","networkChange: " + isConnected);
        if (isConnected){
            if (!isIntialDataLoaded && mUsersList.size == 0){
                mViewModel.getUsers()
            }else if (isAlreadyDataLoaded){
                mViewModel.getUserList(lastUserID)
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        TawkApplication.getInstance()?.setNetworkListener(this)
    }
}