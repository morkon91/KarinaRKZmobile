package com.example.karinarkzmobile;

public interface INewEventObserved {
    void addNewEventObserver(INewEventObserver observer);
    void removeNewEventObserver(INewEventObserver observer);
    void notifyObservers();
}
