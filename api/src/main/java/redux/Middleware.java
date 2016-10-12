package redux;

/**
 * Provides a third-party extension point between dispatching an action, and the moment it reaches the reducer.
 *
 * @see <a href="http://redux.js.org/docs/advanced/Middleware.html">http://redux.js.org/docs/advanced/Middleware.html</a>
 */
public interface Middleware<S> {

	/**
	 * Apply middleware behavior to the dispatched action.
	 *
	 * @param store The previous state
	 * @param action A plain object describing the change that makes sense for your application
	 * @param next The next dispatcher to call
	 * @return The dispatched action
	 */
	Object dispatch(Store<S> store, Object action, Dispatcher next);

}
