package redux.api.enhancer;

import redux.api.Dispatcher;
import redux.api.StateProvider;

/**
 * A middleware is an interface that composes a {@link Dispatcher} to return a new dispatch function. It often turns
 * async actions into actions.
 *
 * @param <S> The store type
 * @see <a href="http://redux.js.org/docs/advanced/Middleware.html">http://redux.js.org/docs/advanced/Middleware.html</a>
 */
interface Middleware<S> {

	/**
	 * Dispatches an action. This is the only way to trigger a state change.
	 *
	 * @param stateProvider An interface to return the current state of the store.
	 * @param action The action
	 * @param dispatcher The next dispatcher in the chain
	 * @return The action
	 * @see <a href="http://redux.js.org/docs/Glossary.html#middleware">http://redux.js.org/docs/Glossary.html#middleware</a>
	 */
	Object dispatch(StateProvider<S> stateProvider, Object action, Dispatcher dispatcher);

}
