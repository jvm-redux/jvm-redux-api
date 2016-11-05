package redux.api;

/**
 * An interface to return the current state of the store.
 *
 * @param <S> The store type
 */
public interface StateProvider<S> {

    /**
     * Returns the current state tree of your application. It is equal to the last value returned by the store’s
     * reducer.
     *
     * @return the current state
     * @see <a href="http://redux.js.org/docs/api/Store.html#getState">http://redux.js.org/docs/api/Store.html#getState</a>
     */
    S getState();

}
