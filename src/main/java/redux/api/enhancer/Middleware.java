package redux.api.enhancer;

import redux.api.Dispatcher;

/**
 * A middleware is an interface that composes a {@link Dispatcher} to return a new dispatch function. It often turns
 * async actions into actions.
 *
 * @see <a href="http://redux.js.org/docs/advanced/Middleware.html">http://redux.js.org/docs/advanced/Middleware.html</a>
 *
 * @param <S> The store type
 */
interface Middleware<S> {

    /**
     * Dispatches an action. This is the only way to trigger a state change.
     *
     * @see <a href="http://redux.js.org/docs/Glossary.html#middleware">http://redux.js.org/docs/Glossary.html#middleware</a>
     *
     * @param stateProvider An interface to return the current state of the store.
     * @param action The action
     * @param next The next dispatcher in the chain
     * @return The action
     */
    Object dispatch(StateProvider<S> stateProvider, Object action, Dispatcher next);

    /**
     * An interface to return the current state of the store.
     *
     * @param <S> The store type
     */
    interface StateProvider<S> {

        /**
         * Returns the current state of the store.
         *
         * @return The current state of the store.
         */
        S getState();

    }

}
