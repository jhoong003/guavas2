package com.example.guavas.observer;

import androidx.fragment.app.Fragment;

/**
 * Subject interface for the subjects in the observer design pattern.
 * Uses push mechanism as the update is identical for all subjects, which is replacing the fragment
 *
 */
public interface Subject {
    public void register(FragmentObserver observer);
    public void unregister();
    public void notifyObserver(Fragment fragment);
}
