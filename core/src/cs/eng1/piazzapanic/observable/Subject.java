package cs.eng1.piazzapanic.observable;

public interface Subject<T> {
  void register(Observer<T> observer);
  void deregister(Observer<T> observer);

  void clearAllObservers();

  void notifyObservers(T update);
}
