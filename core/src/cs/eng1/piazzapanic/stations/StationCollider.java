package cs.eng1.piazzapanic.stations;

import com.badlogic.gdx.scenes.scene2d.Actor;
import cs.eng1.piazzapanic.chef.Chef;
import cs.eng1.piazzapanic.observable.Observer;
import cs.eng1.piazzapanic.observable.Subject;

import java.util.LinkedList;
import java.util.List;

public class StationCollider extends Actor implements Subject<Chef> {
  protected List<Observer<Chef>> observers;

  public StationCollider() {
    this.observers = new LinkedList<>();
  }

  @Override
  public void register(Observer<Chef> observer) {
    if (observer == null) throw new NullPointerException("Observer cannot be null");
    if (!observers.contains(observer)) {
      observers.add(observer);
      observer.setSubject(this);
    }
  }

  @Override
  public void deregister(Observer<Chef> observer) {
    if (observers.remove(observer)) {
      observer.setSubject(null);
    }
  }

  @Override
  public void clearAllObservers() {
    for (Observer<Chef> observer : observers) {
      observer.setSubject(null);
    }
    observers.clear();
  }

  @Override
  public void notifyObservers(Chef chef) {
    for (Observer<Chef> observer : observers) {
      observer.update(chef);
    }
  }
}
