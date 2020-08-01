package com.wanshare.crush.account.view.widget;

import android.os.Handler;
import android.os.Message;

public abstract class AdvancedCountdownTimer {

	private final long mCountdownInterval;

	private long mTotalTime;

	private long mRemainTime;

	private boolean isRunning = false;

	public AdvancedCountdownTimer(long millisInFuture, long countDownInterval) {
		mTotalTime = millisInFuture;
		mCountdownInterval = countDownInterval;

		mRemainTime = millisInFuture;
	}

	public final void seek(int value) {
		synchronized (AdvancedCountdownTimer.this) {
			mRemainTime = ((100 - value) * mTotalTime) / 100;
		}
	}


	public final void cancel() {
		mHandler.removeMessages(MSG_RUN);
		mHandler.removeMessages(MSG_PAUSE);
		isRunning = false;
	}

	public final void resume() {
		mHandler.removeMessages(MSG_PAUSE);
		mHandler.sendMessageAtFrontOfQueue(mHandler.obtainMessage(MSG_RUN));
		isRunning = true;
	}

	public final void pause() {
		mHandler.removeMessages(MSG_RUN);
		mHandler.sendMessageAtFrontOfQueue(mHandler.obtainMessage(MSG_PAUSE));
		isRunning = false;
	}


	public synchronized final AdvancedCountdownTimer start() {
		cancel();
		if (mRemainTime <= 0) {
//			onFinish();
//			isRunning = false;
//			return this;
			mRemainTime = mTotalTime;
		}
		onTick(mRemainTime, new Long(100 * (mTotalTime - mRemainTime) / mTotalTime).intValue());
		mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_RUN),
				mCountdownInterval);
		isRunning = true;
		return this;
	}

	public boolean isRunning(){
		return isRunning;
	}

	public abstract void onTick(long millisUntilFinished, int percent);


	public abstract void onFinish();

	private static final int MSG_RUN = 1;
	private static final int MSG_PAUSE = 2;

	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {

			synchronized (AdvancedCountdownTimer.this) {
				if (msg.what == MSG_RUN) {
					mRemainTime = mRemainTime - mCountdownInterval;

					if (mRemainTime <= 0) {
						onFinish();
					} else if (mRemainTime < mCountdownInterval) {
						sendMessageDelayed(obtainMessage(MSG_RUN), mRemainTime);
					} else {

						sendMessageDelayed(obtainMessage(MSG_RUN),
								mCountdownInterval);

						onTick(mRemainTime, Long.valueOf(100
								* (mTotalTime - mRemainTime) / mTotalTime)
								.intValue());


					}
				} else if (msg.what == MSG_PAUSE) {

				}
			}
		}
	};
}

