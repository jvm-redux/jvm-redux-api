package redux.api;


public interface Action<S, P> {

    /**
     *
     *  return the type of an action
     *
     * */

    S getType();


    /**
     *  return the payload of an action
     * */

    P getPayload();

}
