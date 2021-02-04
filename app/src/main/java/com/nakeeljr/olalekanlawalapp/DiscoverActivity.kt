package com.nakeeljr.olalekanlawalapp

import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.nakeeljr.olalekanlawalapp.model.DiscoveryResponse
import com.nakeeljr.olalekanlawalapp.model.groupie.*
import com.nakeeljr.olalekanlawalapp.model.state.Resource
import com.nakeeljr.olalekanlawalapp.model.state.Status
import com.nakeeljr.olalekanlawalapp.rest.RetrofitApiClient
import com.nakeeljr.olalekanlawalapp.utils.AppUtils
import com.nakeeljr.olalekanlawalapp.viewmodel.DiscoveryViewModel
import com.nakeeljr.olalekanlawalapp.viewmodel.DiscoveryViewModelFactory
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.OnItemClickListener
import com.xwray.groupie.Section
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder


class DiscoverActivity : AppCompatActivity() {
    private val discoveryViewmodel: DiscoveryViewModel by lazy {
        ViewModelProviders.of(this, DiscoveryViewModelFactory(RetrofitApiClient.instance)).get(
            DiscoveryViewModel::class.java
        )
    }
    private lateinit var mNewMusicRecyclerView: RecyclerView
    private lateinit var mNewPlaylistRecyclerView: RecyclerView
    private lateinit var mNewVideoRecyclerView: RecyclerView
    private lateinit var mMoodRecyclerView: RecyclerView
    private lateinit var mNewSongRecyclerView: RecyclerView

    private lateinit var mNewSongSectionShimmer: ShimmerFrameLayout
    private lateinit var mMoodSectionShimmer: ShimmerFrameLayout
    private lateinit var mNewVideoSectionShimmer: ShimmerFrameLayout
    private lateinit var mAlbumSectionShimmer: ShimmerFrameLayout
    private lateinit var mPlaylistShimmerLayout: ShimmerFrameLayout

    private lateinit var mMiniPlayerLayout : LinearLayout
    private lateinit var mMiniPlayerSongArt: ImageView
    private lateinit var mMiniPlayerPlayBtn: ImageView
    private lateinit var mMiniPlayerNextBtn: ImageView
    private lateinit var mMiniPlayerSongTitle: TextView
    private lateinit var mMiniPlayerSongArtist: TextView

    private lateinit var mMiniPlayerProgress: ProgressBar
    private lateinit var mMiniPlayerPlayProgress: ProgressBar

    private lateinit var mediaplayer: MediaPlayer
    private  var mSongDuration : Int = 0
    private var resumePosition = 0


    // Wait update audio progress thread sent message, then update audio play progress.
    private var audioProgressHandler: Handler? = null

    // The thread that send message to audio progress handler to update progress every one second.
    private lateinit var updateAudioPalyerProgressThread: Thread

    // Record whether audio is playing or not.
    private val audioIsPlaying = false

    private var isStarted = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_discover)
        mNewMusicRecyclerView = findViewById(R.id.new_album_recycler_view)
        mAlbumSectionShimmer = findViewById(R.id.shimmer_album_list_layout)
        mNewPlaylistRecyclerView = findViewById(R.id.top_playlist_recycler_view)
        mPlaylistShimmerLayout = findViewById(R.id.shimmer_playlist_layout)
        mNewVideoRecyclerView = findViewById(R.id.new_videos_recycler_view)
        mNewVideoSectionShimmer = findViewById(R.id.shimmer_new_video_layout)
        mMoodRecyclerView = findViewById(R.id.music_genre_recycler_view)
        mMoodSectionShimmer = findViewById(R.id.shimmer_mood_layout)
        mNewSongRecyclerView = findViewById(R.id.new_songs_recycler_view)
        mNewSongSectionShimmer = findViewById(R.id.shimmer_new_song_layout)

        mMiniPlayerLayout = findViewById(R.id.mini_player_layout)
        mMiniPlayerNextBtn = findViewById(R.id.next_img_btn)
        mMiniPlayerPlayBtn = findViewById(R.id.play_pause_img_btn)
        mMiniPlayerSongArt = findViewById(R.id.song_art_img)
        mMiniPlayerSongArtist = findViewById(R.id.new_song_artist_tv)
        mMiniPlayerSongTitle = findViewById(R.id.song_title_tv)

        mMiniPlayerPlayProgress = findViewById(R.id.song_loading_progress_bar)

        mMiniPlayerProgress = findViewById(R.id.mini_player_progressBar)
        audioProgressHandler = Handler()


        discoveryViewmodel.getDiscoveryData()
        discoveryViewmodel.discoveryDataResult.observe(this, {
            handleDiscoveryResultResponse(it)
        })
        mediaplayer = MediaPlayer()


        mediaplayer.setOnPreparedListener {
            if (it.isPlaying) {
                if (!isStarted) {
                    playMedia()

                    mMiniPlayerPlayBtn.setImageResource(R.drawable.ic_pause)
                }else{
                    resumeMedia()
                }
                mMiniPlayerPlayProgress.visibility = View.GONE
                mMiniPlayerPlayBtn.visibility = View.VISIBLE

                audioProgressHandler!!.post(Runnable { //Calculate progress
                    mMiniPlayerProgress.progress =
                        (mediaplayer.duration / mediaplayer.currentPosition) * 100
                })
            }else {
                pauseMedia()
                mMiniPlayerPlayBtn.setImageResource(R.drawable.ic_play)
            }
        }


    }

    private fun handleMPError(error: Int) {
        when (error) {
            MediaPlayer.MEDIA_ERROR_SERVER_DIED -> {
                Log.d("Error","server error")
            }
            MediaPlayer.MEDIA_ERROR_IO -> {
                Log.d("Error","io error")
            }
            MediaPlayer.MEDIA_ERROR_TIMED_OUT -> {
                Log.d("Error","server timed out")
            }
        }
    }

    private fun playMedia() {
        if (!mediaplayer.isPlaying) {
            isStarted = false
            mediaplayer.start()
            mMiniPlayerProgress.progress = mediaplayer.duration
        }
    }

    private fun stopMedia() {
        if (mediaplayer == null) return
        if (mediaplayer.isPlaying) {
            mediaplayer.stop()
        }
    }

    private fun pauseMedia() {
        if (mediaplayer.isPlaying) {
            mediaplayer.pause()
            isStarted = true
            resumePosition = mediaplayer.currentPosition
        }
    }

    private fun resumeMedia() {
        if (!mediaplayer.isPlaying) {
            isStarted = false
            mediaplayer.seekTo(resumePosition)
            mediaplayer.start()
        }
    }


    private fun convertDurationToInt(duration: String) : Int{
        val temp = duration.split(":")
        val minToSec = (temp[0].toInt()) * 60
        return minToSec + temp[1].toInt()
    }

    private fun playMusic(dataSource: String) {
        try {
            mediaplayer.reset()

            // For Android API 26 (Android 8 Oreo) and newer, specify AudioAttributes.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Log.d("Record", "setAudioAttributes()")
                val builder = AudioAttributes.Builder()
                builder.setUsage(AudioAttributes.USAGE_MEDIA)
                builder.setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                val attributes = builder.build()
                mediaplayer.setAudioAttributes(attributes)
            } else {
                Log.d("Record", "setAudioStreamType()")
                mediaplayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
            }
            mediaplayer.setDataSource(dataSource)
            mediaplayer.prepareAsync()
        } catch (e: Exception) {
            Log.d("Record", "error playing mp3")
            e.printStackTrace()
        }
    }

    private fun handleDiscoveryResultResponse(response: Resource<List<DiscoveryResponse>>?){
        response!!.status.let {
            when (it) {
                Status.SUCCESS -> {
                    Log.d("ResultType", response.data.toString() + "\n \n ")
                    val discoveryList = response.data!!

                    val playItem = discoveryList.filter { it.name == "Playlists" }
                    val playlistItem = playItem[0].items!!
                    val playListItems = mutableListOf<Item>()
                    Log.d("History", "1")
                    for (playList in playlistItem) {
                        val playListItem = MusicPlaylistItem(playList!!, this)
                        playListItems.add(playListItem)
                    }

                    mPlaylistShimmerLayout.stopShimmerAnimation()
                    mPlaylistShimmerLayout.visibility = View.GONE
                    mNewPlaylistRecyclerView.visibility = View.VISIBLE
                    updateMusicPlaylistRecyclerview(playListItems)

                    val newVideoItem = discoveryList.filter { it.name == "Videos" }
                    val newVideolistItem = newVideoItem[0].items!!
                    val newVideoListItems = mutableListOf<Item>()
                    Log.d("History", "1")
                    for (newVideoList in newVideolistItem) {
                        val newVideoListItem = NewVideosItem(newVideoList!!, this)
                        newVideoListItems.add(newVideoListItem)
                    }

                    mNewVideoSectionShimmer.stopShimmerAnimation()
                    mNewVideoSectionShimmer.visibility = View.GONE
                    mNewVideoRecyclerView.visibility = View.VISIBLE
                    updateNewVideoRecyclerview(newVideoListItems)

                    //New Songs
                    val newSongItem = discoveryList.filter { it.name == "Trending" }
                    val newSongListItem = newSongItem[0].items!!.take(4)
                    val newSongListItems = mutableListOf<Item>()
                    for (newSongList in newSongListItem) {
                        val newSong = NewSongItem(newSongList!!, this)
                        newSongListItems.add(newSong)
                    }

                    mNewSongSectionShimmer.stopShimmerAnimation()
                    mNewSongSectionShimmer.visibility = View.GONE
                    mNewSongRecyclerView.visibility = View.VISIBLE
                    updateNewSongRecyclerview(newSongListItems)


                    //Mood
                    val moodItem = discoveryList.filter { it.name == "Moods List" }
                    val moodlistItem = moodItem[0].items!!
                    val moodListItems = mutableListOf<Item>()
                    Log.d("History", "1")
                    for (moodList in moodlistItem) {
                        val moodListItem = MoodListItem(moodList!!, this)
                        moodListItems.add(moodListItem)
                    }

                    mMoodSectionShimmer.stopShimmerAnimation()
                    mMoodSectionShimmer.visibility = View.GONE
                    mMoodRecyclerView.visibility = View.VISIBLE
                    updateMoodRecyclerview(moodListItems)


                    //Album List
                    val albumsItem = discoveryList.filter { it.name == "Albums" }
                    val albumList = albumsItem[0].items!!
                    val itemList = mutableListOf<Item>()
                    Log.d("History", "1")
                    for (album in albumList) {
                        val beneficiaryItem = NewAlbumItem(album!!, this)
                        itemList.add(beneficiaryItem)
                    }

                    mAlbumSectionShimmer.stopShimmerAnimation()
                    mAlbumSectionShimmer.visibility = View.GONE
                    mNewMusicRecyclerView.visibility = View.VISIBLE
                    updateNewMusicRecyclerview(itemList)

                }
                Status.LOADING -> {
//                    mEmptyListLayout.visibility = View.GONE
                    mAlbumSectionShimmer.visibility = View.VISIBLE
                    mNewMusicRecyclerView.visibility = View.GONE
                    mAlbumSectionShimmer.startShimmerAnimation()


                    mPlaylistShimmerLayout.visibility = View.VISIBLE
                    mNewPlaylistRecyclerView.visibility = View.GONE
                    mPlaylistShimmerLayout.startShimmerAnimation()

                    mNewVideoSectionShimmer.visibility = View.VISIBLE
                    mNewVideoRecyclerView.visibility = View.GONE
                    mNewVideoSectionShimmer.startShimmerAnimation()

                    mMoodSectionShimmer.visibility = View.VISIBLE
                    mMoodRecyclerView.visibility = View.GONE
                    mMoodSectionShimmer.startShimmerAnimation()

                    mNewSongSectionShimmer.visibility = View.VISIBLE
                    mNewSongRecyclerView.visibility = View.GONE
                    mNewSongSectionShimmer.startShimmerAnimation()

                }
                else -> {
                    mAlbumSectionShimmer.stopShimmerAnimation()
                    mAlbumSectionShimmer.visibility = View.GONE
//                    mEmptyListLayout.visibility = View.VISIBLE
                    mNewMusicRecyclerView.visibility = View.GONE


                    mPlaylistShimmerLayout.stopShimmerAnimation()
                    mPlaylistShimmerLayout.visibility = View.GONE
                    mNewPlaylistRecyclerView.visibility = View.GONE


                    mNewVideoSectionShimmer.stopShimmerAnimation()
                    mNewVideoSectionShimmer.visibility = View.GONE
                    mNewVideoRecyclerView.visibility = View.GONE

                    mMoodSectionShimmer.stopShimmerAnimation()
                    mMoodSectionShimmer.visibility = View.GONE
                    mMoodRecyclerView.visibility = View.GONE

                    mNewSongSectionShimmer.stopShimmerAnimation()
                    mNewSongSectionShimmer.visibility = View.GONE
                    mNewSongRecyclerView.visibility = View.GONE
                }
            }
        }
    }


    override fun onResume() {
        super.onResume()
        mAlbumSectionShimmer.startShimmerAnimation()
        mPlaylistShimmerLayout.startShimmerAnimation()
        mNewVideoSectionShimmer.startShimmerAnimation()
        mMoodSectionShimmer.startShimmerAnimation()
        mNewSongSectionShimmer.startShimmerAnimation()
    }

    override fun onPause() {
        mAlbumSectionShimmer.stopShimmerAnimation()
        mPlaylistShimmerLayout.stopShimmerAnimation()
        mNewVideoSectionShimmer.stopShimmerAnimation()
        mMoodSectionShimmer.stopShimmerAnimation()
        mNewSongSectionShimmer.stopShimmerAnimation()
        super.onPause()
    }

    //
    private lateinit var  newMusicSection : Section
    fun updateNewMusicRecyclerview(beneficiaryList: List<Item>) {
        var shouldInitRecyclerview = true
        fun init() {
            mNewMusicRecyclerView.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = GroupAdapter<ViewHolder>().apply {
                    newMusicSection = Section(beneficiaryList)
                    this.add(newMusicSection)
                    setOnItemClickListener(onItemClick)
                }
                shouldInitRecyclerview = false
            }
        }
        fun updateNewMusic() = newMusicSection.update(beneficiaryList)

        if (shouldInitRecyclerview){
            init()
        }else{
            updateNewMusic()
        }
    }

    private val onItemClick = OnItemClickListener { item, view ->
        if(item is NewSongItem){

            mMiniPlayerLayout.visibility = View.VISIBLE
            mMiniPlayerSongTitle.text = item.songItem.name
            mMiniPlayerSongArtist.text = item.songItem.artistName
            AppUtils.loadImage(item.songItem.artwork!!, mMiniPlayerSongArt, this)
            playMusic(item.songItem.source)
            mSongDuration = convertDurationToInt(item.songItem.duration!!)
            mMiniPlayerPlayProgress.visibility = View.VISIBLE
            mMiniPlayerPlayBtn.visibility = View.GONE
            Log.d("Duration", mSongDuration.toString())
        }
//        val newMusicSelected = item as NewAlbumItem
    }


private lateinit var  newMusicPlaylisSection : Section
fun updateMusicPlaylistRecyclerview(playLists: List<Item>) {
    var shouldInitRecyclerview = true
    fun init() {
        mNewPlaylistRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = GroupAdapter<ViewHolder>().apply {
                newMusicPlaylisSection = Section(playLists)
                this.add(newMusicPlaylisSection)
                setOnItemClickListener(onItemClick)
            }
            shouldInitRecyclerview = false
        }
    }
    fun updatePlaylist() = newMusicPlaylisSection.update(playLists)

    if (shouldInitRecyclerview){
        init()
    }else{
        updatePlaylist()
    }
}

    private lateinit var  newVideoSection : Section
    fun updateNewVideoRecyclerview(playLists: List<Item>) {
        var shouldInitRecyclerview = true
        fun init() {
            mNewVideoRecyclerView.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = GroupAdapter<ViewHolder>().apply {
                    newVideoSection = Section(playLists)
                    this.add(newVideoSection)
                    setOnItemClickListener(onItemClick)
                }
                shouldInitRecyclerview = false
            }
        }
        fun updateVideolist() = newVideoSection.update(playLists)

        if (shouldInitRecyclerview){
            init()
        }else{
            updateVideolist()
        }
    }

    private lateinit var  newMoodSection : Section
    fun updateMoodRecyclerview(moodLists: List<Item>) {
        var shouldInitRecyclerview = true
        fun init() {
            mMoodRecyclerView.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = GroupAdapter<ViewHolder>().apply {
                    newMoodSection = Section(moodLists)
                    this.add(newMoodSection)
                    setOnItemClickListener(onItemClick)
                }
                shouldInitRecyclerview = false
            }
        }
        fun updateMoodlist() = newMoodSection.update(moodLists)

        if (shouldInitRecyclerview){
            init()
        }else{
            updateMoodlist()
        }
    }

    private lateinit var  newSongSection : Section
    fun updateNewSongRecyclerview(newSongLists: List<Item>) {
        var shouldInitRecyclerview = true
        fun init() {
            mNewSongRecyclerView.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = GroupAdapter<ViewHolder>().apply {
                    newSongSection = Section(newSongLists)
                    this.add(newSongSection)
                    setOnItemClickListener(onItemClick)
                }
                shouldInitRecyclerview = false
            }
        }
        fun updateNewSonglist() = newSongSection.update(newSongLists)

        if (shouldInitRecyclerview){
            init()
        }else{
            updateNewSonglist()
        }
    }
}

