package cs.eng1.piazzapanic.observable;

public interface Observer<T> {
  void update(T update);
  void setSubject(Subject<T> subject);
  Subject<T> getSubject();
}
