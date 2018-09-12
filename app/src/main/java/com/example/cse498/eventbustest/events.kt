package com.example.cse498.eventbustest

class Events {

    class FragmentActivityMessage(val message: String)

    // Event used to send message from activity to fragment.
    class ActivityFragmentMessage(val message: String)

    // Event used to send message from activity to activity.
    class ActivityActivityMessage(val message: String)
}
