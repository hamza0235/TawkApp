package com.example.tawkapp.ui.user_detail

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.tawkapp.R
import com.example.tawkapp.TawkApplication
import com.example.tawkapp.databinding.UserDetailFragmentBinding
import com.example.tawkapp.model.users.UserDetail
import com.example.tawkapp.model.users.Users
import com.example.tawkapp.utils.Utility
import com.example.tawkapp.networking.Resource
import com.example.tawkapp.recievers.NetworkReceiver
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserDetailFragment : Fragment(), NetworkReceiver.ConnectivityReceiverListener {

    private val mViewModel: UserDetailViewModel by viewModels()

    private lateinit var mBinding: UserDetailFragmentBinding

    private lateinit var userProfile: Users
    private lateinit var userDetail: UserDetail
    private var isIntialDataLoaded = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = UserDetailFragmentBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.shimmerViewContainer.startShimmerAnimation()

        mBinding.imgBack.setOnClickListener{
            findNavController().popBackStack()
        }

        mBinding.btnSave.setOnClickListener {
            GlobalScope.launch(Dispatchers.Main){
                userProfile.notes = mBinding.etNotes.text.toString()
                mViewModel.saveUserNotes(userProfile)

                TawkApplication().getInstance()?.updateUserData!!.value = true
            }
        }

        mViewModel.userDetailResponse.observe(viewLifecycleOwner){
            when (it.status){
                Resource.Status.LOADING -> {

                }
                Resource.Status.SUCCESS -> {
                    isIntialDataLoaded = true
                    mBinding.shimmerViewContainer.stopShimmerAnimation()
                    userDetail = it.data as UserDetail
                    if (userDetail != null){

                        mBinding.lblHeaderName.text = userDetail.name

                        Glide.with(mBinding.imgAvatar)
                            .load(userDetail.avatar_url)
                            .apply(RequestOptions.circleCropTransform())
                            .into(mBinding.imgAvatar)

                        mBinding.lblBlog.text = "blog : ${userDetail.blog}"
                        mBinding.lblFollowing.text = "Following : ${userDetail.following}"
                        mBinding.lblHeaderName.text = "name : ${userDetail.name}"
                        mBinding.lblCompany.text = "company : ${userDetail.company}"
                        mBinding.lblFollowers.text = "Followers : ${userDetail.followers}"
                    }
                }
                Resource.Status.ERROR -> {
                    //hideShimmer()
                }

            }
        }

        arguments?.let { bundle ->
            val args = UserDetailFragmentArgs.fromBundle(bundle)
            args.userModel.let { userProfileModel ->
                userProfile = userProfileModel
                args.userModel.id?.let { userProfile.login?.let { it1 ->
                    userProfile.id?.let { it2 ->
                        if (Utility.isNetWorkAvaialable(requireContext())){
                            mViewModel.getUser(
                                it2,
                                it1
                            )
                        }else{
                            Toast.makeText(context, "No Internet Available", Toast.LENGTH_SHORT).show()
                        }

                    }
                } }

                userProfileModel.notes?.let {
                    mBinding.etNotes.setText(it)
                }
            }

        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        TawkApplication.getInstance()?.setNetworkListener(this)
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        if (!isIntialDataLoaded)
            userProfile.id?.let { userProfile.login?.let { it1 -> mViewModel.getUser(it, it1) } }
    }

}