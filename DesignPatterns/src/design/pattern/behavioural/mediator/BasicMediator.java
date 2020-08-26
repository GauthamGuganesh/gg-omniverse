package design.pattern.behavioural.mediator;

import java.util.ArrayList;
import java.util.List;

class Participant
{
  int value;
  Mediator mediator;
  
  public Participant(Mediator mediator)
  {
    value = 0;
    this.mediator = mediator;
    mediator.enroll(this);
  }
  
  public void receive(int n)
  {
      System.out.println("Old value " + value);
      value += n;
      System.out.println("Participant received message " + value);
  }

  public void say(int n)
  {
    mediator.broadcast(this, n);
  }
}

class Mediator
{
  List<Participant> participants = new ArrayList<>();

  public void broadcast(Participant source, int n)
  {
      for(Participant p : participants)
      {
          if(!p.equals(source))
            p.receive(n);
      }
  }
  
  public void enroll(Participant participant)
  {
      participants.add(participant);
  }
}