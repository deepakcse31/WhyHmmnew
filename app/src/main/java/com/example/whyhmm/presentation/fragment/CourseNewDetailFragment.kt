package com.example.whyhmm.presentation.fragment

import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Build.VERSION_CODES.R
import android.os.Bundle
import android.util.SparseArray
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import at.huber.youtubeExtractor.VideoMeta
import at.huber.youtubeExtractor.YouTubeExtractor
import at.huber.youtubeExtractor.YtFile
import com.example.whyhmm.R
import com.example.whyhmm.databinding.FragmentCourseNewDetailBinding
import com.example.whyhmm.domain.adapter.LearningAdapter
import com.example.whyhmm.domain.adapter.MarketingEpisodeAdapter
import com.example.whyhmm.domain.adapter.TagsAdapter
import com.example.whyhmm.domain.utils.generateQualityList
import com.example.whyhmm.domain.utils.show
import com.example.whyhmm.presentation.fragment.basefragment.BaseFragment
import com.example.whyhmm.presentation.fragment.viewmodel.CourseDetailNewViewModel
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.TracksInfo
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.MergingMediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelectionOverrides
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.util.Util
import com.google.gson.JsonObject
import com.norulab.exofullscreen.MediaPlayer
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class CourseNewDetailFragment : BaseFragment<FragmentCourseNewDetailBinding,CourseDetailNewViewModel>()  ,Player.Listener{
    private var qualityPopUp: PopupMenu? = null
    private var player: ExoPlayer? = null
    private var playbackPosition = 0L
    private var playWhenReady = true
    private var trackSelector: DefaultTrackSelector? = null
    var qualityList = ArrayList<Pair<String, TrackSelectionOverrides.Builder>>()
    private var playerexo: ExoPlayer? = null
    var fullscreen = false

    private var adaptertags : TagsAdapter?=null

    private var adapterlearn : LearningAdapter?=null

    private var adaptermarketingepisode : MarketingEpisodeAdapter?=null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
         // window.statusBarColor = ContextCompat.getColor(requireContext(), R.c)

        window.statusBarColor = ContextCompat.getColor(requireContext(), com.example.whyhmm.R.color.black)




//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            requireActivity().window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
//            requireActivity().window.statusBarColor =
//                resources.getColor(R.color.black, requireActivity().theme)
//        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            requireActivity().window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
//            requireActivity().window.statusBarColor = resources.getColor(R.color.black)
//        }
       // initListener()
        youtubevideplay()
        lifecycleScope.launchWhenStarted {
            delay(2000)
            binding.mainLay.visibility=View.VISIBLE
        }
        adaptertags= TagsAdapter()
        adapterlearn= LearningAdapter()
        adaptermarketingepisode= MarketingEpisodeAdapter()
        binding.apply {


            var gridLayoutManager=
                GridLayoutManager(requireContext(),2, GridLayoutManager.VERTICAL,false)
            //  gridLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            rvtags.layoutManager= gridLayoutManager
            rvtags.adapter=adaptertags
            rvtags.show()


            rvlearn.layoutManager= LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
            rvlearn.adapter=adapterlearn
            rvlearn.show()



            rvmarketing.layoutManager= LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
            rvmarketing.adapter=adaptermarketingepisode
            rvmarketing.show()
        }
    }
    private fun Apicall(number: String) {
        val payerReg = JsonObject()
        payerReg.addProperty("mobile_number", number)
        // viewModel. login(payerReg)
    }

    private fun initListener() {
        binding.apply {
            ivexpand.setOnClickListener {

                if (fullscreen) {
                    ivexpand.setImageDrawable(
                        ContextCompat.getDrawable(
                            requireContext(),
                            com.example.whyhmm.R.drawable.full_screen_open
                        )
                    )
                    window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
                    if ((activity as AppCompatActivity?)?.supportActionBar != null) {
                        (activity as AppCompatActivity?)?.supportActionBar!!.show()
                    }
                    (activity as AppCompatActivity?)?.requestedOrientation =
                        ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                    val params = binding.playerExo.getLayoutParams()
                    params.width = ViewGroup.LayoutParams.MATCH_PARENT
                    params.height =
                        (200 * requireActivity().resources.displayMetrics.density).toInt()
                    binding.playerExo.setLayoutParams(params)
                    fullscreen = false
                } else {
//                    ivexpand.setImageDrawable(
//                        ContextCompat.getDrawable(
//                            requireContext(),
//                            R.drawable.full_screen_model
//                        )
//                    )
                    window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_FULLSCREEN
                            or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
                    if ((activity as AppCompatActivity?)?.supportActionBar != null) {
                        (activity as AppCompatActivity?)?.supportActionBar!!.hide()
                    }
                    (activity as AppCompatActivity?)?.requestedOrientation =
                        ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                    val params = binding.playerExo.getLayoutParams()
                    params.width = ViewGroup.LayoutParams.MATCH_PARENT
                    params.height = ViewGroup.LayoutParams.MATCH_PARENT
                    binding.playerExo.setLayoutParams(params)
                    fullscreen = true
                }


            }
            binding.exoQuality.setOnClickListener {
                qualityPopUp?.show()
            }
        }
    }


    private fun initPlayer() {
        trackSelector =
            DefaultTrackSelector(/* context= */requireContext(), AdaptiveTrackSelection.Factory())
        player = ExoPlayer.Builder(requireContext())
            .setTrackSelector(trackSelector!!)
            .build()
        player?.playWhenReady = false
        binding.playerExo.player = player
        val defaultHttpDataSourceFactory = DefaultHttpDataSource.Factory()
        // val mediaItem1 = MediaItem.fromUri("https://bitdash-a.akamaihd.net/content/MI201109210084_1/m3u8s/f08e80da-bf1d-4e3d-8899-f0f6155f6efa.m3u8")
        val mediaItem =
            MediaItem.fromUri("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4")
        // val mediaItem = MediaItem.fromUri(URL)
        val mediaSource = ProgressiveMediaSource.Factory(defaultHttpDataSourceFactory)
            .createMediaSource(mediaItem)
        MediaPlayer.exoPlayer?.setMediaSource(mediaSource)
        // val mediaSource = HlsMediaSource.Factory(defaultHttpDataSourceFactory).createMediaSource(mediaItem)
        //player?.setMediaSource(mediaSource)
        player?.setMediaItem(mediaItem)
        player?.seekTo(playbackPosition)
        player?.playWhenReady = false
        player?.addListener(this)
        player?.prepare()
        binding.playerExo.setControllerVisibilityListener {
            if (it == View.VISIBLE) {
                binding.ivexpand.visibility = View.VISIBLE
                binding.exoQuality.visibility = View.VISIBLE
            } else {

                binding.exoQuality.visibility = View.GONE
                binding.ivexpand.visibility = View.GONE
            }
        }

    }

    private fun setUpQualityList() {
        qualityPopUp = PopupMenu(requireContext(), binding.exoQuality)
        qualityList.let {
            for ((i, videoQuality) in it.withIndex()) {
                qualityPopUp?.menu?.add(0, i, 0, videoQuality.first)
            }
        }
        qualityPopUp?.setOnMenuItemClickListener { menuItem ->
            qualityList[menuItem.itemId].let {
                trackSelector!!.setParameters(
                    trackSelector!!.getParameters()
                        .buildUpon()
                        .setTrackSelectionOverrides(it.second.build())
                        .setTunnelingEnabled(true)
                        .build()
                )
            }
            true
        }
    }

    override fun onTracksInfoChanged(tracksInfo: TracksInfo) {
        println("TRACK CHANGED")
        println(tracksInfo.trackGroupInfos)
    }

    override fun onPlaybackStateChanged(playbackState: Int) {
        if (playbackState == Player.STATE_READY) {
            trackSelector?.generateQualityList()?.let {
                qualityList = it
                setUpQualityList()
            }
        }
    }

    private fun releasePlayer() {
        player?.let {
            playbackPosition = it.currentPosition
            playWhenReady = it.playWhenReady
            it.release()
            player = null
        }
    }
    private fun youtubevideplay(){
        player = ExoPlayer.Builder(requireContext()).build()
        binding.playerExo.player = player
        object : YouTubeExtractor(requireContext()) {
            override fun onExtractionComplete(
                ytFiles: SparseArray<YtFile>?,
                videoMeta: VideoMeta?
            ) {
                if (ytFiles != null) {

                    val iTag = 137//tag of video 1080
                    val audioTag = 140 //tag m4a audio
                    // 720, 1080, 480
                    var videoUrl = ""
                    val iTags: List<Int> = listOf(22, 137, 18)
                    for (i in iTags) {
                        val ytFile = ytFiles.get(i)
                        if (ytFile != null) {
                            val downloadUrl = ytFile.url
                            if (downloadUrl != null && downloadUrl.isNotEmpty()) {
                                videoUrl = downloadUrl
                            }
                        }
                    }
                    if (videoUrl == "")
                        videoUrl = ytFiles[iTag].url
                    val audioUrl = ytFiles[audioTag].url
                    val audioSource: MediaSource = ProgressiveMediaSource
                        .Factory(DefaultHttpDataSource.Factory())
                        .createMediaSource(MediaItem.fromUri(audioUrl))
                    val videoSource: MediaSource = ProgressiveMediaSource
                        .Factory(DefaultHttpDataSource.Factory())
                        .createMediaSource(MediaItem.fromUri(videoUrl))
                    player?.setMediaSource(
                        MergingMediaSource(true, videoSource, audioSource), true
                    )
                    player?.prepare()
                    player?.playWhenReady = playWhenReady
                    player?.seekTo(playbackPosition)
                    player?.addListener(this@CourseNewDetailFragment)
                }
            }

        }.extract("https://www.youtube.com/watch?v=Wvzw24sZfqc")

    }


    override fun onStart() {
        super.onStart()
        if (Util.SDK_INT >= 24) {
            initPlayer()
        }
    }

    override fun onStop() {
        super.onStop()
        if (Util.SDK_INT >= 24) {
            releasePlayer()
        }
    }

    override fun onResume() {
        super.onResume()
        if (Util.SDK_INT < 24) {
            initPlayer()
        }
    }

    override fun onPause() {
        super.onPause()
        if (Util.SDK_INT < 24) {
            releasePlayer()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        releasePlayer()
        //window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.black)
    }


}