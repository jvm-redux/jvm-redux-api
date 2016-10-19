package example;

import redux.api.Reducer;
import redux.api.Store;

public class SomeEnhancer<S> implements Store.Enhancer<S> {

    @Override
    public Store.Creator<S> enhance(Store.Creator<S> next) {
        return (reducer, initialState) -> {
            final LiftedReducer<S> liftedReducer = new LiftedReducer(reducer);
            // Does not compile here.
            //
            // I don't know how to solve it because Store.Creator<S> can only creates
            // Store<S> when we need Store<LiftedState<S>>
            //
            // It would work if create were carrying the state, not Store.Creator.
            final Store<LiftedState<S>> liftedStore = next.create(liftedReducer, initialState);
            return unlift(liftedStore);
        };
    }

    private Store<S> unlift(Store<LiftedState<S>> liftedStore) {
        return new Store<S>() {
            @Override
            public S getState() {
                return liftedStore.getState().vanillaState;
            }

            @Override
            public Subscription subscribe(Subscriber subscriber) {
                return liftedStore.subscribe(subscriber);
            }

            @Override
            public void replaceReducer(Reducer<S> reducer) {
                liftedStore.replaceReducer(new LiftedReducer<S>(reducer));
            }

            @Override
            public Object dispatch(Object action) {
                // todo : handde lifter actions eventually here
                // else fallback to default
                return liftedStore.dispatch(action);
            }
        };
    }

    static class LiftedReducer<S> implements Reducer<LiftedState<S>> {
        private final Reducer<S> reducer;

        LiftedReducer(Reducer<S> reducer) {
            this.reducer = reducer;
        }

        @Override
        public LiftedState<S> reduce(LiftedState<S> state, Object action) {
            return state;
        }
    }

    static class LiftedState<S> {
        S vanillaState;
    }
}
