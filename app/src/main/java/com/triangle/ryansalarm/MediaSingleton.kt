package com.triangle.ryansalarm

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.core.math.MathUtils
import java.util.*
import kotlin.concurrent.fixedRateTimer

object MediaSingleton
{
	//region Public Properties
	/**
	 * Controls how long to wait before automatically stopping the audio playback
	 */
	var AutoStopDelayMS: Long = 30 * 1000

	/**
	 * The initial volume to start playing at
	 */
	var InitialVolume: Double = 0.05

	/**
	 * The maximum volume to use
	 */
	var MaxVolume: Double = 1.0

	/**
	 * The volume increase per step
	 */
	var VolumeStep: Double = 0.05

	var listenerActivity: (()->Unit)? = null
	//endregion

	//region Public method(s)
	/**
	 * Initialises the singleton with the specified audio uri for the media player
	 * @param context The context to use for the media player instance
	 * @param SoundURI The uri for the audio file to use
	 */
	fun initialise(context: Context, SoundURI: Uri)
	{
		//region Release resources if needed
		if (null != this.player)
		{
			this.player?.stop()
			this.player?.release()
			this.player = null
		}
		//endregion

		this.player = MediaPlayer.create(
			context,
			SoundURI
		)

		this.handler = Handler(Looper.getMainLooper())
	}

	fun cleanup()
	{
		this.player!!.stop()
		this.player!!.release()
		this.player = null
	}

	fun play()
	{
		this.player!!.setVolume(this.InitialVolume.toFloat(), this.InitialVolume.toFloat())
		this.player!!.seekTo(0)
		this.player!!.start()

		this.handler?.postDelayed(delayStop(), AutoStopDelayMS)
		this.volumeTimer = fixedRateTimer(name = "volume", period = 500, action = { setVolume() })
		Log.d("MediaSingleton", "Audio started")

		listenerActivity?.invoke()
	}

	fun stop()
	{
		this.volumeTimer?.cancel()
		this.volumeTimer = null
		this.player?.stop()
		Log.d("MediaSingleton", "Audio stopped")
	}
	//endregion

	//region Protected Method(s)

	private fun delayStop(): () -> Unit = {
		this.stop()
		Log.d("MediaSingleton", "play post delayed called")
	}

	private fun setVolume()
	{
		this.CurrentVolume = MathUtils.clamp(
			this.CurrentVolume + this.VolumeStep,
			this.InitialVolume, this.MaxVolume
		)

		val volume = this.CurrentVolume.toFloat()
		this.player?.setVolume(volume, volume)
		Log.d("MediaSingleton", "Set volume to $volume")

		// stop changing the volume once the maximum has been reached
		if (Helpers.doublesEquivalent(this.CurrentVolume, this.MaxVolume))
		{
			this.volumeTimer?.cancel()
		}
	}
	//endregion

	//region Protected Member(s)
	private var player: MediaPlayer? = null
	private var handler: Handler? = null

	private var CurrentVolume: Double = 0.05
	private var volumeTimer: Timer? = null
	//endregion
}