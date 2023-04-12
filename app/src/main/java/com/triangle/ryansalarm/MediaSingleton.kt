package com.triangle.ryansalarm

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.util.Log

object MediaSingleton
{
	var AutoStopDelayMS: Long = 5000

	private var player: MediaPlayer? = null
	private var handler: Handler? = null

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
		this.player!!.seekTo(0)
		this.player!!.start()

		this.handler?.postDelayed(delayStop(), AutoStopDelayMS)
	}

	private fun delayStop(): () -> Unit = {
		this.stop()
		Log.d("MediaSingleton", "play post delayed called")
	}

	fun stop()
	{
		this.player?.stop()
	}
}