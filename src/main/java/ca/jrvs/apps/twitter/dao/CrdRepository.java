package ca.jrvs.apps.twitter.dao;

import oauth.signpost.exception.OAuthException;

import java.io.IOException;

public interface CrdRepository<T, ID> {
    T create(T tweet) throws OAuthException, IOException;

    T findById(ID id) throws OAuthException, IOException;

    T deleteById(ID id) throws OAuthException, IOException;
}
