package com.example.guavas.observer;

import androidx.fragment.app.Fragment;

/**
 * This interface is the interface for the subjects of the observer design pattern.
 * Uses push mechanism as the update is identical for all subjects, which is replacing the fragment.
 */
public interface Subject {
    /**
     * Register the observer to the subject.
     *
     * @param observer the observer.
     */
    public void register(FragmentObserver observer);

    /**
     * Unregister the observer from the subject.
     */
    public void unregister();

    /**
     * Tells the observer to update the fragment.
     *
     * @param fragment the new fragment.
     */
    public void notifyObserver(Fragment fragment);
}
