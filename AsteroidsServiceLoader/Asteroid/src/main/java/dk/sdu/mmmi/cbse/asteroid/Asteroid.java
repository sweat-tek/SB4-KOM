/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.asteroid;

import dk.sdu.mmmi.cbse.common.data.Entity;


/**
 *
 * @author Phillip O
 */
public class Asteroid
  extends Entity
{
  private AsteroidType type;
  
  public Asteroid(AsteroidType type)
  {
    this.type = type;
  }
  
  public String getSize() {
    return type.getSize();
  }
}
