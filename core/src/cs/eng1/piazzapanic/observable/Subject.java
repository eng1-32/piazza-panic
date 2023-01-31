package cs.eng1.piazzapanic.observable;

/**
 * A Subject contains a list of registered observers which all receive the same value of type T
 * whenever it is updated.
 *
 * @param <T> The type that is stored within the subject that is sent to all the registered
 *            observers whenever it is updated.
 */
public interface Subject<T> {

  /**
   * Add an observer which will be notified with every new T
   *
   * @param observer the observer to register to this subject
   */
  void register(Observer<T> observer);

  /**
   * Stop an observer from receiving future updates from this subject
   *
   * @param observer the observer to deregister from this subject
   */
  void deregister(Observer<T> observer);

  void clearAllObservers();

  /**
   * @param update The new T to send to every registered observer
   */
  void notifyObservers(T update);

  /**
   * @return the most recent value that was sent to the observers.
   */
  T getLastNotification();
}
