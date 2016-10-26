package redux.api;

/**
 * A dispatcher is an interface that accepts an action or an async action; it then may or may not dispatch one or more
 * actions to the store.
 *
 * @see <a href="http://redux.js.org/docs/Glossary.html#dispatching-function">http://redux.js.org/docs/Glossary.html#dispatching-function</a>
 */
public interface Dispatcher {

	/**
	 * Dispatches an action. This is the only way to trigger a state change.
	 *
	 * @see <a href="http://redux.js.org/docs/api/Store.html#dispatch">http://redux.js.org/docs/api/Store.html#dispatch</a>
	 *
	 * @param action A plain object describing the change that makes sense for your application
	 * @return The dispatched action
	 */
	Object dispatch(Object action);

}
