package redux.api

/*
 * Copyright (C) 2016 Michael Pardo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * Coordinates actions and [Reducers][Reducer]. Store has the following responsibilities:
 * * Holds application state
 * * Allows access to state via [getState()]
 * * Allows state to be updated via [dispatch()]
 * * Registers listeners via [subscribe()]
 * * Handles unregistering of listeners via the [Subscriber] returned by [subscribe()]
 *
 * @see <a href="http://redux.js.org/docs/basics/Store.html">http://redux.js.org/docs/basics/Store.html</a>
 */
interface Store<S : Any> : Dispatcher {

    /**
     * Returns the current state tree of your application. It is equal to the last value returned by the store’s
     * reducer.
     *
     * @see <a href="http://redux.js.org/docs/api/Store.html#getState">http://redux.js.org/docs/api/Store.html#getState</a>
     *
     * @return the current state
     */
    fun getState(): S

    /**
     * Adds a change listener. It will be called any time an action is dispatched, and some part of the state tree may
     * potentially have changed. You may then call [getState()] to read the current state tree inside the callback.
     *
     * @see <a href="http://redux.js.org/docs/api/Store.html#subscribe">http://redux.js.org/docs/api/Store.html#subscribe</a>
     *
     * @param[subscriber] The subscriber
     * @return A subscription
     */
    fun subscribe(subscriber: Subscriber): Subscription

    /**
     * Adds a change listener. It will be called any time an action is dispatched, and some part of the state tree may
     * potentially have changed. You may then call [getState()] to read the current state tree inside the callback.
     *
     * @see <a href="http://redux.js.org/docs/api/Store.html#subscribe">http://redux.js.org/docs/api/Store.html#subscribe</a>
     *
     * @param[subscriber] The subscriber function
     * @return A subscription
     */
    fun subscribe(subscriber: () -> Unit) = subscribe(Subscriber(subscriber))

    /**
     * Replaces the reducer currently used by the store to calculate the state.
     *
     * @see <a href="http://redux.js.org/docs/api/Store.html#replaceReducer">http://redux.js.org/docs/api/Store.html#replaceReducer</a>
     *
     * @param[reducer] The reducer
     */
    fun replaceReducer(reducer: Reducer<S>)

    /**
     * An interface that creates a Redux store.
     *
     * @see <a href="http://redux.js.org/docs/Glossary.html#store-creator">http://redux.js.org/docs/Glossary.html#store-creator</a>
     */
    interface Creator {

        /**
         *
         */
        fun <S : Any> create(reducer: Reducer<S>, initialState: S, enhancer: Enhancer? = null): Store<S>
    }

    /**
     * An interface that composes a store creator to return a new, enhanced store creator.
     *
     * @see <a href="http://redux.js.org/docs/Glossary.html#store-enhancer">http://redux.js.org/docs/Glossary.html#store-enhancer</a>
     */
    interface Enhancer {

        /**
         *
         */
        fun enhance(next: Creator): Creator

        companion object {
            operator fun invoke(f: (Creator) -> Creator) = object : Enhancer {
                override fun enhance(next: Creator): Creator = f(next)
            }
        }
    }

    /**
     * A listener which will be called any time an action is dispatched, and some part of the state tree may potentially
     * have changed. You may then call [getState()] to read the current state tree inside the listener.
     *
     * @see <a href="http://redux.js.org/docs/api/Store.html#subscribe">http://redux.js.org/docs/api/Store.html#subscribe</a>
     */
    interface Subscriber {

        /**
         * Called any time an action is dispatched.
         */
        fun onStateChanged()

        companion object {

            /**
             * Creates a new [Subscriber] instance using the provided function as the [onStateChanged()] implementation.
             *
             * @param[f] A higher-order function equivalent to the [onStateChanged()] function
             * @return A new subscriber instance
             */
            operator fun invoke(f: () -> Unit) = object : Subscriber {
                override fun onStateChanged() = f()
            }

        }

    }

    /**
     * A reference to the [Subscriber] to allow for unsubscription.
     */
    interface Subscription {

        /**
         * Unsubscribe the [Subscriber] from the [Store].
         */
        fun unsubscribe()

        companion object {
            operator fun invoke(f: () -> Unit) = object : Subscription {
                override fun unsubscribe() = f()
            }

        }
    }

    companion object {

        /**
         * When a store is created, an "INIT" action is dispatched so that every reducer returns their initial state.
         * This effectively populates the initial state tree.
         */
        object INIT {}
    }
}
