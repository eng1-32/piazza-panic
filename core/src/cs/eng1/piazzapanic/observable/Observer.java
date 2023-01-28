package cs.eng1.piazzapanic.observable;

import java.util.List;

/**
 * An observer can be implemented in a way where it can be registered with a Subject of the same
 * type T which will contains a central value which notifies this observer every time that value
 * changes.
 *
 * @param <T> The class type that will be received every time this class receives an update.
 */
public interface Observer<T> {

  /**
   * Lets the implementation of Observer deal with the new value that was set in the Subject.
   *
   * @param update The message or value being passed from the Subject to this Observer.
   */
  void update(T update);

  void addSubject(Subject<T> subject);

  void removeSubject(Subject<T> subject);

  List<Subject<T>> getSubjects();

  void deregisterFromAllSubjects();
}
