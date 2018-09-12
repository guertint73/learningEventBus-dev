package com.example.cse498.eventbustest

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.analytics.FirebaseAnalytics

import org.greenrobot.eventbus.Subscribe

 class UserFragment:Fragment() {

     lateinit var metricsUtils: MetricsUtils

override fun onCreateView(inflater:LayoutInflater, container:ViewGroup?, savedInstanceState:Bundle?):View? {
 // register the event to listen.
        GlobalBus.bus!!.register(this)
return inflater.inflate(R.layout.fragment_layout, container, false)
}

     override fun onAttach(context: Context?) {
         super.onAttach(context)
         //metricsUtils.mFirebaseAnalytics.setCurrentScreen(this, "UserFragment", null)
     }

override fun onViewCreated(view:View, savedInstanceState:Bundle?) {
super.onViewCreated(view, savedInstanceState)
setClickListener(view)
}

 fun setClickListener(view:View) {
val btnSubmit = view.findViewById<View>(R.id.submit) as Button
btnSubmit.setOnClickListener {
    val etMessage = view.findViewById<View>(R.id.editText) as EditText

    // We are broadcasting the message here to listen to the subscriber.
    val fragmentActivityMessageEvent = Events.FragmentActivityMessage(
            (etMessage.text).toString())
    GlobalBus.bus!!.post(fragmentActivityMessageEvent)
    var bundle: Bundle = Bundle()

    bundle.putString(FirebaseAnalytics.Param.VALUE, "Fragment Send Message")
    metricsUtils.mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle)
}
 }

@Subscribe
 fun getMessage(activityFragmentMessage:Events.ActivityFragmentMessage) {
val messageView = view!!.findViewById<View>(R.id.message) as TextView
    messageView.text = (getString(R.string.message_received) +
            " " + activityFragmentMessage.message)

Toast.makeText(activity,
        getString(R.string.message_fragment) +
        " " + activityFragmentMessage.message,
Toast.LENGTH_SHORT).show()
}

override fun onDestroyView() {
super.onDestroyView()
 // unregister the registered event.
        GlobalBus.bus!!.unregister(this)
}
}
