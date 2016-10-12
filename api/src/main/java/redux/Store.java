package redux;

/**
 * Coordinates actions and [Reducers][Reducer]. Store has the following responsibilities:
 * <ul>
 * 	<li>Holds application state</li>
 * 	<li>Allows access to state via {@link #getState()}</li>
 * 	<li>Allows state to be updated via {@link redux.Dispatcher#dispatch(Object)}</li>
 * 	<li>Registers listeners via {@link #subscribe(Subscriber)}</li>
 * 	<li>Handles unregistering of listeners via the {@link Subscriber} returned by {@link #subscribe(Subscriber)}</li>
 * </ul>
 *
 * @see <a href="http://redux.js.org/docs/basics/Store.html">http://redux.js.org/docs/basics/Store.html</a>
 */
public interface Store<S> extends Dispatcher {

	/**
	 * Returns the current state tree of your application. It is equal to the last value returned by the store’s
	 * reducer.
	 *
	 * @return the current state
	 * @see <a href="http://redux.js.org/docs/api/Store.html#getState">http://redux.js.org/docs/api/Store.html#getState</a>
	 */
	S getState();

	/**
	 * Adds a change listener. It will be called any time an action is dispatched, and some part of the state tree may
	 * potentially have changed. You may then call {@link #getState()} to read the current state tree inside the callback.
	 *
	 * @return A subscription
	 * @param subscriber The subscriber
	 * @see <a href="http://redux.js.org/docs/api/Store.html#subscribe">http://redux.js.org/docs/api/Store.html#subscribe</a>
	 */
	Subscription subscribe(Subscriber subscriber);

	/**
	 * Replaces the reducer currently used by the store to calculate the state.
	 *
	 * @param reducer The reducer
	 * @see <a href="http://redux.js.org/docs/api/Store.html#replaceReducer">http://redux.js.org/docs/api/Store.html#replaceReducer</a>
	 */
	void replaceReducer(Reducer<S> reducer);

	/**
	 * An interface that creates a Redux store.
	 *
	 * @see <a href="http://redux.js.org/docs/Glossary.html#store-creator">http://redux.js.org/docs/Glossary.html#store-creator</a>
	 */
	interface Creator<S> {

		/**
		 *
		 */
		Store<S> create(Reducer<S> reducer, S initialState, Enhancer<S> enhancer);
	}

	/**
	 * An interface that composes a store creator to return a new, enhanced store creator.
	 *
	 * @see <a href="http://redux.js.org/docs/Glossary.html#store-enhancer">http://redux.js.org/docs/Glossary.html#store-enhancer</a>
	 */
	interface Enhancer<S> {

		/**
		 *
		 */
		Creator<S> enhance(Creator<S> next);
	}

	/**
	 * A listener which will be called any time an action is dispatched, and some part of the state tree may potentially
	 * have changed. You may then call {@link #getState()} to read the current state tree inside the listener.
	 *
	 * @see <a href="http://redux.js.org/docs/api/Store.html#subscribe">http://redux.js.org/docs/api/Store.html#subscribe</a>
	 */
	interface Subscriber {

		/**
		 * Called any time an action is dispatched.
		 */
		void onStateChanged();
	}

	/**
	 * A reference to the {@link Subscriber} to allow for unsubscription.
	 */
	interface Subscription {

		/**
		 * Unsubscribe the {@link Subscriber} from the {@link Store}.
		 */
		void unsubscribe();
	}

}
