package Vizualization.Interfaces;

import Vizualization.Exceptions.NoInternetConnectionException;

@FunctionalInterface
public interface TeamFetcher {
    int fetch(String name) throws NoInternetConnectionException;
}