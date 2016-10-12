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
 * Provides a third-party extension point between dispatching an action, and the moment it reaches the reducer.
 *
 * @see <a href="http://redux.js.org/docs/advanced/Middleware.html">http://redux.js.org/docs/advanced/Middleware.html</a>
 */
interface Middleware<S : Any> {

    /**
     * Apply middleware behavior to the dispatched action.
     *
     * ## Example
     *
     * ```kotlin
     * Middleware { store: Store&lt;S&gt;, action: Any, next: Dispatcher ->
     *     println("Previous state: $store")
     *     val result = next.dispatch(action)
     *     println("New state: $store")
     *     result
     * }
     * ```
     *
     * @param[store] The previous state
     * @param[action] A plain object describing the change that makes sense for your application
     * @param[next] The next dispatcher to call
     * @return The dispatched action
     */
    fun dispatch(store: Store<S>, action: Any, next: Dispatcher): Any

    companion object {
        /**
         * Creates a new [Middleware] instance using the provided function as the [dispatch()] implementation.
         *
         * @param[f] A higher-order function equivalent to the [dispatch()] function
         * @return A new middleware instance
         */
        operator fun <S : Any> invoke(f: (Store<S>, Any, Dispatcher) -> Any) = object : Middleware<S> {
            override fun dispatch(store: Store<S>, action: Any, next: Dispatcher) = f(store, action, next)
        }

    }
}
