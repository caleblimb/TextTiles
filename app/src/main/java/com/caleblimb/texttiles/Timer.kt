package com.caleblimb.texttiles

import android.os.CountDownTimer

class Timer( TimerLength: Long?)
{
    private var newTimer = TimerInstance( TimerLength)


    private class TimerInstance( timerLengthInSecond: Long?) : Thread()
    {
        private var isThreadAllowedToRun = true
        private var timeRemaining: Long = 0;
        private var lengthInSeconds: Long = timerLengthInSecond ?: throw IllegalArgumentException("Value required To Create Timer")




        override fun run()
        {
            timeRemaining = lengthInSeconds
            startTimerInstance()
        }


        fun startTimerInstance()
        {
            while (timeRemaining > 0)
            {
                if (!isThreadAllowedToRun)
                {
                    while(!isThreadAllowedToRun)
                        sleep(10)
                }

                Thread.sleep(1000)
                println("current time $timeRemaining" )

                timeRemaining--
            }
        }


        // This will pause the Thread, but will not destroy the Thread
        fun pauseTimerInstance()
        {
            isThreadAllowedToRun = false
        }

        // Resume thread from last value sleep state
        fun resumeTimerInstance()
        {
            isThreadAllowedToRun = true
        }

        // Directly set a new timer value to decrement time from
        fun alterTimerLength(timerLengthInSecond: Long)
        {
            timeRemaining = timerLengthInSecond
        }

        // add additional time of the current value in the timer thread
        fun addTime(addTime: Long)
        {
            timeRemaining += addTime
        }

        // remove time from timer [clamped to a value of zero]
        fun removeTime(removeTime: Long)
        {
            if((timeRemaining - removeTime) < 0)
            {
                timeRemaining = 1; // ensures that time timer never goes below a value of Zero
            }
            else
            {
                timeRemaining -= removeTime
            }
        }
    }





    /**********************************************************************************************************************/
// Public Functions //
    /**********************************************************************************************************************/


    // Start Threaded Timer
    fun startTimer()
    {
        newTimer.start()
    }

    // Directly set a new timer value to decrement time from
    fun resetTimerTo(newTime /* In Seconds */ : Long )
    {
        newTimer.alterTimerLength(newTime)
    }

    fun getTimeRemainingSeconds() : Long
    {
        return newTimer.timeRemaining
    }

    // This will pause the timer, but will not destroy the timer
    fun pauseTimer()
    {
        newTimer.pauseTimerInstance()
    }

    // Resume timer from last value count
    fun resumeTimer()
    {
        newTimer.resumeTimerInstance()
    }

    // add additional time of the current value in the timer
    fun addLengthToTimer(addTime /* In Seconds */ : Long )
    {
        newTimer.addTime(addTime)
    }

    // remove time from timer [clamped to a value of zero]
    fun removeLengthFromTimer(removeTime /* In Seconds */ : Long )
    {
        newTimer.removeTime(removeTime)
    }



}
