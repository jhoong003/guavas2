package com.example.guavas.observer;

import androidx.fragment.app.Fragment;

/**
 * This interface is the interface for the observer of the observer design pattern
 */
public interface FragmentObserver {

    public void updateContainerWithFragment(Fragment fragment);
}
